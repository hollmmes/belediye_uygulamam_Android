package com.example.girbel1.ui

import android.app.Activity
import android.content.Intent
import android.net.Uri
import android.os.Bundle
import android.provider.MediaStore
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.Button
import android.widget.CheckBox
import android.widget.EditText
import android.widget.ImageView
import android.widget.Toast
import androidx.fragment.app.Fragment
import com.example.girbel1.R
import com.google.firebase.database.FirebaseDatabase
import com.google.firebase.storage.FirebaseStorage
import java.text.SimpleDateFormat
import java.util.*

class cekgonderFragment : Fragment() {

    private lateinit var database: FirebaseDatabase
    private lateinit var storage: FirebaseStorage
    private lateinit var imageView2: ImageView
    private var imageUri: Uri? = null

    companion object {
        private const val PICK_IMAGE_REQUEST = 1
    }

    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {
        val view = inflater.inflate(R.layout.fragment_cekgonder, container, false)

        // Initialize Firebase Database and Storage references
        database = FirebaseDatabase.getInstance()
        storage = FirebaseStorage.getInstance()

        // Get references to your form fields (EditText, CheckBox, etc.)
        val nameEditText = view.findViewById<EditText>(R.id.name)
        val tcNumberEditText = view.findViewById<EditText>(R.id.tc_number)
        val addressEditText = view.findViewById<EditText>(R.id.address)
        val contactEditText = view.findViewById<EditText>(R.id.contact)
        val issueEditText = view.findViewById<EditText>(R.id.issue)
        val kvkkCheckBox = view.findViewById<CheckBox>(R.id.kvkk_checkbox)
        val uploadPhotoButton = view.findViewById<Button>(R.id.upload_photo)
        val sendButton = view.findViewById<Button>(R.id.send_button)
        imageView2 = view.findViewById(R.id.imageView2)

        uploadPhotoButton.setOnClickListener {
            openGallery()
        }

        sendButton.setOnClickListener {
            sendData(nameEditText, tcNumberEditText, addressEditText, contactEditText, issueEditText, kvkkCheckBox)
        }

        return view
    }

    private fun openGallery() {
        val intent = Intent(Intent.ACTION_PICK, MediaStore.Images.Media.EXTERNAL_CONTENT_URI)
        startActivityForResult(intent, PICK_IMAGE_REQUEST)
    }

    override fun onActivityResult(requestCode: Int, resultCode: Int, data: Intent?) {
        super.onActivityResult(requestCode, resultCode, data)
        if (requestCode == PICK_IMAGE_REQUEST && resultCode == Activity.RESULT_OK && data != null && data.data != null) {
            imageUri = data.data
            imageView2.setImageURI(imageUri)
        }
    }

    private fun sendData(
        nameEditText: EditText,
        tcNumberEditText: EditText,
        addressEditText: EditText,
        contactEditText: EditText,
        issueEditText: EditText,
        kvkkCheckBox: CheckBox
    ) {
        val name = nameEditText.text.toString()
        val tcNumber = tcNumberEditText.text.toString()
        val address = addressEditText.text.toString()
        val contact = contactEditText.text.toString()
        val issue = issueEditText.text.toString()
        val kvkkAccepted = kvkkCheckBox.isChecked

        // Tarihi 'dd.MM.yyyy' formatında al
        val dateFormat = SimpleDateFormat("dd_MM_yyyy", Locale.getDefault())
        val currentDate = dateFormat.format(Date())

        // Anahtarı oluştur
        val key = "${name.replace(" ", "_")}_$currentDate"

        // Veriyi hazırlama
        val formData = mutableMapOf<String, Any>(
            "name" to name,
            "tc_number" to tcNumber,
            "address" to address,
            "contact" to contact,
            "issue" to issue,
            "kvkk_accepted" to kvkkAccepted
        )

        val reference = database.getReference("form_submissions")
        val newReference = reference.child(key)

        if (imageUri != null) {
            uploadImage(key, formData)
        } else {
            newReference.setValue(formData)
                .addOnSuccessListener {
                    sendEmail(name, tcNumber, address, contact, issue)
                }
                .addOnFailureListener { error ->
                    // Hata durumunu ele al
                }
        }
    }

    private fun uploadImage(key: String, formData: MutableMap<String, Any>) {
        val storageRef = storage.reference.child("images/$key.jpg")
        val uploadTask = storageRef.putFile(imageUri!!)

        uploadTask.addOnSuccessListener {
            storageRef.downloadUrl.addOnSuccessListener { uri ->
                formData["image_url"] = uri.toString()
                database.getReference("form_submissions").child(key).setValue(formData)
                    .addOnSuccessListener {
                        sendEmail(
                            formData["name"] as String,
                            formData["tc_number"] as String,
                            formData["address"] as String,
                            formData["contact"] as String,
                            formData["issue"] as String
                        )
                    }
                    .addOnFailureListener { error ->
                        // Handle failure
                    }
            }
        }.addOnFailureListener { error ->
            // Handle failure
        }
    }

    private fun sendEmail(name: String, tcNumber: String, address: String, contact: String, issue: String) {
        val emailIntent = Intent(Intent.ACTION_SEND).apply {
            type = "plain/text"
            putExtra(Intent.EXTRA_EMAIL, arrayOf("tfncan.28@gmail.com"))
            putExtra(Intent.EXTRA_SUBJECT, "Çek Gönder")
            putExtra(Intent.EXTRA_TEXT, "Name: $name\nTC Number: $tcNumber\nAddress: $address\nContact: $contact\nIssue: $issue")
        }

        startActivity(Intent.createChooser(emailIntent, "Send mail..."))
        Toast.makeText(requireContext(), "Lütfen Ekranda Beliren Alanda Mail Uygulamanızı Seçiniz", Toast.LENGTH_SHORT).show()
    }
}

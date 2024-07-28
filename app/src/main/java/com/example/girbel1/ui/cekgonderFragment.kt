package com.example.girbel1.ui

import android.app.Activity
import android.content.Intent
import android.graphics.Bitmap
import android.graphics.drawable.BitmapDrawable
import android.net.Uri
import android.os.Bundle
import android.provider.MediaStore
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.Button
import android.widget.EditText
import android.widget.ImageView
import androidx.fragment.app.Fragment
import com.example.girbel1.R
import java.io.ByteArrayOutputStream
import java.io.IOException

private const val ARG_PARAM1 = "param1"
private const val ARG_PARAM2 = "param2"

class cekgonderFragment : Fragment() {

    private var param1: String? = null
    private var param2: String? = null
    private val PICK_IMAGE_REQUEST = 1
    private lateinit var imageView2: ImageView
    private lateinit var nameEditText: EditText
    private lateinit var contactEditText: EditText
    private lateinit var issueEditText: EditText

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        arguments?.let {
            param1 = it.getString(ARG_PARAM1)
            param2 = it.getString(ARG_PARAM2)
        }
    }

    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {
        // Inflate the layout for this fragment
        val view = inflater.inflate(R.layout.fragment_cekgonder, container, false)

        val uploadPhotoButton: Button = view.findViewById(R.id.upload_photo)
        val sendButton: Button = view.findViewById(R.id.send_button)
        imageView2 = view.findViewById(R.id.imageView2)
        nameEditText = view.findViewById(R.id.name)
        contactEditText = view.findViewById(R.id.contact)
        issueEditText = view.findViewById(R.id.issue)

        uploadPhotoButton.setOnClickListener {
            openGallery()
        }

        sendButton.setOnClickListener {
            sendEmail()
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
            val imageUri: Uri? = data.data
            try {
                val bitmap: Bitmap = MediaStore.Images.Media.getBitmap(activity?.contentResolver, imageUri)
                imageView2.setImageBitmap(bitmap)
            } catch (e: IOException) {
                e.printStackTrace()
            }
        }
    }

    private fun sendEmail() {
        val recipient = "tufancanedu@gmail.com"  // Alıcı e-posta adresi
        val subject = "Gönderim Formu"
        val body = """
            İsim Soyisim: ${nameEditText.text}
            İletişim Adresi: ${contactEditText.text}
            Sorun: ${issueEditText.text}
        """.trimIndent()

        val intent = Intent(Intent.ACTION_SEND).apply {
            type = "vnd.android.cursor.item/email"
            putExtra(Intent.EXTRA_EMAIL, arrayOf(recipient))
            putExtra(Intent.EXTRA_SUBJECT, subject)
            putExtra(Intent.EXTRA_TEXT, body)
            putExtra(Intent.EXTRA_STREAM, getImageUri())
        }

        startActivity(Intent.createChooser(intent, "E-posta ile gönder"))
    }

    private fun getImageUri(): Uri? {
        val bitmap = (imageView2.drawable as? BitmapDrawable)?.bitmap ?: return null
        val bytes = ByteArrayOutputStream()
        bitmap.compress(Bitmap.CompressFormat.PNG, 100, bytes)
        val path = MediaStore.Images.Media.insertImage(activity?.contentResolver, bitmap, "title", null)
        return Uri.parse(path)
    }

    companion object {
        @JvmStatic
        fun newInstance(param1: String, param2: String) =
            cekgonderFragment().apply {
                arguments = Bundle().apply {
                    putString(ARG_PARAM1, param1)
                    putString(ARG_PARAM2, param2)
                }
            }
    }
}

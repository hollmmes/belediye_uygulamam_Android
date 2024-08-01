package com.example.girbel1.ui // Paket adınızı buraya ekleyin

import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.Button
import android.widget.CheckBox
import android.widget.EditText
import androidx.fragment.app.Fragment
import com.example.girbel1.R
import com.google.firebase.database.FirebaseDatabase

class deneme : Fragment() {

    private lateinit var database: FirebaseDatabase

    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {
        val view = inflater.inflate(R.layout.fragment_deneme, container, false)

        // Initialize Firebase Database reference
        database = FirebaseDatabase.getInstance()

        // Get references to your form fields (EditText, CheckBox, etc.)
        val nameEditText = view.findViewById<EditText>(R.id.name)
        val tcNumberEditText = view.findViewById<EditText>(R.id.tc_number)
        val addressEditText = view.findViewById<EditText>(R.id.address)
        val contactEditText = view.findViewById<EditText>(R.id.contact)
        val issueEditText = view.findViewById<EditText>(R.id.issue)
        val kvkkCheckBox = view.findViewById<CheckBox>(R.id.kvkk_checkbox)
        val sendButton = view.findViewById<Button>(R.id.send_button)

        sendButton.setOnClickListener {
            // 1. Get Data from Input Fields
            val name = nameEditText.text.toString()
            val tcNumber = tcNumberEditText.text.toString()
            val address = addressEditText.text.toString()
            val contact = contactEditText.text.toString()
            val issue = issueEditText.text.toString()
            val kvkkAccepted = kvkkCheckBox.isChecked

            // 2. Create a Data Object (POJO or Map)
            val formData = hashMapOf(
                "name" to name,
                "tc_number" to tcNumber,
                "address" to address,
                "contact" to contact,
                "issue" to issue,
                "kvkk_accepted" to kvkkAccepted
            )

            // 3. Push to Firebase Realtime Database
            val reference = database.getReference("form_submissions")
            reference.push().setValue(formData)
                .addOnSuccessListener {
                    // Handle successful submission (e.g., show a toast message)
                }
                .addOnFailureListener { error ->
                    // Handle errors (e.g., log or display an error message)
                }
        }

        return view
    }
}

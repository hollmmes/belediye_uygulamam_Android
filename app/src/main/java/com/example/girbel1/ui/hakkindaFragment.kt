package com.example.girbel1.ui

import android.content.Intent
import android.net.Uri
import android.os.Bundle
import androidx.fragment.app.Fragment
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.Button
import com.example.girbel1.R

class hakkindaFragment : Fragment() {

    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {
        // Inflate the layout for this fragment
        val view = inflater.inflate(R.layout.fragment_hakkinda, container, false)

        // Button 1
        val contactButton1: Button = view.findViewById(R.id.contact_button1)
        contactButton1.setOnClickListener {
            val url = "https://www.linkedin.com/in/tufancan-demirkilic/"
            val intent = Intent(Intent.ACTION_VIEW, Uri.parse(url))
            startActivity(intent)
        }


        return view
    }
}

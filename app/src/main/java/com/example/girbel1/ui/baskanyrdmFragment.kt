package com.example.girbel1.ui

import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.webkit.WebSettings
import android.webkit.WebView
import android.webkit.WebViewClient
import androidx.fragment.app.Fragment
import com.example.girbel1.R

class baskanyrdmFragment : Fragment() {
    private var param1: String? = null
    private var param2: String? = null

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        arguments?.let {
        }
    }

    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {
        // Inflate the layout for this fragment
        val view = inflater.inflate(R.layout.fragment_baskanyrdm, container, false)

        val webView = view.findViewById<WebView>(R.id.webView)
        webView.settings.javaScriptEnabled = true
        webView.settings.domStorageEnabled = true
        webView.webViewClient = object : WebViewClient() {
            override fun onPageFinished(view: WebView?, url: String?) {
                super.onPageFinished(view, url)
                // JavaScript kodu ile header'ı gizle
                webView.evaluateJavascript(
                    "document.querySelector('header').style.display='none';",
                    null
                )
            }
        }
        webView.loadUrl("https://giresun.bel.tr/baskan-yardimcilari/")

        return view
    }

    companion object {
        @JvmStatic
        fun newInstance(param1: String, param2: String) =
            baskanyrdmFragment().apply {
                arguments = Bundle().apply {
                }
            }
    }
}

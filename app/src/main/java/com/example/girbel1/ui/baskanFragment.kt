package com.example.girbel1.ui

import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.webkit.WebView
import android.webkit.WebViewClient
import androidx.fragment.app.Fragment
import com.example.girbel1.R

class baskanFragment : Fragment() {

    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {
        val view = inflater.inflate(R.layout.fragment_baskan, container, false)

        // WebView'i bul ve ayarla
        val webView: WebView = view.findViewById(R.id.webView)
        webView.webViewClient = WebViewClient() // Linklerin yeni sekmede açılmasını engeller
        webView.settings.javaScriptEnabled = true // JavaScript'i etkinleştirir

        // URL'yi yükle
        webView.loadUrl("https://giresun.bel.tr/baskanin-ozgecmisi/")

        // Sayfa tamamen yüklendiğinde başlığı gizle
        webView.webViewClient = object : WebViewClient() {
            override fun onPageFinished(view: WebView?, url: String?) {
                super.onPageFinished(view, url)
                view?.evaluateJavascript(
                    """
                    document.querySelector('header').style.display = 'none';
                    """.trimIndent(), null
                )
            }
        }

        return view
    }

    companion object {
        @JvmStatic
        fun newInstance(param1: String, param2: String) =
            baskanFragment().apply {
                arguments = Bundle().apply {
                }
            }
    }
}

package com.example.girbel1.ui

import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.webkit.WebView
import android.webkit.WebViewClient
import androidx.fragment.app.Fragment
import com.example.girbel1.R

class VefatFragment : Fragment() {

    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {
        val view = inflater.inflate(R.layout.fragment_vefat, container, false)

        val webView = view.findViewById<WebView>(R.id.webView)
        webView.settings.javaScriptEnabled = true
        webView.settings.domStorageEnabled = true
        webView.webViewClient = object : WebViewClient() {
            override fun onPageFinished(view: WebView?, url: String?) {
                super.onPageFinished(view, url)
                // JavaScript kodunu ekliyoruz
                val javascript = """
                    javascript:(function() {
                        var header = document.querySelector("header");
                        if (header) {
                            header.style.display = 'none';
                        } else {
                            console.log('Header bulunamadı');
                        }
                    })();
                """
                webView.evaluateJavascript(javascript, null)
            }
        }

        // URL'yi yükle
        val url = "https://giresun.bel.tr/category/vefat-ilanlari/"
        webView.loadUrl(url)

        return view
    }
}

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

class canliFragment : Fragment() {

    private lateinit var webView: WebView

    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {
        // Inflate the layout for this fragment
        val view = inflater.inflate(R.layout.fragment_canli, container, false)
        webView = view.findViewById(R.id.webView)
        setupWebView()
        return view
    }

    private fun setupWebView() {
        webView.webViewClient = WebViewClient()
        webView.settings.javaScriptEnabled = true
        webView.settings.cacheMode = WebSettings.LOAD_NO_CACHE

        val js = """
            (function() {
                document.querySelector('header').style.display='none';
                document.querySelector('#footer').style.display='none';
                document.querySelector('#main').style.transform='none';
            })();
        """

        webView.loadUrl("https://www.giresunkentkonseyi.org/giresun-canli-yayin/")
        webView.webViewClient = object : WebViewClient() {
            override fun onPageFinished(view: WebView?, url: String?) {
                super.onPageFinished(view, url)
                webView.evaluateJavascript(js, null)
            }
        }
    }

}

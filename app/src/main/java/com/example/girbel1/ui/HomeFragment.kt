package com.example.girbel1.ui

import android.content.Intent
import android.net.Uri
import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.webkit.WebView
import android.webkit.WebViewClient
import androidx.fragment.app.Fragment
import androidx.navigation.fragment.findNavController
import com.example.girbel1.R
import com.example.girbel1.databinding.FragmentHomeBinding

class HomeFragment : Fragment() {

    private var _binding: FragmentHomeBinding? = null
    private val binding get() = _binding!!

    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {
        _binding = FragmentHomeBinding.inflate(inflater, container, false)
        val view = binding.root

        val webView = binding.webView
        webView.settings.javaScriptEnabled = true
        webView.settings.domStorageEnabled = true
        webView.webViewClient = object : WebViewClient() {
            override fun onPageFinished(view: WebView?, url: String?) {
                super.onPageFinished(view, url)
                // JavaScript kodunu ekliyoruz
                val javascript = """
    javascript:(function() {
        var element = document.querySelector(".TumSli");
        if (element) {
            document.body.innerHTML = "";
            document.body.appendChild(element);
            document.body.style.overflow = 'hidden';
        } else {
            console.log('Element bulunamadı');
        }

        // .owl-dots sınıfını kaldır
        var dots = document.querySelectorAll(".owl-dots");
        dots.forEach(function(dot) {
            dot.style.display = 'none';
        });
    })();
"""
                webView.evaluateJavascript(javascript, null)
            }

            override fun shouldOverrideUrlLoading(view: WebView?, url: String?): Boolean {
                if (url != null) {
                    val intent = Intent(Intent.ACTION_VIEW, Uri.parse(url))
                    startActivity(intent)
                    return true
                }
                return false
            }
        }

        // URL'yi yükle
        val url = "https://giresun.bel.tr"
        webView.loadUrl(url)

        // Buton tıklama olaylarını ekleyin
        binding.yayinCardView.setOnClickListener {
            findNavController().navigate(R.id.action_homeFragment_to_yayinFragment)
        }
        binding.baskanCardView.setOnClickListener {
            findNavController().navigate(R.id.action_homeFragment_to_baskanFragment)
        }
        binding.contactCardView.setOnClickListener {
            findNavController().navigate(R.id.action_homeFragment_to_iletisimFragment)
        }
        binding.eczaneCardView.setOnClickListener {
            findNavController().navigate(R.id.action_homeFragment_to_eczFragment)
        }
        binding.vefatCardView.setOnClickListener {
            findNavController().navigate(R.id.action_homeFragment_to_vefatFragment)
        }
        binding.belediyeCardview.setOnClickListener {
            val url = "https://online.giresun.bel.tr/ebelediye/#"
            val intent = Intent(Intent.ACTION_VIEW, Uri.parse(url))
            startActivity(intent)
        }

        return view
    }

    override fun onDestroyView() {
        super.onDestroyView()
        _binding = null
    }
}

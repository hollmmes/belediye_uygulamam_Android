package com.example.girbel1.ui

import android.content.Intent
import android.os.Bundle
import android.os.Handler
import android.os.Looper
import androidx.appcompat.app.AppCompatActivity
import com.example.girbel1.MainActivity
import com.example.girbel1.R

class SplashActivity : AppCompatActivity() {

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_splash)
        supportActionBar?.hide()
        // Splash screen'in ne kadar süre gösterileceğini belirleyin (ms cinsinden)
        val splashScreenDuration = 2000L // 3 saniye

        Handler(Looper.getMainLooper()).postDelayed({
            // MainActivity'ye geçiş yap
            val intent = Intent(this, MainActivity::class.java)
            startActivity(intent)
            finish()
        }, splashScreenDuration)
    }
}
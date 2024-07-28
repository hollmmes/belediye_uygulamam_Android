package com.example.girbel1

import android.content.Intent
import android.net.Uri
import android.os.Bundle
import androidx.appcompat.app.AppCompatActivity
import androidx.core.view.ViewCompat
import androidx.core.view.WindowInsetsCompat
import androidx.drawerlayout.widget.DrawerLayout
import androidx.fragment.app.Fragment
import com.example.girbel1.ui.*
import com.google.android.material.bottomnavigation.BottomNavigationView
import com.google.android.material.floatingactionbutton.FloatingActionButton
import com.google.android.material.navigation.NavigationView

class MainActivity : AppCompatActivity() {

    private lateinit var navview: BottomNavigationView
    private lateinit var cekgonderButton: FloatingActionButton
    private lateinit var drawerLayout: DrawerLayout
    private lateinit var navigationView: NavigationView

    private val homeFragment = HomeFragment()
    private val feedFragment = feedFragment()
    private val cekgonderFragment = cekgonderFragment()
    private val baskanyrdmFragment = baskanyrdmFragment()
    private val vizyonFragment = vizyonFragment()
    private val meclisFragment = meclisFragment()
    private val kardesFragment = kardesFragment()
    private val imarFragment = imarFragment()
    private val duyuruFragment = duyuruFragment()
    private val hakkindaFragment = hakkindaFragment()

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_main)

        // Durum çubuğunun gizlenmesi
        supportActionBar?.hide()

        // Drawer ve NavigationView tanımlamaları
        drawerLayout = findViewById(R.id.drawer_layout)
        navigationView = findViewById(R.id.nav_view_drawer)

        // Bottom nav'ın tanımlanması
        navview = findViewById(R.id.nav_view)
        cekgonderButton = findViewById(R.id.cekgonder)

        // Fragment'leri ekleyip ilk olarak HomeFragment'i gösteriyoruz
        if (savedInstanceState == null) {
            supportFragmentManager.beginTransaction()
                .add(R.id.navhostbottom, homeFragment, "HOME")
                .add(R.id.navhostbottom, feedFragment, "FEED").hide(feedFragment)
                .add(R.id.navhostbottom, cekgonderFragment, "CEKGONDER").hide(cekgonderFragment)
                .add(R.id.navhostbottom, baskanyrdmFragment, "BASKANYRDM").hide(baskanyrdmFragment)
                .add(R.id.navhostbottom, vizyonFragment, "VIZYON").hide(vizyonFragment)
                .add(R.id.navhostbottom, meclisFragment, "MECLIS").hide(meclisFragment)
                .add(R.id.navhostbottom, kardesFragment, "KARDES").hide(kardesFragment)
                .add(R.id.navhostbottom, imarFragment, "IMAR").hide(imarFragment)
                .add(R.id.navhostbottom, duyuruFragment, "DUYURU").hide(duyuruFragment)
                .add(R.id.navhostbottom, hakkindaFragment, "DUYURU").hide(hakkindaFragment)
                .commit()
        }

        // BottomNavigationView için dinleyici ekleyin
        navview.setOnItemSelectedListener {
            when (it.itemId) {
                R.id.nav_home -> {
                    // Restart MainActivity
                    val intent = Intent(this, MainActivity::class.java)
                    startActivity(intent)
                    finish()
                }
                R.id.nav_call -> {
                    val intent = Intent(Intent.ACTION_DIAL)
                    intent.data = Uri.parse("tel:4444028")
                    startActivity(intent)
                }
                R.id.nav_anket -> showFragment(feedFragment)
            }
            true
        }

        cekgonderButton.setOnClickListener {
            showFragment(cekgonderFragment)
        }

        // Navigation Drawer için menü öğeleri dinleyicisi
        navigationView.setNavigationItemSelectedListener {
            when (it.itemId) {
                R.id.bsknyrdmclari -> showFragment(baskanyrdmFragment)
                R.id.vizyon -> showFragment(vizyonFragment)
                R.id.meclis -> showFragment(meclisFragment)
                R.id.kardes -> showFragment(kardesFragment)
                R.id.duyuru -> showFragment(duyuruFragment)
                R.id.imar -> showFragment(imarFragment)
                R.id.hakkinda -> showFragment(hakkindaFragment)

                // Diğer menü öğeleri için işlemler
            }
            drawerLayout.closeDrawers()
            true
        }

        enableEdgeToEdge()
        ViewCompat.setOnApplyWindowInsetsListener(findViewById(R.id.main)) { v, insets ->
            val systemBars = insets.getInsets(WindowInsetsCompat.Type.systemBars())
            v.setPadding(systemBars.left, systemBars.top, systemBars.right, systemBars.bottom)
            insets
        }
    }

    private fun showFragment(fragment: Fragment) {
        val fragmentTransaction = supportFragmentManager.beginTransaction()
        supportFragmentManager.fragments.forEach {
            if (it == fragment) {
                fragmentTransaction.show(it)
            } else {
                fragmentTransaction.hide(it)
            }
        }
        fragmentTransaction.commit()
    }

    private fun enableEdgeToEdge() {
        // Boş bırakılmış olan enableEdgeToEdge fonksiyonunun içeriği buraya yazılabilir
    }
}

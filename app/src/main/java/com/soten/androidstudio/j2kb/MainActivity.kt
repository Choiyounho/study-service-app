package com.soten.androidstudio.j2kb

import android.os.Bundle
import androidx.appcompat.app.AppCompatActivity
import androidx.fragment.app.Fragment
import androidx.navigation.findNavController
import androidx.navigation.ui.setupWithNavController
import com.google.android.material.bottomnavigation.BottomNavigationView
import com.jakewharton.threetenabp.AndroidThreeTen
import com.soten.androidstudio.j2kb.utils.BackPressed

class MainActivity : AppCompatActivity(), BackPressed{

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setTheme(R.style.AppTheme)
        setContentView(R.layout.activity_main)

        AndroidThreeTen.init(this)
        val navView: BottomNavigationView = findViewById(R.id.nav_view)


        val navController = findNavController(R.id.nav_host_fragment)
        navView.setupWithNavController(navController)
    }

    override fun backPress() {
        super.onBackPressed()

        val fragmentList: List<Fragment> = supportFragmentManager.fragments
        for (fragment in fragmentList) {
            if (fragment is BackPressed) {
                (fragment as BackPressed).backPress()
                return
            }
        }
    }

}
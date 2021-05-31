package com.soten.androidstudio.j2kb

import android.content.Intent
import android.os.Bundle
import android.util.Log
import android.view.Menu
import android.view.MenuInflater
import android.view.MenuItem
import androidx.appcompat.app.AppCompatActivity
import androidx.fragment.app.Fragment
import androidx.navigation.findNavController
import androidx.navigation.ui.setupWithNavController
import com.google.android.material.bottomnavigation.BottomNavigationView
import com.jakewharton.threetenabp.AndroidThreeTen
import com.soten.androidstudio.j2kb.utils.BackPressed
import com.soten.androidstudio.j2kb.utils.CommonsConstant

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

    override fun onCreateOptionsMenu(menu: Menu?): Boolean {
        val inflater: MenuInflater = menuInflater
        inflater.inflate(R.menu.main, menu)
        return super.onCreateOptionsMenu(menu)
    }

    override fun onOptionsItemSelected(item: MenuItem): Boolean {
        return when (item.itemId) {
            R.id.action_logout -> {

                true
            }
            else -> super.onOptionsItemSelected(item)
        }
    }

}
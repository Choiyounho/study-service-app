package com.soten.androidstudio.j2kb

import android.os.Bundle
import android.widget.EditText
import android.widget.Toast
import androidx.appcompat.app.AppCompatActivity
import androidx.fragment.app.Fragment
import androidx.navigation.findNavController
import androidx.navigation.ui.setupWithNavController
import com.google.android.material.bottomnavigation.BottomNavigationView
import com.jakewharton.threetenabp.AndroidThreeTen
import com.soten.androidstudio.j2kb.model.post.Communicator
import com.soten.androidstudio.j2kb.utils.BackPressed
import com.soten.androidstudio.j2kb.view.home.notice.NoticeFragment
import com.soten.androidstudio.j2kb.view.home.notice.post.NoticePostFragment

class MainActivity : AppCompatActivity(), BackPressed{

    var lastTimeBackPressed: Long = 0

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setTheme(R.style.AppTheme)
        setContentView(R.layout.activity_main)

        AndroidThreeTen.init(this)
        val navView: BottomNavigationView = findViewById(R.id.nav_view)
//
//        val title = findViewById<EditText>(R.id.notice_post_title)
//
//        supportFragmentManager.beginTransaction().replace(
//            R.id.nav_host_fragment,
//            NoticePostFragment().apply {
//                arguments = Bundle().apply {
//                    putString("KEY", title.toString())
//                }
//            }
//        ).commit()

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
        if (System.currentTimeMillis() - lastTimeBackPressed >= 1500) {
            lastTimeBackPressed = System.currentTimeMillis()
            Toast.makeText(this, "'뒤로' 버튼을 한번 더 누르시면 종료됩니다.", Toast.LENGTH_LONG).show()
        } else {
            finish()
        }
    }

}
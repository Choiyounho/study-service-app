package com.soten.androidstudio.j2kb

import android.content.Intent
import android.os.Bundle
import androidx.appcompat.app.AppCompatActivity

class SplashActivity : AppCompatActivity() {
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_splash)

        Thread.sleep(1000L)

        val intent = Intent(this@SplashActivity, LoginActivity::class.java)
        startActivity(intent)
    }
}
package com.soten.androidstudio.j2kb

import android.content.Intent
import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import android.widget.ImageView

class SplashActivity : AppCompatActivity() {
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_splash)

        Thread.sleep(1000L)

        val intent = Intent(this@SplashActivity, MainActivity::class.java)
        startActivity(intent)
    }
}
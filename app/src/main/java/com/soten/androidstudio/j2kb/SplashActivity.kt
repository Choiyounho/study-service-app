package com.soten.androidstudio.j2kb

import android.content.Intent
import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import android.util.Log
import android.widget.ImageView
import com.kakao.sdk.common.util.Utility

class SplashActivity : AppCompatActivity() {
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_splash)

        Thread.sleep(1000L)


        val intent = Intent(this@SplashActivity, LoginActivity::class.java)
        startActivity(intent)
    }
}
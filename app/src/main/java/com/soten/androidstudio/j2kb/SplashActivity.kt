package com.soten.androidstudio.j2kb

import android.content.Intent
import android.os.Bundle
import androidx.appcompat.app.AppCompatActivity
import com.google.firebase.auth.FirebaseAuth
import com.google.firebase.auth.ktx.auth
import com.google.firebase.ktx.Firebase

class SplashActivity : AppCompatActivity() {

    private lateinit var auth: FirebaseAuth

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_splash)

        auth = Firebase.auth

        Thread.sleep(1000L)

    }

    override fun onStart() {
        super.onStart()
        auth.currentUser?.let {
            val intent = Intent(this, MainActivity::class.java)
            startActivity(intent)
        } ?: run {
            val intent = Intent(this, LoginActivity::class.java)
            startActivity(intent)
        }
    }
}
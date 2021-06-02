package com.soten.androidstudio.j2kb

import android.content.Intent
import android.os.Bundle
import android.util.Log
import androidx.appcompat.app.AppCompatActivity
import com.google.firebase.auth.FirebaseAuth
import com.google.firebase.auth.ktx.auth
import com.google.firebase.ktx.Firebase
import com.soten.androidstudio.j2kb.utils.CommonsConstant

class SplashActivity : AppCompatActivity() {

    private lateinit var auth: FirebaseAuth

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)

        auth = Firebase.auth

        Thread.sleep(1000L)
    }

    override fun onStart() {
        super.onStart()
        auth.currentUser?.let {
            val intent = Intent(this, MainActivity::class.java)
            Log.d(CommonsConstant.TAG, "유저 있음")
            startActivity(intent)
            finish()
        } ?: run {
            Log.d(CommonsConstant.TAG, "유저 없음")
            val intent = Intent(this, LoginActivity::class.java)
            startActivity(intent)
            finish()
        }
    }
}
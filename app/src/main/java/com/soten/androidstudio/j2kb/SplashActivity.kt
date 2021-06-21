package com.soten.androidstudio.j2kb

import android.content.Intent
import android.os.Bundle
import android.util.Log
import android.widget.Toast
import androidx.appcompat.app.AppCompatActivity
import com.google.firebase.auth.FirebaseAuth
import com.google.firebase.auth.ktx.auth
import com.google.firebase.firestore.ktx.firestore
import com.google.firebase.ktx.Firebase
import com.soten.androidstudio.j2kb.utils.CommonsConstant
import com.soten.androidstudio.j2kb.utils.DBKey.Companion.DB_USERS

class SplashActivity : AppCompatActivity() {

    private lateinit var auth: FirebaseAuth

    private val store = Firebase.firestore

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)

        auth = Firebase.auth

        Thread.sleep(1000L)
    }

    override fun onStart() {
        super.onStart()
        auth.currentUser?.let {
            store.collection(DB_USERS)
                .document(it.uid)
                .get()
                .addOnSuccessListener { document ->
                    if (!document.exists()) {
                        Toast.makeText(this, "회원 가입 페이지로 이동합니다", Toast.LENGTH_SHORT).show()
                        val intent = Intent(this, LoginActivity::class.java)
                        startActivity(intent)
                        finish()
                    } else {
                        val intent = Intent(this, MainActivity::class.java)
                        Log.d(CommonsConstant.TAG, "document ${document.data}")
                        startActivity(intent)
                        finish()
                    }
                }

        } ?: run {
            val intent = Intent(this, LoginActivity::class.java)
            startActivity(intent)
            finish()
        }
    }
}
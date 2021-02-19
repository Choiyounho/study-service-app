package com.soten.androidstudio.j2kb

import android.content.Intent
import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import android.util.Log
import android.widget.ImageButton
import android.widget.ImageView
import com.kakao.sdk.auth.LoginClient
import com.kakao.sdk.auth.model.OAuthToken

class LoginActivity : AppCompatActivity() {
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_login)

        val callback: ((OAuthToken?, Throwable?) -> Unit) = { token, error ->
            if (error != null) {
                Log.d("TAG", "kakao loing Failed : ", error)
            }
            if (token != null) {
                startMainActivity()
            }
        }

        val loginBtn = findViewById<ImageView>(R.id.btn_kakao_login)
        loginBtn.setOnClickListener {
            LoginClient.instance.run {
                if (isKakaoTalkLoginAvailable(this@LoginActivity)) {
                    loginWithKakaoTalk(this@LoginActivity, callback = callback)
                } else {
                    loginWithKakaoAccount(this@LoginActivity, callback = callback)
                }
            }
        }
    }

    private fun startMainActivity() {
        startActivity(Intent(this@LoginActivity, MainActivity::class.java))
    }

}
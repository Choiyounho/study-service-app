package com.soten.androidstudio.j2kb

import android.content.Intent
import android.os.Bundle
import android.util.Log
import android.widget.ImageView
import androidx.appcompat.app.AppCompatActivity
import com.kakao.sdk.auth.LoginClient
import com.kakao.sdk.auth.model.OAuthToken
import com.kakao.sdk.user.UserApiClient
import com.soten.androidstudio.j2kb.utils.CommonsConstant


class LoginActivity : AppCompatActivity() {

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_login)

        val loginBtn = findViewById<ImageView>(R.id.btn_kakao_login)
        loginBtn.setOnClickListener {
            LoginClient.instance.run {
                if (isKakaoTalkLoginAvailable(this@LoginActivity)) {
                    loginWithKakaoTalk(this@LoginActivity, callback = callback())
                } else {
                    loginWithKakaoAccount(this@LoginActivity, callback = callback())
                }
            }
        }
    }

    private fun callback(): (OAuthToken?, Throwable?) -> Unit {
        return { token, error ->
            if (error != null) {
                Log.e(CommonsConstant.TAG, "kakao login Failed : ", error)
            }
            if (token != null) {
                startMainActivity()
                UserApiClient.instance.me { user, _ ->
                    user?.let {
                        Log.i(
                            CommonsConstant.TAG,
                            "updateLoginInfo: ${user.id} ${user.kakaoAccount?.email} ${user.kakaoAccount?.profile?.nickname} ${user.kakaoAccount?.profile?.thumbnailImageUrl}"
                        )
                    }

                }
            }
        }
    }

    private fun startMainActivity() {
        startActivity(Intent(this@LoginActivity, MainActivity::class.java))
    }

}
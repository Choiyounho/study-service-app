package com.soten.androidstudio.j2kb

import android.app.Application
import com.kakao.sdk.common.KakaoSdk

class KakaoApplication : Application() {

    override fun onCreate() {
        super.onCreate()

        KakaoSdk.init(this@KakaoApplication, getString(R.string.kakao_native_key))
    }
}
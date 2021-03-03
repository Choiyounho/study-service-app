package com.soten.androidstudio.j2kb.utils.kakao

import android.app.Application
import com.kakao.sdk.common.KakaoSdk
import com.soten.androidstudio.j2kb.R

class KakaoApplication : Application() {

    override fun onCreate() {
        super.onCreate()

        KakaoSdk.init(this@KakaoApplication, getString(R.string.kakao_native_key))
    }
}
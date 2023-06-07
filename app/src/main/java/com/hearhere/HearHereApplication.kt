package com.hearhere

import android.app.Application
import android.util.Log
import com.kakao.sdk.common.KakaoSdk
import com.kakao.sdk.common.util.Utility
import dagger.hilt.android.HiltAndroidApp

@HiltAndroidApp
class HearHereApplication : Application() {
    private val KAKAO_KEY = BuildConfig.KAKAO_APP_KEY

    override fun onCreate() {
        super.onCreate()
        KakaoSdk.init(this, KAKAO_KEY)
        val key = Utility.getKeyHash(this)
        Log.d("ok hash",key)
    }
}
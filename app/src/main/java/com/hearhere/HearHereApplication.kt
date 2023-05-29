package com.hearhere

import android.app.Application
import com.kakao.sdk.common.KakaoSdk
import dagger.hilt.android.HiltAndroidApp

@HiltAndroidApp
class HearHereApplication : Application() {
    private val KAKAO_KEY = BuildConfig.KAKAO_APP_KEY

    override fun onCreate() {
        super.onCreate()
        KakaoSdk.init(this, KAKAO_KEY)
    }
}
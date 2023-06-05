package com.hearhere.data.data.network

import android.content.Context
import android.util.Log
import com.hearhere.data.data.local.HearHerePrefKeys
import com.hearhere.data.di.dataStore
import com.hearhere.domain.repository.PreferenceRepository
import kotlinx.coroutines.flow.first
import kotlinx.coroutines.flow.map
import kotlinx.coroutines.runBlocking
import okhttp3.Interceptor
import okhttp3.Response
import javax.inject.Inject

class APIInterceptor @Inject constructor(
    val repository: PreferenceRepository
): Interceptor {
    override fun intercept(chain: Interceptor.Chain): Response {
        var token  = runBlocking {
            repository.getAuthToken().accessToken
        }
        Log.d("header request token",token.toString())

        var request = chain.request()
        request = request?.newBuilder()
            ?.addHeader("Content-Type", "application/json")
            ?.addHeader("Accept", "application/json")
            ?.addHeader("Authorization","Bearer ${token}")
            ?.build()!!
        Log.d("header request",request.toString())
        return chain.proceed(request)
    }
}
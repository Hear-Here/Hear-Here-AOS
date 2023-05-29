package com.hearhere.data.data.network

import com.hearhere.data.data.dto.request.AuthRequest
import com.hearhere.data.data.dto.response.AuthResponse
import com.hearhere.domain.model.ApiResponse
import retrofit2.Response

import retrofit2.http.Body
import retrofit2.http.POST

interface HearHereApiService {
    //Server 통신 부분
    @POST("")
    suspend fun signup(): ApiResponse<String>

    @POST("/login/kakao")
    suspend fun login(
        @Body token : AuthRequest
    ): Response<AuthResponse>
}





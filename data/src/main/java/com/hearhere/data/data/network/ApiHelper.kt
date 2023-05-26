package com.hearhere.data.data.network

import com.hearhere.data.data.dto.request.AuthRequest
import com.hearhere.data.data.dto.response.AuthResponse
import retrofit2.Response

interface ApiHelper {
    suspend fun login(token:AuthRequest) : Response<AuthResponse>
}
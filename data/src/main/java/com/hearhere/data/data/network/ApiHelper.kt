package com.hearhere.data.data.network

import com.hearhere.data.data.dto.request.AuthRequest
import com.hearhere.data.data.dto.response.AuthResponse
import com.hearhere.data.data.dto.response.PostListResponse
import retrofit2.Response

interface ApiHelper {
    suspend fun login(token:AuthRequest) : Response<AuthResponse>
    suspend fun getPostList(lat : Double, lng : Double) : Response<PostListResponse>
}
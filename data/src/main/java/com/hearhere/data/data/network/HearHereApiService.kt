package com.hearhere.data.data.network

import com.hearhere.data.data.dto.request.AuthRequest
import com.hearhere.data.data.dto.response.AuthResponse
import com.hearhere.data.data.dto.response.PostListResponse
import com.hearhere.domain.model.ApiResponse
import retrofit2.Response

import retrofit2.http.Body
import retrofit2.http.GET
import retrofit2.http.POST
import retrofit2.http.Path
import retrofit2.http.Query

interface HearHereApiService {
    //Server 통신 부분
    @POST("")
    suspend fun signup(): ApiResponse<String>

    @POST("/login/kakao")
    suspend fun login(
        @Body token : AuthRequest
    ): Response<AuthResponse>

    @GET("/post/list")
    suspend fun getPostList(
      @Query("latitude") latitude : Double,
      @Query("longitude") longitude : Double,
    ) : Response<PostListResponse>
}





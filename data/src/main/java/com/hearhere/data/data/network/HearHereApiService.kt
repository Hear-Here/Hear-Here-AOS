package com.hearhere.data.data.network

import com.hearhere.data.data.dto.request.AuthRequest
import com.hearhere.data.data.dto.response.AuthResponse
import com.hearhere.data.data.dto.response.LikePostItem
import com.hearhere.data.data.dto.response.LikePostListResponse
import com.hearhere.data.data.dto.response.PostItemResponse
import com.hearhere.data.data.dto.response.PostListResponse
import com.hearhere.domain.model.ApiResponse
import retrofit2.Response

import retrofit2.http.Body
import retrofit2.http.DELETE
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
    ) : Response<List<PostItemResponse>>


    @GET("/post/{postId}")
    suspend fun getPost(
        @Path("postId") postId : Long,
        @Query("latitude") latitude : Double,
        @Query("longitude") longitude : Double,
    ):Response<PostItemResponse>

    @DELETE("/post/{postId}")
    suspend fun deletePost(
        @Path("postId") postId : Long
    ):Response<*>


    @POST("/post/{postId}/heart")
    suspend fun likePost(
        @Path("postId") postId : Long,
    ):Response<*>


    @DELETE("/post/{postId}/heart")
    suspend fun disLikePost(
        @Path("postId") postId : Long
    ):Response<*>

    @GET("/post/heart/list")
    suspend fun getLikePostList(
        @Query("latitude") latitude : Double,
        @Query("longitude") longitude : Double,
    ):Response<List<LikePostItem>>
}





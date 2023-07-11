package com.hearhere.data.data.network

import com.hearhere.data.data.dto.request.AuthRequest
import com.hearhere.data.data.dto.request.PostRequest
import com.hearhere.data.data.dto.response.AuthResponse
import com.hearhere.data.data.dto.response.LikePostItem
import com.hearhere.data.data.dto.response.MyPostListResponse
import com.hearhere.data.data.dto.response.PostItemResponse
import com.hearhere.domain.model.ApiResponse
import retrofit2.Response
import retrofit2.http.Body
import retrofit2.http.DELETE
import retrofit2.http.GET
import retrofit2.http.PATCH
import retrofit2.http.POST
import retrofit2.http.Path
import retrofit2.http.Query

interface HearHereApiService {
    // Server 통신 부분
    @POST("")
    suspend fun signup(): ApiResponse<String>

    @POST("/user/login/kakao")
    suspend fun login(
        @Body token: AuthRequest
    ): Response<AuthResponse>

// latitude=&longtitude=&genreType=&weatherType=&emotionType=&withType=
    @GET("/post/list-map")
    suspend fun getPostList(
        @Query("latitude") latitude: Double,
        @Query("longitude") longitude: Double,
        @Query("genreType") genreType: String?,
        @Query("weatherType") weatherType: String?,
        @Query("withType") withType: String?,
        @Query("emotionType") emotionType: String?
    ): Response<List<PostItemResponse>>

    @GET("/post/{postId}")
    suspend fun getPost(
        @Path("postId") postId: Long,
        @Query("latitude") latitude: Double,
        @Query("longitude") longitude: Double
    ): Response<PostItemResponse>

    @DELETE("/post/{postId}")
    suspend fun deletePost(
        @Path("postId") postId: Long
    ): Response<*>

    @POST("/post/{postId}/heart")
    suspend fun likePost(
        @Path("postId") postId: Long
    ): Response<*>

    @DELETE("/post/{postId}/heart")
    suspend fun disLikePost(
        @Path("postId") postId: Long
    ): Response<*>

    @GET("/post/heart/list")
    suspend fun getLikePostList(
        @Query("latitude") latitude: Double,
        @Query("longitude") longitude: Double
    ): Response<List<LikePostItem>>

    @GET("/post/my/list")
    suspend fun getMyPostList(
        @Query("latitude") latitude: Double,
        @Query("longitude") longitude: Double
    ): Response<List<MyPostListResponse>>

    @PATCH("/user/nickname/{nickname}")
    suspend fun setNickName(
        @Path("nickname") nickname: String
    ): Response<*>

    @POST("/post")
    suspend fun postMusicPosting(
        @Body body: PostRequest
    ): Response<*>
}

package com.hearhere.data.data.network

import com.hearhere.data.data.dto.request.AuthRequest
import com.hearhere.data.data.dto.response.AuthResponse
import com.hearhere.data.data.dto.response.LikePostItem
import com.hearhere.data.data.dto.response.LikePostListResponse
import com.hearhere.data.data.dto.response.PostItemResponse
import com.hearhere.data.data.dto.response.PostListResponse
import retrofit2.Response

interface ApiHelper {
    suspend fun login(token:AuthRequest) : Response<AuthResponse>
    suspend fun getPostList(lat : Double, lng : Double) : Response<List<PostItemResponse>>
    suspend fun getPost(postId : Long,lat: Double, lng: Double) : Response<PostItemResponse>
    suspend fun deletePost(postId: Long) : Response<*>
    suspend fun likePost(postId: Long) : Response<*>
    suspend fun disLikePost(postId: Long) : Response<*>
    suspend fun getLikePostList(lat : Double,lng: Double) : Response<List<LikePostItem>>
}
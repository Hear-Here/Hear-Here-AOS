package com.hearhere.data.data.network

import com.hearhere.data.data.dto.request.AuthRequest
import com.hearhere.data.data.dto.response.AuthResponse
import com.hearhere.data.data.dto.response.LikePostItem
import com.hearhere.data.data.dto.response.LikePostListResponse
import com.hearhere.data.data.dto.response.PostItemResponse
import com.hearhere.data.data.dto.response.PostListResponse
import com.hearhere.data.di.NetworkModule
import retrofit2.Response
import javax.inject.Inject

class ApiHelperImpl @Inject constructor(
    @NetworkModule.api val apiService: HearHereApiService
) : ApiHelper {
    override suspend fun login(request: AuthRequest): Response<AuthResponse> = apiService.login(request)
    override suspend fun getPostList(lat: Double, lng: Double) : Response<List<PostItemResponse>>
    = apiService.getPostList(lat,lng)

    override suspend fun getPost(postId: Long, lat: Double, lng: Double): Response<PostItemResponse>  = apiService.getPost(postId,lat, lng)
    override suspend fun deletePost(postId: Long): Response<*>  = apiService.deletePost(postId)

    override suspend fun likePost(postId: Long): Response<*>  = apiService.likePost(postId)
    override suspend fun disLikePost(postId: Long): Response<*>  = apiService.disLikePost(postId)
    override suspend fun getLikePostList(lat: Double,lng: Double): Response<List<LikePostItem>> = apiService.getLikePostList(lat,lng)

}
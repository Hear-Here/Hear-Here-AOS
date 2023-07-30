package com.hearhere.data.data.network

import com.hearhere.data.data.dto.request.AuthRequest
import com.hearhere.data.data.dto.request.PostListRequest
import com.hearhere.data.data.dto.request.PostRequest
import com.hearhere.data.data.dto.response.AuthResponse
import com.hearhere.data.data.dto.response.LikePostItem
import com.hearhere.data.data.dto.response.MyPostListResponse
import com.hearhere.data.data.dto.response.PostItemResponse
import com.hearhere.data.di.NetworkModule
import retrofit2.Response
import javax.inject.Inject

class ApiHelperImpl @Inject constructor(
    @NetworkModule.api val apiService: HearHereApiService
) : ApiHelper {
    override suspend fun login(request: AuthRequest): Response<AuthResponse> = apiService.login(request)
    override suspend fun getPostList(req : PostListRequest): Response<List<PostItemResponse>> =
        apiService.getPostList(
            latitude = req.lat,
            longitude = req.lng,
            genreType = req.genreType,
            weatherType = req.weatherType,
            withType = req.withType,
            emotionType = req.emotionType
        )

    override suspend fun getPost(postId: Long, lat: Double, lng: Double): Response<PostItemResponse> = apiService.getPost(postId, lat, lng)
    override suspend fun deletePost(postId: Long): Response<*> = apiService.deletePost(postId)

    override suspend fun likePost(postId: Long): Response<*> = apiService.likePost(postId)
    override suspend fun disLikePost(postId: Long): Response<*> = apiService.disLikePost(postId)
    override suspend fun getLikePostList(lat: Double, lng: Double): Response<List<LikePostItem>> = apiService.getLikePostList(lat, lng)
    override suspend fun getMyPostList(lat: Double, lng: Double): Response<List<MyPostListResponse>> = apiService.getMyPostList(lat, lng)
    override suspend fun setNickName(name: String): Response<*> = apiService.setNickName(name)
    override suspend fun postMusicPosting(body: PostRequest): Response<*> = apiService.postMusicPosting(body)
}

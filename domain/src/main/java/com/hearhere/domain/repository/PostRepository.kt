package com.hearhere.domain.repository

import com.hearhere.domain.model.ApiResponse
import com.hearhere.domain.model.LikeMusicPost
import com.hearhere.domain.model.MusicPost
import com.hearhere.domain.model.Pin
import kotlinx.coroutines.flow.Flow

interface PostRepository {
    suspend fun getPostList(lat: Double, lng: Double): Flow<ApiResponse<List<Pin>>>
    suspend fun getPost(postId : Long,lat: Double, lng: Double) :Flow<ApiResponse<MusicPost>>

    suspend fun deletePost(postId: Long) :Flow<ApiResponse<*>>

    suspend fun likePost(postId: Long)

    suspend fun disLikePost(postId: Long)

    suspend fun getLikePostList(lat: Double,lng: Double) : Flow<ApiResponse<List<LikeMusicPost>>>
}
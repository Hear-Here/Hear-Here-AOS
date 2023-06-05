package com.hearhere.domain.usecase

import com.hearhere.domain.model.ApiResponse

interface PatchPostUseCase {
    suspend fun deletePost(postId: Long) : ApiResponse<*>
    suspend fun likePost(postId : Long)
    suspend fun disLikePost(postId: Long)
}
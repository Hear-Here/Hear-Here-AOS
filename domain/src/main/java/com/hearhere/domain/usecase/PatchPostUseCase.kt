package com.hearhere.domain.usecase

interface PatchPostUseCase {
    suspend fun likePost(postId : Long)
    suspend fun disLikePost(postId: Long)
}
package com.hearhere.domain.usecaseImpl

import com.hearhere.domain.repository.PostRepository
import com.hearhere.domain.usecase.PatchPostUseCase
import javax.inject.Inject

class PatchPostUseCaseImpl @Inject constructor(
    private val postRepository: PostRepository
) : PatchPostUseCase {
    override suspend fun likePost(postId: Long)  = postRepository.likePost(postId)

    override suspend fun disLikePost(postId: Long) = postRepository.disLikePost(postId)

}
package com.hearhere.domain.usecaseImpl

import com.hearhere.domain.model.ApiResponse
import com.hearhere.domain.model.Pin
import com.hearhere.domain.repository.PostRepository
import com.hearhere.domain.usecase.GetPostUseCase
import kotlinx.coroutines.flow.collect
import kotlinx.coroutines.flow.first
import javax.inject.Inject

class GetPostUseCaseImpl @Inject constructor(
    private val postRepository: PostRepository
) : GetPostUseCase {

    override suspend fun getPostList(lat: Double, lng: Double): ApiResponse<List<Pin>> {
        return postRepository.getPostList(lat,lng).first()
    }

}
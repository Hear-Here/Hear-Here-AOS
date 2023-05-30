package com.hearhere.domain.usecase

import com.hearhere.domain.model.ApiResponse
import com.hearhere.domain.model.Pin

interface GetPostUseCase {
    suspend fun getPostList(lat: Double,lng :Double) : ApiResponse<List<Pin>>
}
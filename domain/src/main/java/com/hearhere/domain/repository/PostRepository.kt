package com.hearhere.domain.repository

import com.hearhere.domain.model.ApiResponse
import com.hearhere.domain.model.Pin
import kotlinx.coroutines.flow.Flow

interface PostRepository {
    suspend fun getPostList(lat: Double, lng: Double): Flow<ApiResponse<List<Pin>>>
}
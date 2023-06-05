package com.hearhere.domain.repository

import com.hearhere.domain.model.ApiResponse
import com.hearhere.domain.model.AuthToken
import kotlinx.coroutines.flow.Flow

interface AuthRepository  {
    suspend fun login(id: Long, email : String) : Flow<ApiResponse<AuthToken>>
}
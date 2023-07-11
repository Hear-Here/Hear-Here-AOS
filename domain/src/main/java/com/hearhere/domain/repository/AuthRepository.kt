package com.hearhere.domain.repository

import com.hearhere.domain.model.ApiResponse
import com.hearhere.domain.model.AuthToken
import com.hearhere.domain.model.UserInfo
import kotlinx.coroutines.flow.Flow

interface AuthRepository {
    suspend fun login(id: Long, email: String): Flow<ApiResponse<UserInfo>>

    suspend fun setNickName(name: String): Flow<ApiResponse<*>>
}

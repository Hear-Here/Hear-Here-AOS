package com.hearhere.domain.usecase

import com.hearhere.domain.model.ApiResponse
import com.hearhere.domain.model.AuthToken
import com.hearhere.domain.model.UserInfo

interface LoginUseCase {
    suspend fun login(id: Long, email: String): ApiResponse<UserInfo>
    suspend fun setNickName(name: String): ApiResponse<*>
}

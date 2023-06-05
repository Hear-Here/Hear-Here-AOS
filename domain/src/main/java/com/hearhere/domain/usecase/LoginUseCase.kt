package com.hearhere.domain.usecase

import com.hearhere.domain.model.ApiResponse
import com.hearhere.domain.model.AuthToken


interface LoginUseCase {
    suspend fun login(id : Long , email : String) : ApiResponse<AuthToken>
}
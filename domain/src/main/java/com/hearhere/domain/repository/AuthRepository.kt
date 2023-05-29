package com.hearhere.domain.repository

import com.hearhere.domain.model.AuthToken

interface AuthRepository  {
    suspend fun login(token:String) : Result<AuthToken>
}
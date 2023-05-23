package com.hearhere.domain.repository

import com.hearhere.domain.model.AuthToken

interface PreferenceRepository {
    suspend fun getAuthToken():AuthToken
    suspend fun updateToken(token:String)
}
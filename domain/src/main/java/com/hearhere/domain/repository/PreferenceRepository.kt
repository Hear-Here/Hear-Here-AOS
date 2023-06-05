package com.hearhere.domain.repository

import com.hearhere.domain.model.AuthToken
import com.hearhere.domain.model.Location
import kotlinx.coroutines.flow.Flow

interface PreferenceRepository {
    suspend fun getAuthToken():AuthToken
    suspend fun updateToken(token:String)

    suspend fun updateLocation(lat : Double, lng : Double)
    suspend fun getLocation() : Flow<Location>
}
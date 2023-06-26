package com.hearhere.domain.usecase

interface PatchUserInfoUseCase {
    suspend fun updateLocation(lat: Double, lng: Double)
}

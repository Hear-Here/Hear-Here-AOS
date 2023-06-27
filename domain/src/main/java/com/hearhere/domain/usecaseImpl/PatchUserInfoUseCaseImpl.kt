package com.hearhere.domain.usecaseImpl

import com.hearhere.domain.repository.PreferenceRepository
import com.hearhere.domain.usecase.PatchUserInfoUseCase
import javax.inject.Inject

class PatchUserInfoUseCaseImpl@Inject constructor(
    private val preferenceRepository: PreferenceRepository
) : PatchUserInfoUseCase {
    override suspend fun updateLocation(lat: Double, lng: Double) {
        preferenceRepository.updateLocation(lat, lng)
    }
}

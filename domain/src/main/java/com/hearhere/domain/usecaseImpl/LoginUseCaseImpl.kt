package com.hearhere.domain.usecaseImpl

import com.hearhere.domain.model.ApiResponse
import com.hearhere.domain.model.AuthToken
import com.hearhere.domain.repository.AuthRepository
import com.hearhere.domain.repository.PreferenceRepository
import com.hearhere.domain.usecase.LoginUseCase
import kotlinx.coroutines.flow.collect
import kotlinx.coroutines.flow.first
import kotlinx.coroutines.flow.flow
import javax.inject.Inject


class LoginUseCaseImpl @Inject constructor(
    private val repository: AuthRepository,
    private val preferenceRepository: PreferenceRepository
) : LoginUseCase {

    override suspend fun login(id: Long,email: String) : ApiResponse<AuthToken> {
        return repository.login(id,email).first()
    }

    suspend fun getToken() = preferenceRepository.getAuthToken()

    suspend fun updateToken(token: String) = preferenceRepository.updateToken(token)
}
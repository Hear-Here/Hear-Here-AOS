package com.hearhere.domain.usecaseImpl


import com.hearhere.domain.usecase.TestUseCase
import com.hearhere.domain.usecase.repository.TestRepository
import javax.inject.Inject

class TestUseCaseImpl @Inject constructor(private val repository: TestRepository)  : TestUseCase {

    override suspend fun getAccessToken() = repository.getToken()
    override suspend fun updateAccessToken(token: String) = repository.updateToken(token)
}


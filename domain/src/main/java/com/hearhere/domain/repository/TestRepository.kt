package com.hearhere.domain.usecase.repository

import com.hearhere.domain.model.TestModel

interface TestRepository {
    suspend fun getToken(): TestModel
    suspend fun updateToken(token: String)
}

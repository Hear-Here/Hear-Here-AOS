package com.hearhere.domain.usecase.repository

import com.hearhere.domain.model.Music
import com.hearhere.domain.model.TestModel
import retrofit2.Response


interface TestRepository {
    suspend fun getToken() : TestModel
    suspend fun updateToken(token : String)

}
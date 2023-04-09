package com.hearhere.domain.usecase


import com.hearhere.domain.model.TestModel

interface TestUseCase{
   suspend fun getAccessToken() : TestModel
   suspend fun updateAccessToken(token : String)
}

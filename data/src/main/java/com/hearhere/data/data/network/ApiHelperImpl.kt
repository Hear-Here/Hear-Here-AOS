package com.hearhere.data.data.network

import com.hearhere.data.data.dto.request.AuthRequest
import com.hearhere.data.data.dto.response.AuthResponse
import com.hearhere.data.di.NetworkModule
import retrofit2.Response
import javax.inject.Inject

class ApiHelperImpl @Inject constructor(
    @NetworkModule.api val apiService: HearHereApiService
) : ApiHelper {
    override suspend fun login(request: AuthRequest): Response<AuthResponse> = apiService.login(request)

}
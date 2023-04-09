package com.hearhere.data.data.network

import javax.inject.Inject

class ApiHelperImpl @Inject constructor(
    private val apiService: HearHereApiService
) : ApiHelper {
    override suspend fun signup(): ApiResponse<String> = apiService.signup()
}
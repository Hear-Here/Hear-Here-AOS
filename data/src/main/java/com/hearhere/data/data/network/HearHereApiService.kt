package com.hearhere.data.data.network

import retrofit2.http.POST

interface HearHereApiService {
    //Server 통신 부분
    @POST("")
    suspend fun signup(): ApiResponse<String>
}
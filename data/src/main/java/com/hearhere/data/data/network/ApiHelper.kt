package com.hearhere.data.data.network

interface ApiHelper {
    suspend fun signup() : ApiResponse<String>
}
package com.hearhere.data.data.dto.response

data class AuthResponse(
    val id: Long,
    val name: String?,
    val nickName: String?,
    val role: String,
    val refreshToken: String,
    val authState: String,
    val accessToken: String
)

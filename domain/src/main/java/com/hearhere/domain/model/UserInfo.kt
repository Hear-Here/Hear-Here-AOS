package com.hearhere.domain.model

data class UserInfo(
    val auth: AuthToken,
    val state: String
)

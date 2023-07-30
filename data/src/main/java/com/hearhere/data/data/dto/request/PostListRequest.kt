package com.hearhere.data.data.dto.request

data class PostListRequest(
    val lat: Double,
    val lng: Double,
    val genreType: String?,
    val withType: String?,
    val weatherType: String?,
    val emotionType: String?
)

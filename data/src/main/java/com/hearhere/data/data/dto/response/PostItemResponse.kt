package com.hearhere.data.data.dto.response

data class PostItemResponse(
    val postId: Long,
    val writer: String,
    val title: String,
    val artist: String,
    val cover: String?,
    val genreType: String,
    val withType: String,
    val temp: Double,
    val weatherType: String,
    val emotionType: String,
    val content: String?,
    val longitude: Double,
    val latitude: Double,
    val distance: Double,
    val heartCount: Int,
    val isHearted: Boolean
)

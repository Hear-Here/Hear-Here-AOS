package com.hearhere.domain.model

data class Pin(
    val postId: Long,
    val imageUrl: String? = null,
    val latitude: Double,
    val longitude: Double,
    val artist: String? = "",
    val title: String? = "",
    val distance: Double? = 0.0,
    val writer: String? = "",
    val genreType: String
)

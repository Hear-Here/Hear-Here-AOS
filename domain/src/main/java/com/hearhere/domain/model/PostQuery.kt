package com.hearhere.domain.model

data class PostQuery(
    val lat: Double,
    val lng: Double,
    val genreType: String? = null,
    val withType: String? = null,
    val weatherType: String? = null,
    val emotionType: String? = null
)

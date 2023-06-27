package com.hearhere.domain.model

data class LikeMusicPost(
    val postId: Long,
    val title: String,
    val artist: String,
    val coverPath: String? = "",
    val distance: Double,
    val writer: String? = ""
)

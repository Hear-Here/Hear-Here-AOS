package com.hearhere.data.data.dto.response

data class LikePostListResponse(
    val list: List<LikePostItem>?
)

data class LikePostItem(
    val postId: Long?=-1,
    val userEmail : String?="",
    val musicTittle: String?="",
    val musicArtist: String?="",
    val musicCover: String?=null,
    val latitude: Double?=0.0,
    val longitude: Double?=0.0

)
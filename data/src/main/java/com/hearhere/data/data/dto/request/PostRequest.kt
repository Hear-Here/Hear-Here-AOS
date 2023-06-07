package com.hearhere.data.data.dto.request

data class PostRequest(
    val songId : Long,
    val title : String,
    val artist : String,
    val cover : String?,
    val genreType : String,
    val withType : String,
    val temp : Int,
    val weatherType : String,
    val emotionType : String,
    val content : String?,
    val longitude : Double,
    val latitude : Double,
)

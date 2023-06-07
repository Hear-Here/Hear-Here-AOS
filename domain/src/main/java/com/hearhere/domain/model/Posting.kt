package com.hearhere.domain.model

import java.io.Serializable

data class Posting(
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
    val longitude : Double?,
    val latitude : Double?,
):Serializable

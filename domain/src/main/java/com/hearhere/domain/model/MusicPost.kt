package com.hearhere.domain.model

data class MusicPost(
    val postId : Long,
    val writer : String,
    val title : String,
    val artist : String,
    val cover : String?,
    val genre : String,
    val whoWith : String,
    val temp : Double,
    val weather : String,
    val mood : String,
    val content : String?,
    val longitude : Double,
    val latitude : Double,
    val distance : Double,
    val likeCount : Int,
    val isLike : Boolean
)
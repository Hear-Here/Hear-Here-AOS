package com.hearhere.domain.model

data class Music (
    val songId : Long,
    val title : String,
    val artist : String,
    val cover : String?=null,
    val pubYear : String?
)
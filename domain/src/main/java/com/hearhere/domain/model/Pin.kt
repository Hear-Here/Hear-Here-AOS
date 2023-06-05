package com.hearhere.domain.model

import android.graphics.Bitmap
import android.graphics.BitmapFactory
import java.io.IOException
import java.net.HttpURLConnection
import java.net.MalformedURLException
import java.net.URL

data class Pin (
    val postId : Long,
    val imageUrl : String? = null,
    val latitude : Double,
    val longitude : Double,
    val artist: String?="",
    val title : String?="",
    val distance: Double?=0.0,
    val writer : String?="",
)


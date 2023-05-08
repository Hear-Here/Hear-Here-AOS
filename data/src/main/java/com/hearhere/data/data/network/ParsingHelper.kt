package com.hearhere.data.data.network

import com.hearhere.data.data.dto.response.MusicResponse
import retrofit2.Response

interface ParsingHelper {
    suspend fun getMusicInfo(
        keyword: String,
        option: String?,
        display: Int?
    ): Response<MusicResponse>
}
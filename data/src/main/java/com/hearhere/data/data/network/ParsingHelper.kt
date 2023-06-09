package com.hearhere.data.data.network

import com.hearhere.data.data.dto.response.SearchBySongResponse
import okhttp3.ResponseBody
import retrofit2.Response

interface ParsingHelper {
    suspend fun searchMusicBySong(
        keyword: String,
        display: Int?
    ): Response<SearchBySongResponse>

    suspend fun searchMusicByArtist(
        keyword: String,
        display: Int?
    ): Response<ResponseBody>
}

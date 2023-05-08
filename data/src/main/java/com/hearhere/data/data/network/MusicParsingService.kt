package com.hearhere.data.data.network

import com.hearhere.data.data.dto.response.MusicResponse
import retrofit2.Response
import retrofit2.http.GET
import retrofit2.http.Path
import retrofit2.http.Query

//i/search/빅뱅/?sr=album&display=10&key=example&v=0.5
interface MusicParsingService {

    @GET("api/search/{keyword}/")
    suspend fun getMusicInfo(
        @Path(value = "keyword") keyword: String,
        @Query(value = "sr") option: String? = "song",
        @Query(value = "display") display: Int? = 10,
        @Query(value = "key") key: String = "example",
        @Query(value = "v") v: String = "0.5"
    ): Response<MusicResponse>
}
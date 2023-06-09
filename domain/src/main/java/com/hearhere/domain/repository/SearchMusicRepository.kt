package com.hearhere.domain.repository

import com.hearhere.domain.model.SearchedMusic

interface SearchMusicRepository {
    suspend fun searchMusicBySong(keyword: String, display: Int?): Result<List<SearchedMusic>>

    suspend fun searchMusicByArtist(keyword: String, display: Int?): Result<List<SearchedMusic>>
}

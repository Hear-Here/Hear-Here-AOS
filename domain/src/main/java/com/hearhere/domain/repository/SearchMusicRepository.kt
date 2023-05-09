package com.hearhere.domain.repository

import com.hearhere.domain.model.Music

interface SearchMusicRepository {
    suspend fun searchMusicBySong(keyword: String, display: Int?): Result<List<Music>>

    suspend fun searchMusicByArtist(keyword: String, display: Int?): Result<List<Music>>
}
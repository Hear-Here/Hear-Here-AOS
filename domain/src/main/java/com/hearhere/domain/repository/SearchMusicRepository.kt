package com.hearhere.domain.repository

import com.hearhere.domain.model.Music

interface SearchMusicRepository {
    suspend fun getMusicInfo(keyword: String, option: String?, display: Int?): Result<List<Music>>
}
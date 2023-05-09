package com.hearhere.domain.usecase

import com.hearhere.domain.model.Music

interface SearchMusicUseCase {
     suspend fun searchMusicBySong(keyword: String, option: String?, display : Int?) : Result<List<Music>>
     suspend fun searchMusicByArtist(keyword: String, display : Int?) : Result<List<Music>>
}
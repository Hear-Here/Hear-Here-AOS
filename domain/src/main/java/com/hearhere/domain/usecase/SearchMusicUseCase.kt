package com.hearhere.domain.usecase

import com.hearhere.domain.model.SearchedMusic

interface SearchMusicUseCase {
     suspend fun searchMusicBySong(keyword: String, display : Int?) : Result<List<SearchedMusic>>
     suspend fun searchMusicByArtist(keyword: String, display : Int?) : Result<List<SearchedMusic>>
}
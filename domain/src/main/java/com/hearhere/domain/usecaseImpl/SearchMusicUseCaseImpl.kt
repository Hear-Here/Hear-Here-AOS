package com.hearhere.domain.usecaseImpl

import com.hearhere.domain.model.Music
import com.hearhere.domain.repository.SearchMusicRepository
import com.hearhere.domain.usecase.SearchMusicUseCase
import javax.inject.Inject

class SearchMusicUseCaseImpl @Inject constructor(
    private val repository: SearchMusicRepository
) : SearchMusicUseCase {
    override suspend fun searchMusicBySong(keyword: String, display: Int?) =
        repository.searchMusicBySong(keyword, display?: 10)

    override suspend fun searchMusicByArtist(keyword: String, display: Int?): Result<List<Music>> =
        repository.searchMusicByArtist(keyword, display)
}
package com.hearhere.domain.usecaseImpl

import com.hearhere.domain.repository.SearchMusicRepository
import com.hearhere.domain.usecase.SearchMusicUseCase
import javax.inject.Inject

class SearchMusicUseCaseImpl @Inject constructor(
    private val repository: SearchMusicRepository
) : SearchMusicUseCase {
    override suspend fun getMusicInfo(keyword: String, option: String?, display: Int?) =
        repository.getMusicInfo(keyword, option?:"song", display?:10)
}
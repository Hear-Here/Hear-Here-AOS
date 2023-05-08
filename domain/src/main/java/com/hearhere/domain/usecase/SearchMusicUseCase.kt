package com.hearhere.domain.usecase

import com.hearhere.domain.model.Music

interface SearchMusicUseCase {
     suspend fun getMusicInfo(keyword: String, option: String?, display : Int?) : Result<List<Music>>
}
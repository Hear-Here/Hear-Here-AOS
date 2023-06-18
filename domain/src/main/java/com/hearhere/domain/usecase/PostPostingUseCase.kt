package com.hearhere.domain.usecase

import com.hearhere.domain.model.ApiResponse
import com.hearhere.domain.model.Posting

interface PostPostingUseCase {
    suspend fun postMusicPosting(posting: Posting) : ApiResponse<*>
}
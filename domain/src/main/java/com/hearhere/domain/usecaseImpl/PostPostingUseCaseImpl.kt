package com.hearhere.domain.usecaseImpl

import com.hearhere.domain.model.ApiResponse
import com.hearhere.domain.model.Location
import com.hearhere.domain.model.Posting
import com.hearhere.domain.repository.PostRepository
import com.hearhere.domain.repository.PreferenceRepository
import com.hearhere.domain.usecase.PostPostingUseCase
import kotlinx.coroutines.flow.first
import javax.inject.Inject

class PostPostingUseCaseImpl @Inject constructor(
    val repository: PostRepository,
    val preferenceRepository: PreferenceRepository
) : PostPostingUseCase {
    private val DEFAULT_LOCATION = Location(37.566667, 126.978427)

    override suspend fun postMusicPosting(posting: Posting): ApiResponse<*> {
        val location = preferenceRepository.getLocation().first()
        return repository.postMusicPosting(posting.copy(latitude = location.lat, longitude = location.lng)).first()
    }
}

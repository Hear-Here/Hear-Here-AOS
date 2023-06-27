package com.hearhere.domain.usecaseImpl

import com.hearhere.domain.model.ApiResponse
import com.hearhere.domain.model.LikeMusicPost
import com.hearhere.domain.model.Location
import com.hearhere.domain.model.MusicPost
import com.hearhere.domain.model.MyMusicPost
import com.hearhere.domain.model.Pin
import com.hearhere.domain.repository.PostRepository
import com.hearhere.domain.repository.PreferenceRepository
import com.hearhere.domain.usecase.GetPostUseCase
import kotlinx.coroutines.CoroutineScope
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.async
import kotlinx.coroutines.flow.first
import kotlinx.coroutines.launch
import javax.inject.Inject

class GetPostUseCaseImpl @Inject constructor(
    private val postRepository: PostRepository,
    private val preferenceRepository: PreferenceRepository
) : GetPostUseCase {

    var myLocation: Location? = null

    init {
        CoroutineScope(Dispatchers.IO).launch {
            getLocation()
        }
    }
    suspend fun getLocation(): Location? {
        if (myLocation == null) {
            CoroutineScope(Dispatchers.IO).async {
                myLocation = preferenceRepository.getLocation().first()
            }.await()
            return myLocation
        } else return myLocation!!
    }

    override suspend fun getPostList(lat: Double, lng: Double): ApiResponse<List<Pin>> {
        return postRepository.getPostList(lat, lng).first()
    }

    override suspend fun getPost(postId: Long): ApiResponse<MusicPost> {
        val location = myLocation ?: getLocation() ?: return ApiResponse.Error("can not find location")
        return postRepository.getPost(postId, location.lat, location.lng).first()
    }

    override suspend fun getLikePostList(): ApiResponse<List<LikeMusicPost>> {
        val location = myLocation ?: getLocation() ?: return ApiResponse.Error("can not find location")
        return postRepository.getLikePostList(location.lat, location.lng).first()
    }

    override suspend fun getMyPostList(): ApiResponse<List<MyMusicPost>> {
        val location = myLocation ?: getLocation() ?: return ApiResponse.Error("can not find location")
        return postRepository.getMyPostList(location.lat, location.lng).first()
    }
}

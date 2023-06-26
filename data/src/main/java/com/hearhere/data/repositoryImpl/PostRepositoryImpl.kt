package com.hearhere.data.repositoryImpl

import android.util.Log
import com.hearhere.data.data.dto.request.PostRequest
import com.hearhere.data.data.dto.response.LikePostItem
import com.hearhere.data.data.dto.response.MyPostListResponse
import com.hearhere.data.data.dto.response.PostItemResponse
import com.hearhere.data.data.network.ApiHelperImpl
import com.hearhere.domain.model.ApiResponse
import com.hearhere.domain.model.LikeMusicPost
import com.hearhere.domain.model.MusicPost
import com.hearhere.domain.model.MyMusicPost
import com.hearhere.domain.model.Pin
import com.hearhere.domain.model.Posting
import com.hearhere.domain.repository.PostRepository
import kotlinx.coroutines.flow.Flow
import kotlinx.coroutines.flow.flow
import javax.inject.Inject

class PostRepositoryImpl @Inject constructor(
    private val apiHelper: ApiHelperImpl
) : BaseRepository(), PostRepository {

    override suspend fun getPostList(lat: Double, lng: Double) = flow<ApiResponse<List<Pin>>> {
        safeApiCall { apiHelper.getPostList(lat, lng) }
            .collect {
                when (it) {
                    is ApiResponse.Success -> {
                        emit(it.data!!.mapToDomain())
                    }

                    else -> {
                        emit(ApiResponse.Error(it.message.toString(), it.throwable))
                    }
                }
            }
    }

    override suspend fun getPost(postId: Long, lat: Double, lng: Double) = flow<ApiResponse<MusicPost>> {
        safeApiCall { apiHelper.getPost(postId, lat, lng) }
            .collect {
                when (it) {
                    is ApiResponse.Success -> {
                        emit(it.data!!.maptoDomain())
                    }
                    is ApiResponse.Error -> {
                        emit(ApiResponse.Error(it.message.toString(), it.throwable))
                    }
                }
            }
    }

    override suspend fun deletePost(postId: Long): Flow<ApiResponse<*>> {
        return safeApiCall { apiHelper.deletePost(postId) }
    }

    override suspend fun likePost(postId: Long) {
        safeApiCall { apiHelper.likePost(postId) }.collect {
            when (it) {
                is ApiResponse.Success -> {
                    Log.d("api", "like Post $postId success")
                }
                else -> {
                    Log.d("api", "like Post $postId false")
                }
            }
        }
    }

    override suspend fun disLikePost(postId: Long) {
        safeApiCall { apiHelper.disLikePost(postId) }.collect {
            when (it) {
                is ApiResponse.Success -> {
                    Log.d("api", "like Post $postId success")
                }

                else -> {
                    Log.d("api", "like Post $postId false")
                }
            }
        }
    }

    override suspend fun getLikePostList(lat: Double, lng: Double) = flow<ApiResponse<List<LikeMusicPost>>> {
        safeApiCall { apiHelper.getLikePostList(lat, lng) }
            .collect {
                when (it) {
                    is ApiResponse.Success -> {
                        emit(it.data!!.mapToDomain())
                    }
                    is ApiResponse.Error -> {
                        emit(ApiResponse.Error(it.message.toString(), it.throwable))
                    }
                }
            }
    }

    override suspend fun getMyPostList(
        lat: Double,
        lng: Double
    ): Flow<ApiResponse<List<MyMusicPost>>> = flow {
        safeApiCall { apiHelper.getMyPostList(lat, lng) }
            .collect {
                when (it) {
                    is ApiResponse.Success -> {
                        emit(it.data!!.mapToDomain())
                    }
                    is ApiResponse.Error -> {
                        emit(ApiResponse.Error(it.message.toString(), it.throwable))
                    }
                }
            }
    }

    override suspend fun postMusicPosting(posting: Posting): Flow<ApiResponse<*>> = flow {
        safeApiCall {
            apiHelper.postMusicPosting(
                PostRequest(
                    songId = posting.songId,
                    title = posting.title,
                    artist = posting.artist,
                    cover = posting.cover,
                    genreType = posting.genreType,
                    withType = posting.withType,
                    temp = posting.temp,
                    weatherType = posting.weatherType,
                    emotionType = posting.emotionType,
                    content = posting.content ?: "",
                    longitude = posting.longitude!!,
                    latitude = posting.latitude!!
                )
            )
        }.collect {
            when (it) {
                is ApiResponse.Success -> {
                    emit(it)
                }

                else -> {
                    emit(it)
                }
            }
        }
    }

    @JvmName("loadLikePostList")
    fun List<PostItemResponse>.mapToDomain(): ApiResponse<List<Pin>> {
        val temp = ArrayList<Pin>()
        this.forEach {
            val pin = Pin(
                postId = it.postId,
                imageUrl = it.cover,
                latitude = it.latitude,
                longitude = it.longitude,
                distance = it.distance,
                writer = it.writer,
                title = it.title,
                artist = it.artist,
                genreType = it.genreType
            )
            temp.add(pin)
        }
        return ApiResponse.Success(temp)
    }
    @JvmName("loadMyPostList")
    fun List<MyPostListResponse>.mapToDomain(): ApiResponse<List<MyMusicPost>> {
        val temp = ArrayList<MyMusicPost>()
        this?.forEach {
            val post = MyMusicPost(
                postId = it.postId ?: -1,
                coverPath = it.cover,
                artist = it.artist,
                title = it.title,
                distance = it.distance,
                writer = it.writer,
            )
            temp.add(post)
        }
        return ApiResponse.Success(temp)
    }
    fun List<LikePostItem>.mapToDomain(): ApiResponse<List<LikeMusicPost>> {
        val temp = ArrayList<LikeMusicPost>()
        this?.forEach {
            val post = LikeMusicPost(
                postId = it.postId ?: -1,
                coverPath = it.cover,
                artist = it.artist,
                title = it.title,
                distance = it.distance,
                writer = it.writer
            )
            temp.add(post)
        }
        return ApiResponse.Success(temp)
    }

    fun PostItemResponse.maptoDomain(): ApiResponse<MusicPost> {
        val post = MusicPost(
            postId = postId,
            writer = writer,
            title = title,
            artist = artist,
            cover = cover,
            genre = genreType,
            whoWith = withType,
            temp = temp,
            weather = weatherType,
            mood = emotionType,
            content = content,
            longitude = longitude,
            latitude = latitude,
            distance = distance,
            likeCount = likeCount,
            isLike = isLiked
        )
        return ApiResponse.Success(post)
    }
}

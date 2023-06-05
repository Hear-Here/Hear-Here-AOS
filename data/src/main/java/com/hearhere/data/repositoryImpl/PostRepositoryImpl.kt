package com.hearhere.data.repositoryImpl

import android.util.Log
import com.hearhere.data.data.dto.response.LikePostItem
import com.hearhere.data.data.dto.response.LikePostListResponse
import com.hearhere.data.data.dto.response.PostItem
import com.hearhere.data.data.dto.response.PostItemResponse
import com.hearhere.data.data.dto.response.PostListResponse
import com.hearhere.data.data.network.ApiHelperImpl
import com.hearhere.domain.model.ApiResponse
import com.hearhere.domain.model.LikeMusicPost
import com.hearhere.domain.model.MusicPost
import com.hearhere.domain.model.Pin
import com.hearhere.domain.repository.PostRepository
import kotlinx.coroutines.flow.collect
import kotlinx.coroutines.flow.flow
import java.util.concurrent.Flow
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

    override suspend fun getPost(postId: Long)=  flow<ApiResponse<MusicPost>> {
        safeApiCall { apiHelper.getPost(postId) }
            .collect{
                when(it){
                    is ApiResponse.Success ->{
                        emit(it.data!!.maptoDomain())
                    }
                    is ApiResponse.Error ->{
                        emit(ApiResponse.Error(it.message.toString(), it.throwable))
                    }
                }
            }
    }

    override suspend fun likePost(postId: Long){
        safeApiCall { apiHelper.likePost(postId) }.collect{
            when(it){
                is ApiResponse.Success ->{
                    Log.d( "api", "like Post ${postId} success")
                }
                else-> {
                    Log.d( "api", "like Post ${postId} false")
                }
            }
        }
    }

    override suspend fun disLikePost(postId: Long){
        safeApiCall { apiHelper.disLikePost(postId) }.collect {
            when (it) {
                is ApiResponse.Success -> {
                    Log.d("api", "like Post ${postId} success")
                }

                else -> {
                    Log.d("api", "like Post ${postId} false")
                }
            }
        }
    }

    override suspend fun getLikePostList()= flow<ApiResponse<List<LikeMusicPost>>> {
        safeApiCall { apiHelper.getLikePostList() }
            .collect{
                when(it){
                    is ApiResponse.Success ->{
                        emit(it.data!!.mapToDomain())
                    }
                    is ApiResponse.Error ->{
                        emit(ApiResponse.Error(it.message.toString(), it.throwable))
                    }
                }
            }
    }


    fun PostListResponse.mapToDomain(): ApiResponse<List<Pin>> {
        val temp = ArrayList<Pin>()
        this.list.forEach {
            val pin = Pin(
                postId = it.postId,
                imageUrl = it.cover,
                latitude = it.latitude,
                longitude = it.longitude
            )
            temp.add(pin)
        }
        return ApiResponse.Success(temp)
    }

    fun List<LikePostItem>.mapToDomain() : ApiResponse<List<LikeMusicPost>>{
        val temp = ArrayList<LikeMusicPost>()
        this?.forEach {
            val post = LikeMusicPost(
                postId = it.postId?:-1,
                coverPath = it.musicCover?:null,
                artist = it.musicArtist?:"",
                title = it.musicTittle?:"",
                distance = 0.0 //TODO Fix
            )
            temp.add(post)
        }
        return ApiResponse.Success(temp)
    }

    fun PostItemResponse.maptoDomain(): ApiResponse<MusicPost> {
        val post = MusicPost(
            postId,
            writer,
            title,
            artist,
            cover,
            genre,
            whoWith,
            temp,
            weather,
            mood,
            content,
            longitude,
            latitude,
            distance,
            likeCount,
            isLike
        )
        return ApiResponse.Success(post)
    }
}
package com.hearhere.data.repositoryImpl

import com.hearhere.data.data.dto.response.PostItem
import com.hearhere.data.data.dto.response.PostListResponse
import com.hearhere.data.data.network.ApiHelperImpl
import com.hearhere.domain.model.ApiResponse
import com.hearhere.domain.model.Pin
import com.hearhere.domain.repository.PostRepository
import kotlinx.coroutines.flow.flow
import java.util.concurrent.Flow
import javax.inject.Inject

class PostRepositoryImpl @Inject constructor(
    private val apiHelper: ApiHelperImpl
) : BaseRepository(), PostRepository {

    override suspend fun getPostList(lat: Double, lng: Double)= flow<ApiResponse<List<Pin>>>{
        safeApiCall { apiHelper.getPostList(lat, lng) }
            .collect {
                when(it){
                    is ApiResponse.Success->{ emit( it.data!!.mapToDomain() )}
                    else ->{ emit(ApiResponse.Error(it.message.toString(),it.throwable))  }
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
}
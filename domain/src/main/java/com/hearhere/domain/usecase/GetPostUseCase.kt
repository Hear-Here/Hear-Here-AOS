package com.hearhere.domain.usecase

import com.hearhere.domain.model.ApiResponse
import com.hearhere.domain.model.LikeMusicPost
import com.hearhere.domain.model.MusicPost
import com.hearhere.domain.model.MyMusicPost
import com.hearhere.domain.model.Pin

interface GetPostUseCase {
    suspend fun getPostList(lat: Double,lng :Double) : ApiResponse<List<Pin>>

    suspend fun getPost(postId : Long) : ApiResponse<MusicPost>
    suspend fun getLikePostList() : ApiResponse<List<LikeMusicPost>>

    suspend fun getMyPostList() : ApiResponse<List<MyMusicPost>>
}
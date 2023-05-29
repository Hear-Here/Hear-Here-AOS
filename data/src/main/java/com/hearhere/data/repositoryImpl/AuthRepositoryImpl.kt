package com.hearhere.data.repositoryImpl

import android.content.Context
import android.util.Log
import com.hearhere.data.data.dto.request.AuthRequest
import com.hearhere.data.data.dto.response.AuthResponse
import com.hearhere.data.data.network.ApiHelper
import com.hearhere.data.data.network.ApiHelperImpl
import com.hearhere.domain.model.ApiResponse
import com.hearhere.domain.model.AuthToken
import com.hearhere.domain.repository.AuthRepository
import dagger.hilt.android.qualifiers.ApplicationContext
import kotlinx.coroutines.flow.Flow
import kotlinx.coroutines.flow.flow
import javax.inject.Inject

class AuthRepositoryImpl @Inject constructor(
    @ApplicationContext private val context: Context,
    private val apiHelper: ApiHelperImpl
) : BaseRepository(), AuthRepository {


    override suspend fun login(id: Long, email: String): Flow<ApiResponse<AuthToken>> = flow{
        safeApiCall {
            apiHelper.login(AuthRequest(id, email))
        }.collect {
            Log.d("catch kakao",it.toString())
            when (it) {
                is ApiResponse.Success -> {
                    emit(ApiResponse.Success(it.data!!.toDomain()))
                }
                else -> {
                    emit(ApiResponse.Error(it.message.toString(),it.throwable))
                }
            }
        }
    }

    fun AuthResponse.toDomain() = AuthToken(this.accessToken)

}
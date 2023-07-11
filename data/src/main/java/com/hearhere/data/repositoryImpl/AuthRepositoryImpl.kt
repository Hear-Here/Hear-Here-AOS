package com.hearhere.data.repositoryImpl

import android.content.Context
import com.hearhere.data.data.dto.request.AuthRequest
import com.hearhere.data.data.dto.response.AuthResponse
import com.hearhere.data.data.network.ApiHelperImpl
import com.hearhere.domain.model.ApiResponse
import com.hearhere.domain.model.AuthToken
import com.hearhere.domain.model.UserInfo
import com.hearhere.domain.repository.AuthRepository
import dagger.hilt.android.qualifiers.ApplicationContext
import kotlinx.coroutines.flow.Flow
import kotlinx.coroutines.flow.flow
import javax.inject.Inject

class AuthRepositoryImpl @Inject constructor(
    @ApplicationContext private val context: Context,
    private val apiHelper: ApiHelperImpl
) : BaseRepository(), AuthRepository {

    override suspend fun login(id: Long, email: String): Flow<ApiResponse<UserInfo>> = flow {
        safeApiCall {
            apiHelper.login(AuthRequest(id, email))
        }.collect {
            when (it) {
                is ApiResponse.Success -> {
                    emit(ApiResponse.Success(it.data!!.toDomain()))
                }
                else -> {
                    emit(ApiResponse.Error(it.message.toString(), it.throwable))
                }
            }
        }
    }

    override suspend fun setNickName(name: String): Flow<ApiResponse<*>> = flow {
        safeApiCall { apiHelper.setNickName(name) }.collect {
            emit(it)
        }
    }

    fun AuthResponse.toDomain() = UserInfo(state = this.authState, auth = AuthToken(this.accessToken))
}

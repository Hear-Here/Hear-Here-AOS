package com.hearhere.data.repositoryImpl

import android.util.Log
import com.hearhere.domain.model.ApiResponse
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.flow.flow
import kotlinx.coroutines.flow.flowOn
import retrofit2.HttpException
import retrofit2.Response
import java.io.IOException

abstract class BaseRepository() {
    suspend fun <T> safeApiCall(apiToBeCalled: suspend () -> Response<T>)= flow<ApiResponse<T>>{
        try {
            val response: Response<T> = apiToBeCalled()
            if (response.isSuccessful) {
                emit(ApiResponse.Success(data = response.body()!!))
            } else {
                emit(ApiResponse.Error(
                    errorMessage = response.message() ?: "network get response but fail"
                ))
            }

        } catch (e: HttpException) {
            emit(ApiResponse.Error(errorMessage = e.message ?: "Something went wrong", throwable = e))
        } catch (e: IOException) {
            emit(ApiResponse.Error("Please check your network connection",throwable = e))
        } catch (e: Exception) {
            Log.d("safeApi-Error",e.message.toString())
            emit(ApiResponse.Error(errorMessage = "Something went wrong",throwable = e))
        }
    }.flowOn(Dispatchers.IO)
}

package com.hearhere.data.repositoryImpl

import com.hearhere.data.data.dto.response.ApiResponse
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.withContext
import okhttp3.ResponseBody
import retrofit2.HttpException
import retrofit2.Response
import java.io.IOException

abstract class BaseRepository() {
    suspend fun <T> safeApiCall(apiToBeCalled: suspend () -> Response<T>): ApiResponse<T> {
        return withContext(Dispatchers.IO) {
            try {
                val response: Response<T> = apiToBeCalled()
                if (response.isSuccessful) {
                    ApiResponse.Success(data = response.body()!!)
                } else {
                    ApiResponse.Error(
                        errorMessage = response.message() ?: "network get response but fail"
                    )
                }

            } catch (e: HttpException) {
                ApiResponse.Error(errorMessage = e.message ?: "Something went wrong", throwable = e)
            } catch (e: IOException) {
                ApiResponse.Error("Please check your network connection",throwable = e)
            } catch (e: Exception) {
                ApiResponse.Error(errorMessage = "Something went wrong",throwable = e)
            }
        }
    }
}

package com.hearhere.domain.model

sealed class ApiResponse<T>(
    val data: T? = null,
    val message: String? = null,
    val throwable: Throwable? = null
) {
    class Success<T>(data: T) : ApiResponse<T>(data = data)
    class Error<T>(errorMessage: String, throwable: Throwable? = null) :
        ApiResponse<T>(message = errorMessage)
}

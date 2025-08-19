package com.example.movies.domain.model

sealed class ApiResult<out T> {
    data class Success<T>(val data: T) : ApiResult<T>()
    sealed class Error : ApiResult<Nothing>() {
        data object Unauthorized : Error()
        data object Forbidden : Error()
        data object NotFound : Error()
        data class Unknown(
            val code: Int,
            val message: String,
            val error: String
        ) : Error()
    }
}
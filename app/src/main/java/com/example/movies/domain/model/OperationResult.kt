package com.example.movies.domain.model

sealed class OperationResult<out T> {
    data object Loading : OperationResult<Nothing>()

    data class Success<out T>(val data: T? = null) : OperationResult<T>()

    sealed class Error : OperationResult<Nothing>() {
        data object Unauthorized : Error()
        data object Forbidden : Error()
        data object NotFound : Error()
        data class Unknown(
            val code: Int?,
            val message: String?,
            val error: String?,
            val exception: Throwable?
        ) : Error()
    }
}
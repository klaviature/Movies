package com.example.movies.domain.model

sealed class MovieResult {
    data class Success(val movie: MovieDeprecated) : MovieResult()
    sealed class Error : MovieResult() {
        data object Unauthorized : Error()
        data object Forbidden : Error()
        data object NotFound : Error()
        data class Unknown(
            val code: Int,
            val message: String,
            val error: String
        )
    }
}
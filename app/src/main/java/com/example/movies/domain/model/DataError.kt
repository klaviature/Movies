package com.example.movies.domain.model

interface DataError : Error {
    sealed class Network : DataError {
        data class Unauthorized(val message: String?) : Network()
        data class Forbidden(val message: String?) : Network()
        data class NotFound(val message: String?) : Network()
        data class Unknown(val message: String?) : Network()
//        Unauthorized,
//        Forbidden,
//        NotFound,
//        Unknown
    }

    enum class Local : DataError {
        NotFound,
        Critical
    }
}
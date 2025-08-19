package com.example.movies.domain.model

data class LinkedMovie(
    val id: Int,
    val name: String,
    val poster: Image?,
    val rating: MovieRating?,
    val year: Int
)

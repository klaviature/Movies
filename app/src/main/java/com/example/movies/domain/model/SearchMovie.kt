package com.example.movies.domain.model

data class SearchMovie(
    val id: Int,
    val name: String,
    val alternativeName: String,
    val enName: String,
    val logo: Image?,
    val poster: Image?,
    val backdrop: Image?,
    val rating: MovieRating?,
    val year: Int,
    val ageRating: Int,
    val top10: Int? = null,
    val top250: Int? = null
)

data class Logo(
    val url: String?,
    val previewUrl: String?
)

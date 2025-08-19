package com.example.movies.domain.model

import com.example.movies.data.model.MovieType

data class Movie(
    val id: Int,
    val name: String,
    val type: MovieType,
    val year: Int,
    val description: String,
    val status: String?,
    val rating: MovieRating,
    val length: Int?,
    val ageRating: Int,
    val logo: Image,
    val poster: Image,
    val backdrop: Image,
    val videos: Videos?,
    val genres: List<String>,
    val countries: List<String>,
    val reviewInfo: ReviewInfo,
    val budget: CurrencyValue<Int>,
    val fees: MovieFees,
    val similarMovies: List<LinkedMovie>?,
    val sequelsAndPrequels: List<LinkedMovie>?,
    val top10: Int?,
    val top250: Int?,
    val isTicketsOnSale: Boolean,
    val totalSeriesLength: Int?,
    val averageSeriesLength: Int?,
    val isSeries: Boolean
)

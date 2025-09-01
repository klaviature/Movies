package com.example.movies.data.api.model

import kotlinx.serialization.SerialName

data class SearchMovieDto(
    @SerialName("id") val id: Int? = null,
    @SerialName("name") val name: String? = null,
    @SerialName("alternativeName") val alternativeName: String? = null,
    @SerialName("enName") val enName: String? = null,
    @SerialName("type") val type: MovieType? = null,
    @SerialName("year") val year: Int? = null,
    @SerialName("description") val description: String? = null,
    @SerialName("shortDescription") val shortDescription: String? = null,
    @SerialName("movieLength") val movieLength: Int? = null,
    @SerialName("logo") val logo: ShortImageDto? = null,
    @SerialName("poster") val poster: ShortImageDto? = null,
    @SerialName("backdrop") val backdrop: ShortImageDto? = null,
    @SerialName("rating") val rating: RatingDto? = null,
    @SerialName("genres") val genres: List<ItemNameDto>? = null,
    @SerialName("countries") val countries: List<ItemNameDto>? = null,
    @SerialName("releaseYears") val releaseYears: YearRangeDto? = null,
    @SerialName("isSeries") val isSeries: Boolean? = null,
    @SerialName("ticketsOnSale") val ticketsOnSale: Boolean? = null,
    @SerialName("totalSeriesLength") val totalSeriesLength: Int? = null,
    @SerialName("seriesLength") val seriesLength: Int? = null,
    @SerialName("ageRating") val ageRating: Int? = null,
    @SerialName("top10") val top10: Int? = null,
    @SerialName("top250") val top250: Int? = null,
    @SerialName("status") val status: String? = null
)

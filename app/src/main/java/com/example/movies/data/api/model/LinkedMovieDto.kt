package com.example.movies.data.api.model

import kotlinx.serialization.SerialName
import kotlinx.serialization.Serializable
import kotlinx.serialization.json.JsonIgnoreUnknownKeys

@Serializable
@JsonIgnoreUnknownKeys
data class LinkedMovieDto(
    @SerialName("id") val id: Int? = null,
    @SerialName("name") val name: String? = null,
    @SerialName("enName") val enName: String? = null,
    @SerialName("alternativeName") val alternativeName: String? = null,
    @SerialName("type") val type: MovieType? = null,
    @SerialName("poster") val poster: ShortImageDto? = null,
    @SerialName("rating") val rating: RatingDto? = null,
    @SerialName("year") val year: Int? = null
)

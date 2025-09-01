package com.example.movies.data.api.model

import kotlinx.serialization.ExperimentalSerializationApi
import kotlinx.serialization.SerialName
import kotlinx.serialization.Serializable
import kotlinx.serialization.json.JsonIgnoreUnknownKeys

@JsonIgnoreUnknownKeys
@Serializable
data class MovieDto(
    @SerialName("id") val id: Int? = null,
    @SerialName("name") val name: String? = null,
    @SerialName("alternativeName") val alternativeName: String? = null,
    @SerialName("enName") val enName: String? = null,
    @SerialName("type") val type: MovieType? = null,
    @SerialName("year") val year: Int? = null,
    @SerialName("description") val description: String? = null,
    @SerialName("shortDescription") val shortDescription: String? = null,
    @SerialName("slogan") val slogan: String? = null,
    @SerialName("status") val status: String? = null,
    @SerialName("rating") val rating: RatingDto? = null,
    @SerialName("movieLength") val movieLength: Int? = null,
    @SerialName("ageRating") val ageRating: Int? = null,
    @SerialName("logo") val logo: ShortImageDto? = null,
    @SerialName("poster") val poster: ShortImageDto? = null,
    @SerialName("backdrop") val backdrop: ShortImageDto? = null,
    @SerialName("videos") val videos: VideosDto? = null,
    @SerialName("genres") val genres: List<ItemNameDto>? = null,
    @SerialName("countries") val countries: List<ItemNameDto>? = null,
    @SerialName("persons") val persons: List<PersonInMovieDto>? = null,
    @SerialName("reviewInfo") val reviewInfo: ReviewInfoDto? = null,
    @SerialName("budget") val budget: CurrencyValueDto? = null,
    @SerialName("fees") val fees: FeesDto? = null,
    @SerialName("premiere") val premiere: PremiereDto? = null,
    @SerialName("similarMovies") val similarMovies: List<LinkedMovieDto>? = null,
    @SerialName("sequelsAndPrequels") val sequelsAndPrequels: List<LinkedMovieDto>? = null,
    @SerialName("releaseYears") val releaseYears: List<YearRangeDto>? = null,
    @SerialName("top10") val top10: Int? = null,
    @SerialName("top250") val top250: Int? = null,
    @SerialName("ticketsOnSale") val ticketsOnSale: Boolean? = null,
    @SerialName("totalSeriesLength") val totalSeriesLength: Int? = null,
    @SerialName("seriesLength") val seriesLength: Int? = null,
    @SerialName("isSeries") val isSeries: Boolean? = null,
    @SerialName("lists") val lists: List<String>? = null,
    @SerialName("updatedAt") val updatedAt: String? = null,
    @SerialName("createdAt") val createdAt: String? = null
)

@Serializable
data class ItemNameDto(
    @SerialName("name") val name: String? = null
)

@Serializable
data class CountryDto(
    @SerialName("name") val name: String? = null
)

@Serializable
data class GenreDto(
    @SerialName("name") val name: String? = null
)

@Serializable
data class ShortImageDto(
    @SerialName("url") val url: String? = null,
    @SerialName("previewUrl") val previewUrl: String? = null
)

@Serializable
data class PosterDto(
    @SerialName("url") val url: String? = null,
    @SerialName("previewUrl") val previewUrl: String? = null
)

@Serializable
data class LogoDto(
    @SerialName("url") val url: String? = null
)

@Serializable
data class RatingDto(
    @SerialName("kp") val kp: Double? = null,
    @SerialName("imdb") val imdb: Double? = null,
    @SerialName("tmdb") val tmdb: Double? = null,
    @SerialName("filmCritics") val filmCritics: Double? = null,
    @SerialName("russianFilmCritics") val russianFilmCritics: Double? = null,
    @SerialName("await") val await: Double? = null
)

@Serializable
data class FeesDto(
    @SerialName("world") val world: CurrencyValueDto? = null,
    @SerialName("usa") val usa: CurrencyValueDto? = null,
    @SerialName("russia") val russia: CurrencyValueDto? = null
)

@Serializable
data class CurrencyValueDto(
    @SerialName("currency") val currency: String? = null,
    @SerialName("value") val value: Int? = null
)

@Serializable
data class YearRangeDto(
    @SerialName("start") val start: Int? = null,
    @SerialName("end") val end: Int? = null
)

@Serializable
data class PremiereDto(
    @SerialName("country") val country: String? = null,
    @SerialName("world") val world: String? = null,
    @SerialName("russia") val russia: String? = null,
    @SerialName("digital") val digital: String? = null,
    @SerialName("cinema") val cinema: String? = null,
    @SerialName("bluray") val bluray: String? = null,
    @SerialName("dvd") val dvd: String? = null
)

@Serializable
data class ReviewInfoDto(
    @SerialName("count") val count: Int? = null,
    @SerialName("positiveCount") val positiveCount: Int? = null,
    @SerialName("percentage") val percentage: String? = null
)

@Serializable
data class PersonInMovieDto(
    @SerialName("id") val id: Int? = null,
    @SerialName("photo") val photo: String? = null,
    @SerialName("name") val name: String? = null,
    @SerialName("enName") val enName: String? = null,
    @SerialName("description") val description: String? = null,
    @SerialName("profession") val profession: String? = null,
    @SerialName("enProfession") val enProfession: String? = null
)

@Serializable
data class VideosDto(
    @SerialName("trailers") val trailers: List<TrailerDto>? = null
)

@Serializable
data class TrailerDto(
    @SerialName("name") val name: String? = null,
    @SerialName("url") val url: String? = null,
    @SerialName("site") val site: String? = null,
    @SerialName("size") val size: Int? = null,
    @SerialName("type") val type: String? = null
)

@Serializable
data class BackdropDto(
    @SerialName("url") val url: String? = null,
    @SerialName("previewUrl") val previewUrl: String? = null
)

//@Serializable
//sealed class MovieType {
//    @Serializable
//    @SerialName("movie")
//    data object Movie : MovieType()
//
//    @Serializable
//    @SerialName("series")
//    data object Series : MovieType()
//}
@OptIn(ExperimentalSerializationApi::class)
@Serializable
@JsonIgnoreUnknownKeys
enum class MovieType {
    @SerialName("movie")
    MOVIE,

    @SerialName("tv-series")
    TV_SERIES,

    @SerialName("cartoon")
    CARTOON,

    @SerialName("anime")
    ANIME,

    @SerialName("animated-series")
    ANIMATED_SERIES,

    @SerialName("tv-show")
    TV_SHOW,

    @SerialName("string")
    UNKNOWN
}

package com.example.movies.data.mapper

import com.example.movies.data.api.model.CurrencyValueDto
import com.example.movies.data.api.model.FeesDto
import com.example.movies.data.api.model.ItemNameDto
import com.example.movies.data.api.model.LinkedMovieDto
import com.example.movies.data.api.model.MovieDto
import com.example.movies.data.api.model.MovieType
import com.example.movies.data.api.model.PersonInMovieDto
import com.example.movies.data.api.model.RatingDto
import com.example.movies.data.api.model.ReviewInfoDto
import com.example.movies.data.api.model.SearchMovieDto
import com.example.movies.data.api.model.ShortImageDto
import com.example.movies.data.api.model.TrailerDto
import com.example.movies.data.api.model.VideosDto
import com.example.movies.domain.model.CurrencyValue
import com.example.movies.domain.model.Image
import com.example.movies.domain.model.LinkedMovie
import com.example.movies.domain.model.Movie
import com.example.movies.domain.model.MovieFees
import com.example.movies.domain.model.MovieRating
import com.example.movies.domain.model.PersonMovie
import com.example.movies.domain.model.ReviewInfo
import com.example.movies.domain.model.SearchMovie
import com.example.movies.domain.model.Video
import com.example.movies.domain.model.Videos

fun MovieDto.toDomain(): Movie = Movie(
    id = id!!,
    name = name ?: alternativeName ?: enName ?: "",
    type = type ?: MovieType.UNKNOWN,
    year = year ?: 0,
    description = description ?: "",
    status = status,
    rating = rating!!.toDomain(),
    length = movieLength,
    ageRating = ageRating ?: 0,
    logo = logo?.toDomain() ?: Image(null, null),
    poster = poster?.toDomain() ?: Image(null, null),
    backdrop = backdrop?.toDomain() ?: Image(null, null),
    videos = videos?.toDomain(),
    genres = genres?.map {it.toDomain() } ?: emptyList(),
    countries = countries?.map { it.toDomain() } ?: emptyList(),
    persons = persons?.map { it.toDomain() } ?: emptyList(),
    reviewInfo = reviewInfo?.toDomain() ?: ReviewInfo(0, 0, ""),
    budget = budget?.toDomain() ?: CurrencyValue(0),
    fees = fees?.toDomain() ?: MovieFees(CurrencyValue(0), CurrencyValue(0)),
    similarMovies = similarMovies?.map { it.toDomain() },
    sequelsAndPrequels = sequelsAndPrequels?.map { it.toDomain() },
    top10 = top10,
    top250 = top250,
    isTicketsOnSale = ticketsOnSale ?: false,
    totalSeriesLength = totalSeriesLength,
    averageSeriesLength = seriesLength,
    isSeries = isSeries ?: false,
)

fun PersonInMovieDto.toDomain() = PersonMovie(
    id = id ?: 0,
    photo = photo ?: "",
    name = name ?: enName ?: "",
    description = description ?: "",
    profession = profession
)

fun LinkedMovieDto.toDomain() = LinkedMovie(
    id = id ?: 0,
    name = name ?: "",
    poster = poster?.toDomain(),
    rating = rating?.toDomain(),
    year = year ?: 0
)

fun FeesDto.toDomain() = MovieFees(
    world = world?.toDomain() ?: CurrencyValue(0),
    russia = russia?.toDomain() ?: CurrencyValue(0)
)

fun CurrencyValueDto.toDomain() = CurrencyValue(
    value = value ?: 0,
    currency = currency
)

fun ReviewInfoDto.toDomain() = ReviewInfo(
    count = count ?: 0,
    positiveCount = positiveCount ?: 0,
    percentage = percentage ?: ""
)

fun SearchMovieDto.toDomain() = SearchMovie(
    id = id ?: 0,
    name = name ?: "",
    alternativeName = alternativeName ?: "",
    enName = enName ?: "",
    logo = logo?.toDomain(),
    poster = poster?.toDomain(),
    backdrop = backdrop?.toDomain(),
    rating = rating?.toDomain(),
    year = year ?: 0,
    ageRating = ageRating ?: 0,
    top10 = top10,
    top250 = top250
)

fun ShortImageDto.toDomain() = Image(
    url = url ?: "",
    previewUrl = previewUrl ?: ""
)

fun RatingDto.toDomain() = MovieRating(
    kp = kp,
    imdb = imdb,
)

fun VideosDto.toDomain() = Videos(
    trailers = trailers?.map { it.toDomain() } ?: emptyList()
)

fun TrailerDto.toDomain() = Video(
    name = name ?: "",
    url = url ?: "",
    site = site ?: ""
)

fun ItemNameDto.toDomain() = name!!
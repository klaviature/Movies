package com.example.movies.presentation.ui.moviedetailscreen

import android.util.Log
import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.example.movies.data.repository.MoviesRepositoryImpl
import com.example.movies.domain.model.ApiResult
import com.example.movies.domain.model.Movie
import com.example.movies.domain.model.Review
import com.example.movies.domain.model.Video
import com.example.movies.domain.usecases.GetMovieUseCase
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.flow.asStateFlow
import kotlinx.coroutines.launch
import java.util.Collections.emptyList
import java.util.Locale.getDefault

class MovieDetailViewModel : ViewModel() {
    private val _state = MutableStateFlow(MovieDetailState())
    val state get() = _state.asStateFlow()

    private val getMovieUseCase = GetMovieUseCase(MoviesRepositoryImpl)

    fun loadMovieInfo(movieId: Int) {
        viewModelScope.launch {
            Log.d("MovieDetailViewModel", "loadMovieInfo: started")
            val result = getMovieUseCase(movieId)
            when (result) {
                ApiResult.Error.Forbidden -> {
                    Log.d("MovieDetailViewModel", "loadMovieInfo: forbidden")
                }

                ApiResult.Error.NotFound -> {
                    Log.d("MovieDetailViewModel", "loadMovieInfo: not fount")
                }

                ApiResult.Error.Unauthorized -> {
                    Log.d("MovieDetailViewModel", "loadMovieInfo: unauthorized")
                }

                is ApiResult.Error.Unknown -> {
                    Log.d("MovieDetailViewModel", "loadMovieInfo: unknown")
                }

                is ApiResult.Success -> {
                    _state.value = result.data.toUiState()
                    Log.d("MovieDetailViewModel", "loadMovieInfo: success")
                }
            }
        }
    }
}

data class MovieDetailState(
    val name: String = "",
    val type: String = "",
    val year: String = "",
    val description: String = "",
    val length: String? = null,
    val ageRating: String = "",
    val posterUrl: String? = null,
    val backdropUrl: String? = null,
    val logoUrl: String? = null,
    val ratingKp: String? = null,
    val ratingImdb: String? = null,
    val trailers: List<Video>? = null,
    val genres: List<String> = emptyList(),
    val countries: List<String> = emptyList(),
    val reviews: List<Review> = emptyList(),
    val isFavourite: Boolean = false,
    val isWatched: Boolean = false
)

fun Movie.toUiState() = MovieDetailState(
    name = name,
    type = type.name,
    year = year.toString(),
    description = description,
    length = "${length?.toString()} мин",
    ageRating = "$ageRating+",
    backdropUrl = backdrop.url,
    posterUrl = poster.url,
    logoUrl = logo.url,
    ratingKp = rating.kp?.toString(),
    ratingImdb = rating.imdb?.toString(),
    trailers = videos?.trailers,
    genres = genres.map {
        it.replaceFirstChar {
            if (it.isLowerCase()) it.titlecase(getDefault()) else it.toString()
        }
    },
    countries = countries
)

//fun MovieDeprecated.toUiState(): MovieDetailState = MovieDetailState(
//    name = name ?: "",
//    type = type ?: "",
//    year = year.toString() ?: "",
//    description = description ?: "",
//    length = movieLength.toString() ?: "",
//    ageRating = ageRating.toString() ?: "0+",
//    posterUrl = poster?.url ?: "",
//    ratingKp = rating?.kp.toString() ?: "0",
//    ratingImdb = rating?.imdb.toString() ?: "0",
//    trailers = videos?.trailers ?: emptyList(),
//    genres = genres ?: emptyList(),
//    countries = countries ?: emptyList(),
//    reviews = emptyList()
//)
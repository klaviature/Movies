package com.example.movies.presentation.ui.moviedetail

import android.app.Application
import android.util.Log
import androidx.lifecycle.AndroidViewModel
import androidx.lifecycle.viewModelScope
import com.example.movies.data.database.MovieDatabase
import com.example.movies.data.repository.MoviesRepositoryImplTest
import com.example.movies.data.repository.ReviewsRepositoryImpl
import com.example.movies.domain.model.ApiResult
import com.example.movies.domain.model.DataError
import com.example.movies.domain.model.Movie
import com.example.movies.domain.model.Result
import com.example.movies.domain.model.Review
import com.example.movies.domain.model.Video
import com.example.movies.domain.usecases.AddMovieToFavouritesUseCase
import com.example.movies.domain.usecases.AddMovieToWatchedUseCase
import com.example.movies.domain.usecases.CheckIfMovieIsFavouriteUseCase
import com.example.movies.domain.usecases.CheckIfMovieIsWatchedUseCase
import com.example.movies.domain.usecases.GetMovieUseCaseTest
import com.example.movies.domain.usecases.GetReviewsUseCase
import com.example.movies.domain.usecases.RemoveMovieFromFavouritesUseCase
import com.example.movies.domain.usecases.RemoveMovieFromWatchedUseCase
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.flow.asStateFlow
import kotlinx.coroutines.flow.update
import kotlinx.coroutines.launch
import java.util.Collections.emptyList
import java.util.Locale.getDefault

class MovieDetailViewModel(application: Application) : AndroidViewModel(application) {
    private val _state = MutableStateFlow(MovieDetailState())
    val state get() = _state.asStateFlow()

    private var reviewsPage: Int = 1

    private val getMovieUseCase = GetMovieUseCaseTest(
        MoviesRepositoryImplTest(
            dao = MovieDatabase.getInstance(getApplication<Application>().applicationContext)
                .movieDao()
        )
    )
    private val getReviewsUseCase = GetReviewsUseCase(ReviewsRepositoryImpl)

    private val addMovieToFavouritesUseCase = AddMovieToFavouritesUseCase(
        MoviesRepositoryImplTest(
            dao = MovieDatabase.getInstance(getApplication<Application>().applicationContext)
                .movieDao()
        )
    )
    private val removeMovieFromFavouritesUseCase = RemoveMovieFromFavouritesUseCase(
        MoviesRepositoryImplTest(
            dao = MovieDatabase.getInstance(getApplication<Application>().applicationContext)
                .movieDao()
        )
    )
    private val checkIfMovieIsFavouriteUseCase = CheckIfMovieIsFavouriteUseCase(
        MoviesRepositoryImplTest(
            dao = MovieDatabase.getInstance(getApplication<Application>().applicationContext)
                .movieDao()
        )
    )
    private val addMovieToWatchedUseCase = AddMovieToWatchedUseCase(
        MoviesRepositoryImplTest(
            dao = MovieDatabase.getInstance(getApplication<Application>().applicationContext)
                .movieDao()
        )
    )
    private val removeMovieFromWatchedUseCase = RemoveMovieFromWatchedUseCase(
        MoviesRepositoryImplTest(
            dao = MovieDatabase.getInstance(getApplication<Application>().applicationContext)
                .movieDao()
        )
    )
    private val checkIfMovieIsWatchedUseCase = CheckIfMovieIsWatchedUseCase(
        MoviesRepositoryImplTest(
            dao = MovieDatabase.getInstance(getApplication<Application>().applicationContext)
                .movieDao()
        )
    )

    private var currentMovie: Movie? = null

    init {
        Log.d("MovieDetailViewModel", "Viewmodel was created")
    }

    fun meme(movieId: Int) {
        viewModelScope.launch {
            getMovieUseCase.getMovie(movieId).collect { result ->
                when (result) {
                    is Result.Error -> {
                        when (result.error) {
                            is DataError.Network -> {
                                _state.update {
                                    it.copy(
                                        error = when (result.error) {
                                            is DataError.Network.Forbidden ->
                                                MovieDetailState.Error.Network.Forbidden

                                            is DataError.Network.NotFound ->
                                                MovieDetailState.Error.Network.NotFound

                                            is DataError.Network.Unauthorized ->
                                                MovieDetailState.Error.Network.Unauthorized

                                            is DataError.Network.Unknown ->
                                                MovieDetailState.Error.Network.Unknown(result.error.message)
                                        },
                                        isLoading = false
                                    )
                                }
                            }

                            is DataError.Local -> {
                                _state.update {
                                    it.copy(
                                        error = when (result.error) {
                                            DataError.Local.NotFound -> MovieDetailState.Error.Local.Critical
                                            DataError.Local.Critical -> MovieDetailState.Error.Local.Critical
                                        },
                                        isLoading = false
                                    )
                                }
                            }
                        }
                        Log.d("MovieDetailViewModel", "meme: error ${_state.value}")
//                        _state.update {
//                            it.copy(isLoading = false)
//                        }
                    }

                    Result.Loading -> _state.update {
                        Log.d("MovieDetailViewModel", "meme: loading")
                        it.copy(isLoading = true)
                    }

                    is Result.Success -> {
                        _state.update {
                            it.copy(isLoading = false, movie = result.data.toUiState())
                        }
                        currentMovie = result.data
                        Log.d("MovieDetailViewModel", "meme: success ${_state.value}")
                    }
                }
            }
        }
        viewModelScope.launch {
            checkIfMovieIsFavouriteUseCase(movieId).collect { result ->
                when (result) {
                    is Result.Error -> {
                        _state.update {
                            it.copy(
                                error = MovieDetailState.Error.Local.Critical,
                                isFavouriteLoading = false
                            )
                        }
                    }

                    Result.Loading -> _state.update {
                        it.copy(isFavouriteLoading = true)
                    }

                    is Result.Success -> {
                        _state.update {
                            it.copy(
                                isFavourite = result.data,
                                isFavouriteLoading = false
                            )
                        }
                    }
                }
            }
        }
        viewModelScope.launch {
            checkIfMovieIsWatchedUseCase(movieId).collect { result ->
                when (result) {
                    is Result.Error -> {
                        _state.update {
                            it.copy(
                                error = MovieDetailState.Error.Local.Critical,
                                isWatchedLoading = false
                            )
                        }
                    }

                    Result.Loading -> _state.update { it.copy(isWatchedLoading = true) }
                    is Result.Success -> {
                        _state.update {
                            it.copy(
                                isWatched = result.data,
                                isWatchedLoading = false
                            )
                        }
                    }
                }
            }
        }
    }

    fun toggleFavouriteStatus() {
        val isFavourite = _state.value.isFavourite
        val movie = currentMovie ?: return

        viewModelScope.launch {
            if (isFavourite) {
                removeMovieFromFavouritesUseCase(movie.id).collect { result ->
                    when (result) {
                        is Result.Error -> _state.update {
                            it.copy(
                                error = MovieDetailState.Error.Local.Critical,
                                isFavouriteLoading = false
                            )
                        }

                        Result.Loading -> _state.update {
                            it.copy(isFavouriteLoading = true)
                        }

                        is Result.Success -> _state.update {
                            it.copy(
                                isFavouriteLoading = false,
                                isFavourite = false,
                                message = "Фильм удален из Избранного"
                            )
                        }
                    }
                }
            } else {
                addMovieToFavouritesUseCase(movie).collect { result ->
                    when (result) {
                        is Result.Error -> _state.update {
                            it.copy(
                                error = MovieDetailState.Error.Local.Critical,
                                isFavouriteLoading = false
                            )
                        }

                        Result.Loading -> _state.update {
                            it.copy(isFavouriteLoading = true)
                        }

                        is Result.Success -> _state.update {
                            it.copy(
                                isFavouriteLoading = false,
                                isFavourite = true,
                                message = "Фильм добавлен в Избранное"
                            )
                        }
                    }
                }
            }
        }
    }

    fun toggleWatchedStatus() {
        val isWatched = _state.value.isWatched
        val movie = currentMovie ?: return

        viewModelScope.launch {
            if (isWatched) {
                removeMovieFromWatchedUseCase(movie.id).collect { result ->
                    when (result) {
                        is Result.Error -> _state.update {
                            it.copy(
                                error = MovieDetailState.Error.Local.Critical,
                                isWatchedLoading = false
                            )
                        }

                        Result.Loading -> _state.update {
                            it.copy(isWatchedLoading = true)
                        }

                        is Result.Success -> _state.update {
                            it.copy(
                                isWatchedLoading = false,
                                isWatched = false,
                                message = "Фильм удален из Просмотренного"
                            )
                        }
                    }
                }
            } else {
                addMovieToWatchedUseCase(movie).collect { result ->
                    when (result) {
                        is Result.Error -> _state.update {
                            it.copy(
                                error = MovieDetailState.Error.Local.Critical,
                                isWatchedLoading = false
                            )
                        }

                        Result.Loading -> _state.update {
                            it.copy(isWatchedLoading = true)
                        }

                        is Result.Success -> _state.update {
                            it.copy(
                                isWatchedLoading = false,
                                isWatched = true,
                                message = "Фильм добавлен в Просмотренное"
                            )
                        }
                    }
                }
            }
        }
    }

    fun loadReviews(movieId: Int) {
        viewModelScope.launch {
            Log.d("MovieDetailViewModel", "loadReviews: started")
            val result = getReviewsUseCase(movieId, reviewsPage)
            when (result) {
                ApiResult.Error.Forbidden -> {
                    Log.d("MovieDetailViewModel", "loadReviews: forbidden")
                }

                ApiResult.Error.NotFound -> {
                    Log.d("MovieDetailViewModel", "loadReviews: not found")
                }

                ApiResult.Error.Unauthorized -> {
                    Log.d("MovieDetailViewModel", "loadReviews: unauthorized")
                }

                is ApiResult.Error.Unknown -> {
                    Log.d("MovieDetailViewModel", "loadReviews: unknown")
                }

                is ApiResult.Success -> {
                    Log.d("MovieDetailViewModel", "loadReviews: success")
                    _state.value = _state.value.copy(reviews = result.data)
                }
            }
            reviewsPage++
        }
    }

    fun errorShown() = _state.update { it.copy(error = null) }

    fun messageShown() = _state.update { it.copy(message = null) }
}

data class MovieDetailState(
    val isLoading: Boolean = false,
    val movie: MovieUi = MovieUi(),
    val reviews: List<Review> = emptyList(),
    val error: Error? = null,
    val message: String? = null,
    val isWatchedLoading: Boolean = false,
    val isFavouriteLoading: Boolean = false,
    val isWatched: Boolean = false,
    val isFavourite: Boolean = false
) {
    data class MovieUi(
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
        val countries: List<String> = emptyList()
    )

    sealed interface Error {
        sealed interface Network : Error {
            data object Forbidden : Network
            data object NotFound : Network
            data object Unauthorized : Network
            data class Unknown(val message: String? = null) : Network
        }

        sealed interface Local : Error {
            data object Critical : Local
        }
    }
}

fun Movie.toUiState() = MovieDetailState.MovieUi(
    name = name,
    type = type.name,
    year = year.toString(),
    description = description,
    length = "${length?.toString()} мин",
    ageRating = "$ageRating+",
    backdropUrl = backdrop.url ?: backdrop.previewUrl,
    posterUrl = poster.url ?: poster.previewUrl,
    logoUrl = logo.url ?: logo.previewUrl,
    ratingKp = rating.kp?.let { String.format("%.1f", it) },
    ratingImdb = rating.imdb?.let { String.format("%.1f", it) },
    trailers = videos?.trailers,
    genres = genres.map {
        it.replaceFirstChar {
            if (it.isLowerCase()) it.titlecase(getDefault()) else it.toString()
        }
    },
    countries = countries
)
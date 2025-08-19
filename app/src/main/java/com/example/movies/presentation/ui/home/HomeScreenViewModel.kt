package com.example.movies.presentation.ui.home

import android.util.Log
import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.example.movies.data.repository.MoviesRepositoryImpl
import com.example.movies.domain.model.ApiResult
import com.example.movies.domain.model.Movie
import com.example.movies.domain.usecases.recommended.GetRecommendedMoviesUseCase
import com.example.movies.domain.usecases.recommended.SearchMoviesUseCase
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.flow.asStateFlow
import kotlinx.coroutines.launch

class HomeScreenViewModel : ViewModel() {

    private val getRecommendedMoviesUseCase = GetRecommendedMoviesUseCase(MoviesRepositoryImpl)
    private val searchMoviesUseCase = SearchMoviesUseCase(MoviesRepositoryImpl)

    private val _movies = MutableStateFlow<HomeScreenState>(HomeScreenState())
    val movies = _movies.asStateFlow()

    init {
        getRecommendedMovies()
    }

    fun getRecommendedMovies() {
        viewModelScope.launch {
            Log.d("HomeScreenViewModel", "getRecommendedMovies: started")
            _movies.value = _movies.value.copy(isRefreshing = true)
            val result = getRecommendedMoviesUseCase(1)
            when (result) {
                ApiResult.Error.Forbidden -> {
                    Log.d("HomeScreenViewModel", "getRecommendedMovies: forbidden")
                }

                ApiResult.Error.NotFound -> {
                    Log.d("HomeScreenViewModel", "getRecommendedMovies: not found")
                }

                ApiResult.Error.Unauthorized -> {
                    Log.d("HomeScreenViewModel", "getRecommendedMovies: unauthorized")
                }

                is ApiResult.Error.Unknown -> {
                    Log.d("HomeScreenViewModel", "getRecommendedMovies: ${result.message}")
                }

                is ApiResult.Success -> {
                    _movies.value = _movies.value.copy(movies = result.data)
                    Log.d("HomeScreenViewModel", "getRecommendedMovies: success")
                    Log.d("HomeScreenViewModel", "getRecommendedMovies: ${result.data}")
                }
            }
            _movies.value = _movies.value.copy(isRefreshing = false)
        }
    }
}

data class HomeScreenState(
    val movies: List<Movie> = emptyList(),
    val isRefreshing: Boolean = false
)
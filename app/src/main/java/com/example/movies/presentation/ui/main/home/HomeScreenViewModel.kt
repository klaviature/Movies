package com.example.movies.presentation.ui.main.home

import android.app.Application
import android.util.Log
import androidx.lifecycle.AndroidViewModel
import androidx.lifecycle.viewModelScope
import com.example.movies.data.database.MovieDatabase
import com.example.movies.data.repository.MoviesRepositoryImpl
import com.example.movies.data.repository.MoviesRepositoryImplTest
import com.example.movies.domain.model.DataError
import com.example.movies.domain.model.Movie
import com.example.movies.domain.model.Result
import com.example.movies.domain.usecases.recommended.GetRecommendedMoviesUseCase
import com.example.movies.domain.usecases.recommended.SearchMoviesUseCase
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.flow.asStateFlow
import kotlinx.coroutines.flow.update
import kotlinx.coroutines.launch

class HomeScreenViewModel(application: Application) : AndroidViewModel(application) {

    private val getRecommendedMoviesUseCase = GetRecommendedMoviesUseCase(
        MoviesRepositoryImplTest(
            dao = MovieDatabase.getInstance(getApplication<Application>().applicationContext).movieDao()
        )
    )
    private val searchMoviesUseCase = SearchMoviesUseCase(MoviesRepositoryImpl)

    private val _state = MutableStateFlow<HomeScreenState>(HomeScreenState())
    val state = _state.asStateFlow()

    init {
//        getRecommendedMovies()
        Log.d("HomeScreenViewModel", "Viewmodel was created")
    }

    fun load() {
        viewModelScope.launch {
            getRecommendedMoviesUseCase(1).collect { result ->
                when (result) {
                    is Result.Error -> {
                        when (result.error) {
                            is DataError.Network.Forbidden -> {
                                Log.d("HomeScreenViewModel", "meme: forbidden")
                                _state.update {
                                    it.copy(error = HomeScreenState.Error.Forbidden)
                                }
                            }
                            is DataError.Network.NotFound -> {
                                Log.d("HomeScreenViewModel", "meme: not found")
                                _state.update {
                                    it.copy(error = HomeScreenState.Error.NotFound)
                                }
                            }
                            is DataError.Network.Unauthorized -> {
                                Log.d("HomeScreenViewModel", "meme: unauthorized")
                                _state.update {
                                    it.copy(error = HomeScreenState.Error.Unauthorized)
                                }
                            }
                            is DataError.Network.Unknown -> {
                                Log.d("HomeScreenViewModel", "meme: unknown")
                                _state.update {
                                    it.copy(error = HomeScreenState.Error.Unknown(result.error.message))
                                }
                            }
                        }
                        _state.update { it.copy(isRefreshing = false) }
                    }
                    Result.Loading -> {
                        Log.d("HomeScreenViewModel", "meme: loading")
                        _state.update {
                            it.copy(isRefreshing = true)
                        }
                    }
                    is Result.Success -> {
                        Log.d("HomeScreenViewModel", "meme: success")
                        _state.update {
                            it.copy(movies = result.data, isRefreshing = false)
                        }
                    }
                }
            }
        }
    }

//    fun getRecommendedMovies() {
//        viewModelScope.launch {
//            Log.d("HomeScreenViewModel", "getRecommendedMovies: started")
//            _movies.value = _movies.value.copy(isRefreshing = true)
//            val result = getRecommendedMoviesUseCase(1)
//            when (result) {
//                ApiResult.Error.Forbidden -> {
//                    Log.d("HomeScreenViewModel", "getRecommendedMovies: forbidden")
//                }
//
//                ApiResult.Error.NotFound -> {
//                    Log.d("HomeScreenViewModel", "getRecommendedMovies: not found")
//                }
//
//                ApiResult.Error.Unauthorized -> {
//                    Log.d("HomeScreenViewModel", "getRecommendedMovies: unauthorized")
//                }
//
//                is ApiResult.Error.Unknown -> {
//                    Log.d("HomeScreenViewModel", "getRecommendedMovies: ${result.message}")
//                }
//
//                is ApiResult.Success -> {
//                    _movies.value = _movies.value.copy(movies = result.data)
//                    Log.d("HomeScreenViewModel", "getRecommendedMovies: success")
////                    Log.d("HomeScreenViewModel", "getRecommendedMovies: ${result.data}")
//                }
//            }
//            _movies.value = _movies.value.copy(isRefreshing = false)
//        }
//    }
}

data class HomeScreenState(
    val movies: List<Movie> = emptyList(),
    val isRefreshing: Boolean = false,
    val error: Error? = null
) {
    sealed interface Error {
        data object Forbidden : Error
        data object NotFound : Error
        data object Unauthorized : Error
        data class Unknown(val message: String? = null) : Error
    }
}


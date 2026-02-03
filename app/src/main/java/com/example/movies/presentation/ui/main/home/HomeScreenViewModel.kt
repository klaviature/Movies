package com.example.movies.presentation.ui.main.home

import android.util.Log
import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import androidx.room.util.query
import com.example.movies.domain.model.DataError
import com.example.movies.domain.model.Movie
import com.example.movies.domain.model.Result
import com.example.movies.domain.usecases.recommended.GetRecommendedMoviesUseCase
import dagger.hilt.android.lifecycle.HiltViewModel
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.flow.asStateFlow
import kotlinx.coroutines.flow.debounce
import kotlinx.coroutines.flow.distinctUntilChanged
import kotlinx.coroutines.flow.map
import kotlinx.coroutines.flow.update
import kotlinx.coroutines.launch
import javax.inject.Inject

@HiltViewModel
class HomeScreenViewModel @Inject constructor(
    private val getRecommendedMoviesUseCase: GetRecommendedMoviesUseCase
) : ViewModel() {
    private val _state = MutableStateFlow<HomeScreenState>(HomeScreenState())
    val state = _state.asStateFlow()

    init {
        load()
        Log.d("HomeScreenViewModel", "Viewmodel was created")

        viewModelScope.launch {
            _state
                .map { it.searchQuery }
                .distinctUntilChanged()
                .debounce(500)
                .collect { query ->
                    resetAndLoad(query)
                }
        }
    }

    private fun resetAndLoad(query: String) {
        _state.update { it.copy(movies = emptyList(), page = 1, searchQuery = query) }
    }

    fun errorShown() = _state.update { it.copy(error = null) }

    fun load() {
        viewModelScope.launch {
            getRecommendedMoviesUseCase(page = state.value.page).collect { result ->
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
                        _state.update { it.copy(page = it.page + 1) }
                    }
                }
            }
        }
    }
}

data class HomeScreenState(
    val movies: List<Movie> = emptyList(),
    val searchQuery: String = "",
    val page: Int = 1,
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


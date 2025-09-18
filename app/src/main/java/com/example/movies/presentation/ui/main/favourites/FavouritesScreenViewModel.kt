package com.example.movies.presentation.ui.main.favourites

import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.example.movies.domain.model.Movie
import com.example.movies.domain.model.Result
import com.example.movies.domain.usecases.favourites.GetFavouritesMoviesUseCase
import dagger.hilt.android.lifecycle.HiltViewModel
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.flow.asStateFlow
import kotlinx.coroutines.flow.update
import kotlinx.coroutines.launch
import javax.inject.Inject

@HiltViewModel
class FavouritesScreenViewModel @Inject constructor(
    private val getFavouritesMoviesUseCase: GetFavouritesMoviesUseCase
) : ViewModel() {
    private val _state = MutableStateFlow(FavouritesScreenState())
    val state get() = _state.asStateFlow()

    init {
        getFavouriteMovies()
    }

    private fun getFavouriteMovies() {
        viewModelScope.launch {
            getFavouritesMoviesUseCase().collect { result ->
                when (result) {
                    is Result.Error -> _state.update {
                        it.copy(isLoading = false, error = FavouritesScreenState.Error.Critical)
                    }
                    Result.Loading -> _state.update { it.copy(isLoading = true) }
                    is Result.Success -> _state.update {
                        it.copy(isLoading = false, movies = result.data)
                    }
                }
            }
        }
    }

    fun errorShown() = _state.update { it.copy(error = null) }

    fun messageShown() = _state.update { it.copy(message = null) }
}

data class FavouritesScreenState(
    val isLoading: Boolean = false,
    val error: Error? = null,
    val message: Message? = null,
    val movies: List<Movie> = emptyList()
) {
    sealed interface Error {
        data object Critical : Error
    }

    sealed interface Message {

    }
}
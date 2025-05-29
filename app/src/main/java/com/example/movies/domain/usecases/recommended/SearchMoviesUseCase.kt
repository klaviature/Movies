package com.example.movies.domain.usecases.recommended

import com.example.movies.domain.model.Movie
import com.example.movies.domain.repositories.MoviesRepository
import kotlinx.coroutines.flow.Flow

class SearchMoviesUseCase(
    private val repository: MoviesRepository
) {
    suspend operator fun invoke(query: String, page: Int): Flow<List<Movie>> {
        return repository.searchMovies(query, page)
    }
}
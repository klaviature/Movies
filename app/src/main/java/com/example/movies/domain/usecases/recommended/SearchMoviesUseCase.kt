package com.example.movies.domain.usecases.recommended

import com.example.movies.domain.model.ApiResult
import com.example.movies.domain.model.SearchMovie
import com.example.movies.domain.repositories.MoviesRepository

class SearchMoviesUseCase(
    private val repository: MoviesRepository
) {
    suspend operator fun invoke(query: String, page: Int): ApiResult<List<SearchMovie>> {
        return repository.searchMovies(query, page)
    }
}
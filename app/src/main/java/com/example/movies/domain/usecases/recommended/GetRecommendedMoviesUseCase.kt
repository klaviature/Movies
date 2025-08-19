package com.example.movies.domain.usecases.recommended

import com.example.movies.domain.model.ApiResult
import com.example.movies.domain.model.Movie
import com.example.movies.domain.repositories.MoviesRepository

class GetRecommendedMoviesUseCase(
    private val repository: MoviesRepository
) {
    suspend operator fun invoke(page: Int): ApiResult<List<Movie>> {
        return repository.getRecommendedMovies(page)
    }
}
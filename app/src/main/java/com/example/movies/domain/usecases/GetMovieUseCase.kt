package com.example.movies.domain.usecases

import com.example.movies.domain.model.ApiResult
import com.example.movies.domain.model.Movie
import com.example.movies.domain.repositories.MoviesRepository

class GetMovieUseCase(
    private val repository: MoviesRepository
) {
    suspend operator fun invoke(movieId: Int): ApiResult<Movie> {
        return repository.getMovieDetails(movieId)
    }
}
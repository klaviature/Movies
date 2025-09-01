package com.example.movies.domain.usecases.recommended

import com.example.movies.domain.model.DataError
import com.example.movies.domain.model.Movie
import com.example.movies.domain.model.Result
import com.example.movies.domain.repositories.MoviesRepositoryTest
import kotlinx.coroutines.flow.Flow
import javax.inject.Inject

class GetRecommendedMoviesUseCase @Inject constructor(
    private val repository: MoviesRepositoryTest
) {
    suspend operator fun invoke(page: Int): Flow<Result<List<Movie>, DataError.Network>> {
        return repository.getRecommendedMovies(page)
    }
}
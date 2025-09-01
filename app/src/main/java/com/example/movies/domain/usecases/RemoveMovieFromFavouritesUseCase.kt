package com.example.movies.domain.usecases

import com.example.movies.domain.model.DataError
import com.example.movies.domain.model.Result
import com.example.movies.domain.repositories.MoviesRepositoryTest
import kotlinx.coroutines.flow.Flow

class RemoveMovieFromFavouritesUseCase(
    private val moviesRepository: MoviesRepositoryTest
) {
    suspend operator fun invoke(movieId: Int): Flow<Result<Unit, DataError.Local>> {
        return moviesRepository.removeMovieFromFavourite(movieId)
    }
}
package com.example.movies.domain.usecases

import com.example.movies.domain.model.DataError
import com.example.movies.domain.model.Result
import com.example.movies.domain.repositories.MoviesRepositoryTest
import kotlinx.coroutines.flow.Flow

class CheckIfMovieIsFavouriteUseCase(
    private val repository: MoviesRepositoryTest
) {
    suspend operator fun invoke(movieId: Int): Flow<Result<Boolean, DataError.Local>> {
        return repository.isMovieInFavourite(movieId)
    }
}
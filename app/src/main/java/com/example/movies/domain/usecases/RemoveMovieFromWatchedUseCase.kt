package com.example.movies.domain.usecases

import com.example.movies.domain.model.DataError
import com.example.movies.domain.model.Result
import com.example.movies.domain.repositories.MoviesRepositoryTest
import kotlinx.coroutines.flow.Flow
import javax.inject.Inject

class RemoveMovieFromWatchedUseCase @Inject constructor(
    private val repositoryTest: MoviesRepositoryTest
) {
    suspend operator fun invoke(movieId: Int): Flow<Result<Unit, DataError.Local>> {
        return repositoryTest.removeMovieFromWatched(movieId)
    }
}
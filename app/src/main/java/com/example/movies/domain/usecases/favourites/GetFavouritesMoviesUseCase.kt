package com.example.movies.domain.usecases.favourites

import com.example.movies.domain.model.DataError
import com.example.movies.domain.model.Movie
import com.example.movies.domain.model.MovieDeprecated
import com.example.movies.domain.model.Result
import com.example.movies.domain.repositories.FavouritesRepository
import com.example.movies.domain.repositories.MoviesRepositoryTest
import kotlinx.coroutines.flow.Flow
import javax.inject.Inject

class GetFavouritesMoviesUseCase @Inject constructor(
    private val repositoryTest: MoviesRepositoryTest
) {
    suspend operator fun invoke(): Flow<Result<List<Movie>, DataError.Local>> {
        return repositoryTest.getFavouriteMovies()
    }
}
package com.example.movies.domain.repositories

import com.example.movies.domain.model.DataError
import com.example.movies.domain.model.Movie
import com.example.movies.domain.model.Result
import com.example.movies.domain.model.SearchMovie
import kotlinx.coroutines.flow.Flow

interface MoviesRepositoryTest {

    suspend fun getRecommendedMovies(page: Int): Flow<Result<List<Movie>, DataError.Network>>

    suspend fun getMovieDetailsFromNetwork(movieId: Int): Flow<Result<Movie, DataError.Network>>

    suspend fun getMovieDetailsFromLocal(movieId: Int): Flow<Result<Movie, DataError.Local>>

    suspend fun saveOrUpdateMovie(movie: Movie): Flow<Result<Unit, DataError.Local>>

    suspend fun getWatchedMovies(): Flow<Result<List<Movie>, DataError.Local>>

    suspend fun getFavouriteMovies(): Flow<Result<List<Movie>, DataError.Local>>

    suspend fun searchMovies(query: String, page: Int): Flow<Result<List<SearchMovie>, DataError.Network>>

    suspend fun addMovieToWatched(movie: Movie): Flow<Result<Unit, DataError.Local>>

    suspend fun addMovieToFavourite(movie: Movie): Flow<Result<Unit, DataError.Local>>

    suspend fun removeMovieFromWatched(movieId: Int): Flow<Result<Unit, DataError.Local>>

    suspend fun removeMovieFromFavourite(movieId: Int): Flow<Result<Unit, DataError.Local>>

    suspend fun isMovieInWatched(movieId: Int): Flow<Result<Boolean, DataError.Local>>

    suspend fun isMovieInFavourite(movieId: Int): Flow<Result<Boolean, DataError.Local>>
}
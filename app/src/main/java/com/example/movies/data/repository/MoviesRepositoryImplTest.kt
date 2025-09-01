package com.example.movies.data.repository

import android.util.Log
import com.example.movies.data.api.ApiFactoryCoroutines
import com.example.movies.data.database.MovieDao
import com.example.movies.data.database.model.MovieEntity
import com.example.movies.data.database.model.MovieWithDetails
import com.example.movies.data.mapper.toDomain
import com.example.movies.domain.model.DataError
import com.example.movies.domain.model.Image
import com.example.movies.domain.model.Movie
import com.example.movies.domain.model.MovieRating
import com.example.movies.domain.model.Result
import com.example.movies.domain.model.SearchMovie
import com.example.movies.domain.repositories.MoviesRepositoryTest
import kotlinx.coroutines.flow.Flow
import kotlinx.coroutines.flow.flow
import retrofit2.HttpException
import javax.inject.Inject

class MoviesRepositoryImplTest @Inject constructor(
    private val dao: MovieDao
) : MoviesRepositoryTest {

    private companion object {
        const val TAG = "MoviesRepositoryImplTest"
    }

    private val apiService = ApiFactoryCoroutines.apiService

    override suspend fun getRecommendedMovies(page: Int): Flow<Result<List<Movie>, DataError.Network>> = flow {
        emit(Result.Loading)
        try {
            val response = apiService.getMovies(page)
            if (response.isSuccessful) {
                Log.d(TAG, "getRecommendedMovies: response: successful")
                emit(
                    Result.Success(
                        response.body()?.docs?.map { it.toDomain() } ?: emptyList()
                    )
                )
            } else {
                val message = response.message()
                Log.d(TAG, "getRecommendedMovies: response: error: $message")
                emit(
                    when (response.code()) {
                        401 -> Result.Error(DataError.Network.Unauthorized(message))
                        403 -> Result.Error(DataError.Network.Forbidden(message))
                        404 -> Result.Error(DataError.Network.NotFound(message))
                        else -> Result.Error(DataError.Network.Unknown(message))
                    }
                )
            }
        } catch (e: HttpException) {
            Log.d(TAG, "getRecommendedMovies: exception: ${e.message}")
            emit(Result.Error(DataError.Network.Unknown(e.message)))
        }
    }

    override suspend fun getMovieDetailsFromNetwork(movieId: Int): Flow<Result<Movie, DataError.Network>> = flow {
        try {
            emit(Result.Loading)
            val response = apiService.getMovie(movieId)
            if (response.isSuccessful) {
                Log.d(TAG, "getMovieDetailsFromNetwork: success")
                emit(Result.Success(response.body()!!.toDomain()))
            } else {
                val message = response.message()
                Log.d(TAG, "getMovieDetailsFromNetwork: error: $message")
                emit(
                    when (response.code()) {
                        401 -> Result.Error(DataError.Network.Unauthorized(message))
                        403 -> Result.Error(DataError.Network.Forbidden(message))
                        404 -> Result.Error(DataError.Network.NotFound(message))
                        else -> Result.Error(DataError.Network.Unknown(message))
                    }
                )
            }
        } catch(e: HttpException) {
            Log.d(TAG, "getMovieDetailsFromNetwork: error: ${e.message}")
            emit(Result.Error(DataError.Network.Unknown(e.message)))
        }
    }

    override suspend fun saveOrUpdateMovie(movie: Movie): Flow<Result<Unit, DataError.Local>> = flow {
        try {
            emit(Result.Loading)
            dao.insertMovieWithDetails(
                MovieWithDetails(
                    entity = movie.toEntity(),
                    genres = movie.genres,
                    countries = movie.countries
                )
            )
            emit(Result.Success(Unit))
        } catch (e: Exception) {
            emit(Result.Error(DataError.Local.Critical))
        }
    }

    override suspend fun getMovieDetailsFromLocal(movieId: Int): Flow<Result<Movie, DataError.Local>> = flow {
        try {
            emit(Result.Loading)
            val movie = dao.getMovie(movieId)
            val countries = dao.getMovieCountries(movieId).map { it.country }
            val genres = dao.getMovieGenres(movieId).map { it.genre }
            val movieWithDetails = MovieWithDetails(
                entity = movie,
                genres = genres,
                countries = countries
            )
            Log.d(TAG, "getMovieDetailsFromLocal: successful")
            emit(Result.Success(movieWithDetails.toDomain()))
        } catch (e: Exception) {
            Log.d(TAG, "getMovieDetailsFromLocal: error: ${e.message}")
            emit(Result.Error(DataError.Local.Critical))
        }
    }

    override suspend fun getWatchedMovies(): Flow<Result<List<Movie>, DataError.Local>> = flow {
        try {
            emit(Result.Loading)
            val movies = dao.getWatchedMovies()
            val genresByMovie = dao
                .getGenresForMovies(movies.map { it.id })
                .groupBy { it.movieId }
            val countriesByMovie = dao
                .getCountriesForMovies(movies.map { it.id })
                .groupBy { it.movieId }

            val moviesWithDetails = movies.map { movie ->
                MovieWithDetails(
                    entity = movie,
                    genres = genresByMovie[movie.id]?.map { it.genre } ?: emptyList(),
                    countries = countriesByMovie[movie.id]?.map { it.country } ?: emptyList()
                )
            }
            Log.d(TAG, "getWatchedMovies: dao: getWatchedMoviesWithDetails: successful")
            emit(Result.Success(moviesWithDetails.map { it.toDomain() }))
        } catch (e: Exception) {
            Log.d(TAG, "getWatchedMovies: dao: getWatchedMoviesWithDetails: error: ${e.message}")
            emit(Result.Error(DataError.Local.Critical))
        }
    }

    override suspend fun getFavouriteMovies(): Flow<Result<List<Movie>, DataError.Local>> = flow {
        try {
            emit(Result.Loading)
            val movies = dao.getFavouriteMovies()
            val genresByMovie = dao
                .getGenresForMovies(movies.map { it.id })
                .groupBy { it.movieId }
            val countriesByMovie = dao
                .getCountriesForMovies(movies.map { it.id })
                .groupBy { it.movieId }

            val moviesWithDetails = movies.map { movie ->
                MovieWithDetails(
                    entity = movie,
                    genres = genresByMovie[movie.id]?.map { it.genre } ?: emptyList(),
                    countries = countriesByMovie[movie.id]?.map { it.country } ?: emptyList()
                )
            }
            Log.d(TAG, "getFavouriteMovies: dao: getFavouriteMoviesWithDetails: successful")
            emit(Result.Success(moviesWithDetails.map { it.toDomain() }))
        } catch (e: Exception) {
            Log.d(TAG, "getFavouriteMovies: dao: getFavouriteMoviesWithDetails: error: ${e.message}")
            emit(Result.Error(DataError.Local.Critical))
        }
    }

    override suspend fun searchMovies(
        query: String,
        page: Int
    ): Flow<Result<List<SearchMovie>, DataError.Network>> = flow {
        try {
            emit(Result.Loading)
            val response = apiService.getMovies(query, page)
            if (response.isSuccessful) {
                Log.d(TAG, "searchMovies: response: successful")
                emit(Result.Success(response.body()!!.docs.map { it.toDomain() }))
            } else {
                val message = response.message()
                Log.d(TAG, "searchMovies: response: error: $message")
                emit(
                    when (response.code()) {
                        401 -> Result.Error(DataError.Network.Unauthorized(message))
                        403 -> Result.Error(DataError.Network.Forbidden(message))
                        404 -> Result.Error(DataError.Network.NotFound(message))
                        else -> Result.Error(DataError.Network.Unknown(message))
                    }
                )
            }
        } catch (e: HttpException) {
            Log.d(TAG, "searchMovies: exception: ${e.message}")
            emit(Result.Error(DataError.Network.Unknown(e.message)))
        }
    }

    override suspend fun addMovieToWatched(movie: Movie): Flow<Result<Unit, DataError.Local>> = flow {
        try {
            emit(Result.Loading)
            dao.addMovieToWatchedWithDetails(
                MovieWithDetails(
                    entity = movie.toEntity(),
                    genres = movie.genres,
                    countries = movie.countries
                )
            )
            Log.d(TAG, "addMovieToWatched: dao: addWatched: successful")
            emit(Result.Success(Unit))
        } catch (e: Exception) {
            Log.d(TAG, "addMovieToWatched: dao: addWatched: error: ${e.message}")
            emit(Result.Error(DataError.Local.Critical))
        }
    }

    override suspend fun addMovieToFavourite(movie: Movie): Flow<Result<Unit, DataError.Local>> = flow{
        try {
            emit(Result.Loading)
            dao.addMovieToFavouritesWithDetails(
                MovieWithDetails(
                    entity = movie.toEntity(),
                    genres = movie.genres,
                    countries = movie.countries
                )
            )
            Log.d(TAG, "addMovieToFavourite: dao: addFavourite: successful")
            emit(Result.Success(Unit))
        } catch (e: Exception) {
            Log.d(TAG, "addMovieToFavourite: dao: addFavourite: error: ${e.message}")
            emit(Result.Error(DataError.Local.Critical))
        }
    }

    override suspend fun removeMovieFromWatched(movieId: Int): Flow<Result<Unit, DataError.Local>> = flow {
        try {
            emit(Result.Loading)
            dao.removeMovieFromWatched(movieId)
            Log.d(TAG, "removeMovieFromWatched: dao: removeWatched: successful")
            emit(Result.Success(Unit))
        } catch (e: Exception) {
            Log.d(TAG, "removeMovieFromWatched: dao: removeWatched: error: ${e.message}")
            emit(Result.Error(DataError.Local.Critical))
        }
    }

    override suspend fun removeMovieFromFavourite(movieId: Int): Flow<Result<Unit, DataError.Local>> = flow {
        try {
            emit(Result.Loading)
            dao.removeMovieFromFavourites(movieId)
            Log.d(TAG, "removeMovieFromFavourite: dao: removeFavourite: successful")
            emit(Result.Success(Unit))
        } catch (e: Exception) {
            Log.d(TAG, "removeMovieFromFavourite: dao: removeFavourite: error: ${e.message}")
            emit(Result.Error(DataError.Local.Critical))
        }
    }

    override suspend fun isMovieInWatched(movieId: Int): Flow<Result<Boolean, DataError.Local>> = flow {
        try {
            emit(Result.Loading)
            val result = dao.isWatched(movieId)
            Log.d(TAG, "isMovieInWatched: dao: isWatched: successful")
            emit(Result.Success(result))
        } catch (e: Exception) {
            Log.d(TAG, "isMovieInWatched: dao: isWatched: error: ${e.message}")
            emit(Result.Error(DataError.Local.Critical))
        }
    }

    override suspend fun isMovieInFavourite(movieId: Int): Flow<Result<Boolean, DataError.Local>> = flow {
        try {
            emit(Result.Loading)
            val result = dao.isFavourite(movieId)
            Log.d(TAG, "isMovieInFavourite: dao: isFavourite: successful")
            emit(Result.Success(result))
        } catch (e: Exception) {
            Log.d(TAG, "isMovieInFavourite: dao: isFavourite: error: ${e.message}")
            emit(Result.Error(DataError.Local.Critical))
        }
    }
}

fun Movie.toEntity() = MovieEntity(
    id = id,
    name = name,
    type = type,
    year = year,
    description = description,
    length = length,
    ageRating = ageRating,
    logoUrl = logo.url ?: logo.previewUrl,
    posterUrl = poster.url ?: poster.previewUrl,
    backdropUrl = backdrop.url ?: backdrop.previewUrl,
    totalSeriesLength = totalSeriesLength,
    averageSeriesLength = averageSeriesLength,
    isSeries = isSeries
)

fun MovieWithDetails.toDomain() = Movie(
    id = entity.id,
    name = entity.name,
    type = entity.type,
    year = entity.year,
    description = entity.description,
    status = null,
    rating = MovieRating(kp = null, imdb = null),
    length = entity.length,
    ageRating = entity.ageRating,
    logo = Image(url = entity.logoUrl, previewUrl = null),
    poster = Image(url = entity.posterUrl, previewUrl = null),
    backdrop = Image(url = entity.backdropUrl, previewUrl = null),
    videos = null,
    genres = genres,
    countries = countries,
    reviewInfo = null,
    budget = null,
    fees = null,
    similarMovies = null,
    sequelsAndPrequels = null,
    top10 = null,
    top250 = null,
    isTicketsOnSale = false,
    totalSeriesLength = entity.totalSeriesLength,
    averageSeriesLength = entity.averageSeriesLength,
    isSeries = entity.isSeries
)
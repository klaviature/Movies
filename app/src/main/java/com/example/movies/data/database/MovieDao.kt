package com.example.movies.data.database
import androidx.room.Dao
import androidx.room.Insert
import androidx.room.OnConflictStrategy
import androidx.room.Query
import androidx.room.Transaction
import com.example.movies.data.database.model.FavouriteEntity
import com.example.movies.data.database.model.MovieCountryCrossRef
import com.example.movies.data.database.model.MovieEntity
import com.example.movies.data.database.model.MovieGenreCrossRef
import com.example.movies.data.database.model.MovieWithDetails
import com.example.movies.data.database.model.WatchedEntity

@Dao
interface MovieDao {
    // ---------------- MovieEntity ----------------
    @Insert(onConflict = OnConflictStrategy.REPLACE)
    suspend fun insertMovie(movie: MovieEntity)

    @Query("DELETE FROM movies WHERE id = :movieId")
    suspend fun deleteMovie(movieId: Int)

    @Query("SELECT * FROM movies WHERE id = :movieId")
    suspend fun getMovie(movieId: Int): MovieEntity

    @Query("SELECT EXISTS(SELECT 1 FROM movies WHERE id = :movieId)")
    suspend fun isMovieExists(movieId: Int): Boolean

    @Transaction
    suspend fun insertMovieWithDetails(movie: MovieWithDetails) {
        insertMovie(movie.entity)
        insertGenres(movie.genres.toGenresCrossRef(movie.entity.id))
        insertCountries(movie.countries.toCountriesCrossRef(movie.entity.id))
    }

    @Transaction
    suspend fun removeMovieWithDetails(movieId: Int) {
        deleteGenresForMovie(movieId)
        deleteCountriesForMovie(movieId)
        deleteMovie(movieId)
    }

    // ---------------- Genres ----------------
    @Insert(onConflict = OnConflictStrategy.REPLACE)
    suspend fun insertGenres(genres: List<MovieGenreCrossRef>)

    @Query("DELETE FROM movie_genre_cross_ref WHERE movieId = :movieId")
    suspend fun deleteGenresForMovie(movieId: Int)

    @Query("SELECT * FROM movie_genre_cross_ref WHERE movieId = :movieId ORDER BY orderNumber")
    suspend fun getMovieGenres(movieId: Int): List<MovieGenreCrossRef>

    @Query("SELECT * FROM movie_genre_cross_ref WHERE movieId IN (:movieIds) ORDER BY movieId, orderNumber")
    suspend fun getGenresForMovies(movieIds: List<Int>): List<MovieGenreCrossRef>

    // ---------------- Countries ----------------
    @Insert(onConflict = OnConflictStrategy.REPLACE)
    suspend fun insertCountries(countries: List<MovieCountryCrossRef>)

    @Query("DELETE FROM movie_country_cross_ref WHERE movieId = :movieId")
    suspend fun deleteCountriesForMovie(movieId: Int)

    @Query("SELECT * FROM movie_country_cross_ref WHERE movieId = :movieId ORDER BY orderNumber")
    suspend fun getMovieCountries(movieId: Int): List<MovieCountryCrossRef>

    @Query("SELECT * FROM movie_country_cross_ref WHERE movieId IN (:movieIds) ORDER BY movieId, orderNumber")
    suspend fun getCountriesForMovies(movieIds: List<Int>): List<MovieCountryCrossRef>

    // ---------------- Favourites ----------------
    @Insert(onConflict = OnConflictStrategy.REPLACE)
    suspend fun addFavourite(favourite: FavouriteEntity)

    @Query("DELETE FROM favourites WHERE movie_id = :movieId")
    suspend fun removeFavourite(movieId: Int)

    @Query("SELECT EXISTS(SELECT 1 FROM favourites WHERE movie_id = :movieId)")
    suspend fun isFavourite(movieId: Int): Boolean

    @Query("""
        SELECT * FROM movies 
        INNER JOIN favourites ON movies.id = favourites.movie_id
    """)
    suspend fun getFavouriteMovies(): List<MovieEntity>

    @Transaction
    suspend fun addMovieToFavouritesWithDetails(movie: MovieWithDetails) {
        insertMovieWithDetails(movie)
        addFavourite(FavouriteEntity(movieId = movie.entity.id))
    }

    @Transaction
    suspend fun removeMovieFromFavourites(movieId: Int) {
        removeFavourite(movieId)
        if (!isWatched(movieId)) {
            removeMovieWithDetails(movieId)
        }
    }

    // ---------------- Watched ----------------
    @Insert(onConflict = OnConflictStrategy.REPLACE)
    suspend fun addWatched(watched: WatchedEntity)

    @Query("DELETE FROM watched WHERE movie_id = :movieId")
    suspend fun removeWatched(movieId: Int)

    @Query("SELECT EXISTS(SELECT 1 FROM watched WHERE movie_id = :movieId)")
    suspend fun isWatched(movieId: Int): Boolean

    @Query("""
        SELECT * FROM movies 
        INNER JOIN watched ON movies.id = watched.movie_id
    """)
    suspend fun getWatchedMovies(): List<MovieEntity>

    @Transaction
    suspend fun addMovieToWatchedWithDetails(movie: MovieWithDetails) {
        insertMovieWithDetails(movie)
        addWatched(WatchedEntity(movieId = movie.entity.id))
    }

    @Transaction
    suspend fun removeMovieFromWatched(movieId: Int) {
        removeWatched(movieId)
        if (!isFavourite(movieId)) {
            removeMovieWithDetails(movieId)
        }
    }
}

private fun List<String>.toCountriesCrossRef(
    movieId: Int
) = this.withIndex().map { (index, country) ->
    MovieCountryCrossRef(movieId, country, index)
}

private fun List<String>.toGenresCrossRef(
    movieId: Int
) = this.withIndex().map { (index, genre) ->
    MovieGenreCrossRef(movieId, genre, index)
}
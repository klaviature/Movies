package com.example.movies.data.database

import androidx.lifecycle.LiveData
import androidx.room.Dao
import androidx.room.Insert
import androidx.room.Query
import io.reactivex.rxjava3.core.Completable
import io.reactivex.rxjava3.core.Maybe
import io.reactivex.rxjava3.core.Single

@Dao
interface WatchedMoviesDao {

    @Insert
    fun addMovieToWatched(movie: WatchedMovieEntity): Completable

    @Query("DELETE FROM watched_movies WHERE id = :movieId")
    fun removeMovieFromWatched(movieId: Int): Completable

    @Query("SELECT COUNT(*) FROM watched_movies WHERE id = :movieId")
    fun isMovieWatched(movieId: Int): Single<Boolean>

    @Query("SELECT * FROM watched_movies")
    fun getWatchedMovies(): LiveData<List<WatchedMovieEntity>>

    @Query("SELECT * FROM watched_movies WHERE id = :movieId")
    fun getWatchedMovie(movieId: Int): Maybe<WatchedMovieEntity>
}
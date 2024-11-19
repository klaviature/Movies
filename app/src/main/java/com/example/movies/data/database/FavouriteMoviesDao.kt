package com.example.movies.data.database

import androidx.lifecycle.LiveData
import androidx.room.Dao
import androidx.room.Delete
import androidx.room.Insert
import androidx.room.OnConflictStrategy
import androidx.room.Query
import androidx.room.Update
import com.example.movies.data.model.Movie
import io.reactivex.rxjava3.core.Completable
import io.reactivex.rxjava3.core.Maybe
import io.reactivex.rxjava3.core.Single

@Dao
interface FavouriteMoviesDao {

    @Query("SELECT * FROM favourite_movies")
    fun getFavouriteMovies(): LiveData<List<FavouriteMovieEntity>>

    @Query("SELECT * FROM favourite_movies WHERE id IN (:ids)")
    fun getFavouriteMovies(ids: List<Int>): LiveData<List<FavouriteMovieEntity>>

    @Query("SELECT * FROM favourite_movies WHERE id = :id")
    fun getFavouriteMovie(id: Int): Maybe<FavouriteMovieEntity>

    @Insert(onConflict = OnConflictStrategy.REPLACE)
    fun addMovieToFavourites(movie: FavouriteMovieEntity): Completable

    @Delete
    fun removeMovieFromFavourites(movie: FavouriteMovieEntity): Completable

    //  We need this method when actual information about movie has been changed on the server
    //  This method will be called after starting MovieDetailActivity to update the entry if
    //  the movie is in favourites
    @Update(onConflict = OnConflictStrategy.REPLACE)
    fun updateMovieInFavourites(movie: FavouriteMovieEntity): Completable

    @Query("SELECT COUNT(*) FROM favourite_movies WHERE id = :id")
    fun isMovieInFavourites(id: Int): Single<Boolean>

    @Query("SELECT EXISTS(SELECT 1 FROM favourite_movies LIMIT 1)")
    fun isAnyMovieInFavourites(): Single<Boolean>
}
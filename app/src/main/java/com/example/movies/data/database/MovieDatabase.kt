package com.example.movies.data.database

import android.content.Context
import androidx.room.Database
import androidx.room.Room
import androidx.room.RoomDatabase
import com.example.movies.data.model.Movie

@Database(entities = ([FavouriteMovieEntity::class, WatchedMovieEntity::class]), version = 1, exportSchema = false)
abstract class MovieDatabase : RoomDatabase() {

    companion object {

        private val DB_NAME = "movie.db"
        private var INSTANCE: MovieDatabase? = null

        fun getInstance(context: Context): MovieDatabase {
            return INSTANCE ?: synchronized(this) {
                val instance = Room.databaseBuilder(
                    context.applicationContext,
                    MovieDatabase::class.java,
                    DB_NAME
                ).build()
                INSTANCE = instance
                instance
            }
        }
    }

    abstract fun favouriteMoviesDao(): FavouriteMoviesDao

    abstract fun watchedMoviesDao(): WatchedMoviesDao
}
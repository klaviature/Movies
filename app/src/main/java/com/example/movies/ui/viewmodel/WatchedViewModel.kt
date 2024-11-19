package com.example.movies.ui.viewmodel

import android.app.Application
import androidx.lifecycle.AndroidViewModel
import androidx.lifecycle.LiveData
import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.map
import com.example.movies.data.database.MovieDatabase
import com.example.movies.data.model.Movie
import com.example.movies.toMovie
import io.reactivex.rxjava3.disposables.CompositeDisposable

class WatchedViewModel(application: Application) : AndroidViewModel(application) {

    private val LOG_TAG = "WatchedViewModel"

    private val watchedMoviesDao =
        MovieDatabase.getInstance(getApplication<Application>().applicationContext).watchedMoviesDao()

    fun getMovies(): LiveData<List<Movie>> {
        return watchedMoviesDao.getWatchedMovies().map { it -> it.map { it.toMovie() } }
    }
}
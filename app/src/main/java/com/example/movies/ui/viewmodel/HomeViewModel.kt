package com.example.movies.ui.viewmodel

import android.app.Application
import android.util.Log
import androidx.lifecycle.AndroidViewModel
import androidx.lifecycle.LiveData
import androidx.lifecycle.MutableLiveData
import com.example.movies.data.api.kinopoisk.ApiFactory
import com.example.movies.data.database.MovieDatabase
import com.example.movies.data.model.Movie
import com.example.movies.data.model.MovieResponse
import io.reactivex.rxjava3.android.schedulers.AndroidSchedulers
import io.reactivex.rxjava3.core.Single
import io.reactivex.rxjava3.disposables.CompositeDisposable
import io.reactivex.rxjava3.schedulers.Schedulers

class HomeViewModel(application: Application) : AndroidViewModel(application) {
    private val LOG_TAG = "MainViewModel"

    private val _movies: MutableLiveData<List<Movie>> = MutableLiveData()
    val movies: LiveData<List<Movie>> = _movies

    private val _isLoading = MutableLiveData<Boolean>()
    val isLoading: LiveData<Boolean> = _isLoading

    private var currentPage = 1
    private var currentPageCount = 0
    private var currentQuery: String? = null

    private val compositeDisposable: CompositeDisposable = CompositeDisposable()

    fun loadMovies(query: String? = null) {
        if (query != currentQuery) {
            _movies.value = mutableListOf()
            currentPage = 1
        }
        currentQuery = query
        if (currentQuery == null) {
            loadMoviesData { ApiFactory.apiService.loadMovies(currentPage) }
        } else {
            loadMoviesData { ApiFactory.apiService.searchMovies(query, currentPage) }
        }
    }

    private fun loadMoviesData(apiCall: () -> Single<MovieResponse>) {
        val disposable = apiCall()
            .subscribeOn(Schedulers.newThread())
            .observeOn(AndroidSchedulers.mainThread())
            .doOnSubscribe { _isLoading.postValue(true) }
            .doAfterTerminate { _isLoading.postValue(false) }
            .map { movieResponse: MovieResponse ->
                currentPageCount = movieResponse.pageCount
                movieResponse.movies
            }
            .subscribe(
                { movies ->
                    val currentMovies = _movies.value?.toMutableList() ?: mutableListOf()
                    currentMovies.addAll(movies)
                    _movies.value = currentMovies
                },
                { throwable ->
                    Log.d(LOG_TAG, throwable.toString())
                })
        compositeDisposable.add(disposable)
    }

    fun loadNextPage() {
        if (currentPage != currentPageCount) {
            currentPage++
            loadMovies(currentQuery)
        }
    }

    override fun onCleared() {
        super.onCleared()
        compositeDisposable.clear()
    }
}
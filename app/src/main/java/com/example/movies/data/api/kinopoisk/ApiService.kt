package com.example.movies.data.api.kinopoisk

import com.example.movies.data.model.Movie
import com.example.movies.data.model.MovieResponse
import com.example.movies.data.model.ReviewResponse
import io.reactivex.rxjava3.core.Single
import retrofit2.http.GET
import retrofit2.http.Headers
import retrofit2.http.Path
import retrofit2.http.Query

interface ApiService {

    @GET("movie?selectFields=id&selectFields=name&selectFields=rating&selectFields=poster&sortField=votes.kp&sortType=-1&sortField=votes.imdb&sortType=-1&limit=40")
    @Headers("X-API-KEY:3Y0FDCK-WTCMVES-H6QS5G9-9VM6446")
    fun loadMovies(@Query("page") page: Int): Single<MovieResponse>

    @GET("movie/{id}")
    @Headers("X-API-KEY:3Y0FDCK-WTCMVES-H6QS5G9-9VM6446")
    fun loadMovie(@Path("id") id: Int): Single<Movie>

    @GET("review")
    @Headers("X-API-KEY:3Y0FDCK-WTCMVES-H6QS5G9-9VM6446")
    fun loadReviews(@Query("movieId") movieId: Int, @Query("page") page: Int): Single<ReviewResponse>

    @GET("movie/search?sortField=votes.kp&sortType=-1&limit=40")
    @Headers("X-API-KEY:3Y0FDCK-WTCMVES-H6QS5G9-9VM6446")
    fun searchMovies(@Query("query") name: String?, @Query("page") page: Int): Single<MovieResponse>
}
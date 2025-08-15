package com.example.movies.presentation.ui.moviedetailscreen

import androidx.lifecycle.ViewModel
import com.example.movies.domain.model.Country
import com.example.movies.domain.model.Genre
import com.example.movies.domain.model.Rating
import com.example.movies.domain.model.Review
import com.example.movies.domain.model.Trailer
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.flow.asStateFlow
import java.util.Collections.emptyList

class MovieDetailViewModel : ViewModel() {
    private val _state = MutableStateFlow(MovieDetailState())
    val state get() = _state.asStateFlow()



    fun loadMovieInfo() {

    }
}

data class MovieDetailState(
    val name: String = "",
    val type: String = "",
    val year: String = "",
    val description: String = "",
    val length: String = "",
    val ageRating: String = "",
    val posterUrl: String = "",
    val ratingKp: String = "",
    val ratingImdb: String = "",
    val trailers: List<Trailer> = emptyList(),
    val genres: List<Genre> = emptyList(),
    val countries: List<Country> = emptyList(),
    val reviews: List<Review> = emptyList()
)
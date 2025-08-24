package com.example.movies.presentation.ui

import androidx.compose.foundation.layout.PaddingValues
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.lazy.grid.GridCells
import androidx.compose.foundation.lazy.grid.LazyGridState
import androidx.compose.foundation.lazy.grid.LazyVerticalGrid
import androidx.compose.foundation.lazy.grid.items
import androidx.compose.foundation.lazy.grid.rememberLazyGridState
import androidx.compose.runtime.Composable
import androidx.compose.ui.Modifier
import androidx.compose.ui.tooling.preview.Preview
import androidx.compose.ui.unit.dp
import com.example.movies.domain.model.Movie
import com.example.movies.presentation.ui.theme.MoviesTheme

@Composable
fun MoviesList(
    modifier: Modifier = Modifier,
    state: LazyGridState = rememberLazyGridState(),
    contentPadding: PaddingValues = PaddingValues(0.dp),
    movies: List<Movie> = listOf(),
    onMovieClick: (Int) -> Unit = {}
) {
    LazyVerticalGrid(
        modifier = modifier,
        state = state,
        contentPadding = contentPadding,
        columns = GridCells.Adaptive(190.dp)
    ) {
        items(movies) {
            MovieCard(
                modifier = Modifier.padding(8.dp),
                ratingKp = it.rating.kp?.toString(),
                ratingImdb = it.rating.imdb?.toString(),
                posterUrl = it.poster.url,
                onClick = { onMovieClick(it.id) }
            )
        }
    }
}

@Preview
@Composable
private fun MoviesListPreview() {
    MoviesTheme {
        MoviesList(
//            movies = listOf(
//                MovieDeprecated(0),
//                MovieDeprecated(0),
//                MovieDeprecated(0),
//                MovieDeprecated(0),
//                MovieDeprecated(0),
//            )
        )
    }
}
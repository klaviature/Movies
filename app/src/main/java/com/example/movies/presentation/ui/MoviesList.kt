package com.example.movies.presentation.ui

import androidx.compose.foundation.layout.PaddingValues
import androidx.compose.foundation.layout.consumeWindowInsets
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.lazy.grid.GridCells
import androidx.compose.foundation.lazy.grid.LazyVerticalGrid
import androidx.compose.runtime.Composable
import androidx.compose.ui.Modifier
import androidx.compose.ui.tooling.preview.Preview
import androidx.compose.ui.unit.dp
import com.example.movies.domain.model.Movie
import com.example.movies.domain.model.Rating
import com.example.movies.presentation.ui.theme.MoviesTheme
import dev.chrisbanes.haze.HazeState
import dev.chrisbanes.haze.hazeSource

@Composable
fun MoviesList(
    modifier: Modifier = Modifier,
    contentPadding: PaddingValues = PaddingValues(0.dp),
    movies: List<Movie> = listOf()
) {
    LazyVerticalGrid (
        modifier = modifier,
        contentPadding = contentPadding,
        columns = GridCells.Adaptive(190.dp)
    ) {
        for (movie in movies) {
            item {
                MovieCard(
                    modifier = Modifier.padding(8.dp),
                    rating = Rating(10.0, 10.0)
                )
            }
        }
    }
}

@Preview
@Composable
private fun MoviesListPreview() {
    MoviesTheme {
        MoviesList(
            movies = listOf(
                Movie(0),
                Movie(0),
                Movie(0),
                Movie(0),
                Movie(0),
            )
        )
    }
}
package com.example.movies.presentation.ui.moviedetail

import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.PaddingValues
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.size
import androidx.compose.foundation.lazy.LazyRow
import androidx.compose.foundation.lazy.items
import androidx.compose.runtime.Composable
import androidx.compose.ui.Modifier
import androidx.compose.ui.tooling.preview.Preview
import androidx.compose.ui.unit.dp
import com.example.movies.presentation.ui.MovieCard
import com.example.movies.presentation.ui.theme.MoviesTheme

@Composable
fun LinkedMovieList(
    modifier: Modifier = Modifier,
    movies: List<LinkedMovieUi>,
    contentPadding: PaddingValues = PaddingValues(),
    onClick: (Int) -> Unit = {}
) {
    LazyRow(
        modifier = modifier,
        horizontalArrangement = Arrangement.spacedBy(12.dp),
        contentPadding = contentPadding
    ) {
        items(movies) { movie ->
            MovieCard(
                modifier = Modifier.size(150.dp, 200.dp),
                posterUrl = movie.posterUrl,
                ratingKp = String.format("%.1f", movie.ratingKp),
                ratingImdb = String.format("%.1f", movie.ratingImdb),
                onClick = { onClick(movie.id) },
                overridePainter = null
            )
        }
    }
}

@Preview
@Composable
private fun LinkedMovieListPreview() {
    MoviesTheme {
        LinkedMovieList(
            movies = List(5) { index ->
                LinkedMovieUi(
                    id = index,
                    name = "Movie $index",
                    posterUrl = "",
                    ratingKp = (index + 3).toDouble(),
                    ratingImdb = (index + 3).toDouble()
                )
            }
        )
    }
}
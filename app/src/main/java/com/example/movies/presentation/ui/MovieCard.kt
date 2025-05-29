package com.example.movies.presentation.ui

import androidx.compose.foundation.background
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.defaultMinSize
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.shape.CircleShape
import androidx.compose.material3.Card
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.ui.Modifier
import androidx.compose.ui.draw.clip
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.tooling.preview.Preview
import androidx.compose.ui.unit.dp
import coil3.compose.AsyncImage
import com.example.movies.domain.model.Movie
import com.example.movies.domain.model.Rating
import com.example.movies.presentation.ui.theme.MoviesTheme
import java.util.Locale

@Composable
fun MovieCard(
    modifier: Modifier = Modifier,
    rating: Rating? = null,
    movie: Movie? = null,
    isPreview: Boolean = false
) {
    Card(
        modifier = modifier
            .height(280.dp)
    ) {
        AsyncImage(
            model = "https://example.com/image.jpg",
            contentDescription = null
        )
        rating?.let {
            RatingCircle(
                modifier = Modifier.padding(start = 8.dp, top = 8.dp),
                rating = it.kp
            )
        }
    }
}

@Composable
@Preview(showBackground = true)
private fun MovieCardPreview(modifier: Modifier = Modifier) {
    MoviesTheme {
        MovieCard(
            rating = Rating(5.2, 5.2),
            isPreview = true
        )
    }
}

@Composable
fun RatingCircle(
    modifier: Modifier = Modifier,
    rating: Double = 0.0
) {
    Box(
        modifier = modifier
            .clip(shape = CircleShape)
            .background(
                color = when (rating) {
                    in 0.0..4.9 -> Color.Red
                    in 5.0..7.9 -> Color.Yellow
                    in 8.0..10.0 -> Color.Green
                    else -> Color.Transparent
                }
            )
            .padding(14.dp)
    ) {
        Text(String.format(Locale("US"), "%.1f", rating))
    }
}

@Preview
@Composable
private fun RatingPreview() {
    MoviesTheme {
        RatingCircle(rating = 1.3)
    }
}
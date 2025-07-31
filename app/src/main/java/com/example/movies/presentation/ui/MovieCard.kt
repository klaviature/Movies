package com.example.movies.presentation.ui

import androidx.compose.foundation.Image
import androidx.compose.foundation.background
import androidx.compose.foundation.clickable
import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.Spacer
import androidx.compose.foundation.layout.defaultMinSize
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.size
import androidx.compose.foundation.shape.CircleShape
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.material3.Card
import androidx.compose.material3.CardDefaults
import androidx.compose.material3.Icon
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.draw.clip
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.graphics.ColorFilter
import androidx.compose.ui.graphics.painter.Painter
import androidx.compose.ui.graphics.vector.ImageVector
import androidx.compose.ui.layout.ContentScale
import androidx.compose.ui.res.painterResource
import androidx.compose.ui.res.vectorResource
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.text.style.TextAlign
import androidx.compose.ui.tooling.preview.Preview
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import coil3.compose.AsyncImage
import com.example.movies.R
import com.example.movies.domain.model.Movie
import com.example.movies.domain.model.Rating
import com.example.movies.presentation.ui.theme.MoviesTheme
import java.util.Locale

@Composable
fun MovieCard(
    modifier: Modifier = Modifier,
    posterUrl: String? = null,
    rating: Rating? = null,
    onClick: () -> Unit = {},
    overridePainter: Painter? = null
) {
    Card(
        modifier = modifier
            .height(280.dp)
            .fillMaxWidth()
            .clickable(onClick = onClick)
    ) {
        Box(
            modifier = Modifier.fillMaxSize()
        ) {
            if (overridePainter != null) {
                Image(
                    modifier = Modifier.fillMaxSize(),
                    painter = overridePainter,
                    contentDescription = null,
                    contentScale = ContentScale.Fit
                )
            } else {
                posterUrl?.let {
                    AsyncImage(
                        model = it,
                        contentDescription = null
                    )
                }
            }

            rating?.let {
                Column(
                    modifier = Modifier
                        .align(Alignment.BottomEnd)
                        .padding(8.dp),
                    verticalArrangement = Arrangement.spacedBy(4.dp)
                ) {
                    KpRatingCard(rating = it.kp)
                    ImdbRatingCard(rating = it.imdb)
                }
            }
        }
    }
}

@Composable
@Preview(showBackground = true)
private fun MovieCardPreview(modifier: Modifier = Modifier) {
    MoviesTheme {
        MovieCard(
            rating = Rating(5.2, 5.2),
            overridePainter = painterResource(R.drawable.intouchables)
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

@Composable
fun KpRatingCard(
    modifier: Modifier = Modifier,
    rating: Double
) {
    Box(
        modifier = modifier
            .size(70.dp, 25.dp)
            .clip(CardDefaults.shape)
            .background(Color.Black)
            .padding(vertical = 4.dp, horizontal = 8.dp)
    ) {
        Row(
            verticalAlignment = Alignment.CenterVertically,
            horizontalArrangement = Arrangement.spacedBy(4.dp)
        ) {
            Image(
                imageVector = ImageVector.vectorResource(R.drawable.kinopoisk_colored_logo),
                contentDescription = null
            )
            Text(
                modifier = Modifier.weight(1f),
                text = rating.toString(),
                fontWeight = FontWeight.Bold,
                color = Color.White,
                textAlign = TextAlign.End,
                fontSize = 12.sp,
                lineHeight = 12.sp
            )
        }
    }
}

@Preview
@Composable
private fun KpRatingCardPreview() {
    MoviesTheme {
        KpRatingCard(rating = 10.0)
    }
}

@Composable
fun ImdbRatingCard(
    modifier: Modifier = Modifier,
    rating: Double
) {
    Box(
        modifier = modifier
            .size(70.dp, 25.dp)
            .clip(CardDefaults.shape)
            .background(Color.Black)
            .padding(vertical = 4.dp, horizontal = 8.dp)
    ) {
        Row(
            verticalAlignment = Alignment.CenterVertically,
            horizontalArrangement = Arrangement.spacedBy(4.dp)
        ) {
            Image(
                modifier = Modifier
                    .clip(RoundedCornerShape(4.dp))
                    .background(Color.Yellow)
                    .padding(horizontal = 2.dp),
                imageVector = ImageVector.vectorResource(R.drawable.imdb_logo),
                contentDescription = null
            )
            Text(
                modifier = Modifier.weight(1f),
                text = rating.toString(),
                fontWeight = FontWeight.Bold,
                color = Color.White,
                textAlign = TextAlign.End,
                fontSize = 12.sp,
                lineHeight = 12.sp
            )
        }
    }
}

@Preview
@Composable
private fun ImdbRatingCardPreview() {
    MoviesTheme {
        ImdbRatingCard(rating = 10.0)
    }
}
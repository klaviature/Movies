package com.example.movies.presentation.ui

import androidx.compose.foundation.Image
import androidx.compose.foundation.background
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.size
import androidx.compose.foundation.shape.CircleShape
import androidx.compose.material.icons.Icons
import androidx.compose.material3.Card
import androidx.compose.material3.Icon
import androidx.compose.runtime.Composable
import androidx.compose.ui.Modifier
import androidx.compose.ui.draw.alpha
import androidx.compose.ui.draw.clip
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.graphics.painter.Painter
import androidx.compose.ui.graphics.vector.ImageVector
import androidx.compose.ui.layout.ContentScale
import androidx.compose.ui.res.painterResource
import androidx.compose.ui.res.vectorResource
import androidx.compose.ui.tooling.preview.Preview
import androidx.compose.ui.unit.dp
import coil3.compose.AsyncImage
import coil3.compose.ImagePainter
import com.example.movies.R
import com.example.movies.presentation.ui.theme.MoviesTheme

@Composable
fun TrailerCard(
    modifier: Modifier = Modifier,
    trailerUrl: String = "",
    overridePainter: Painter? = null
) {
    Card(
        modifier = modifier
            .size(width = 240.dp, height = 130.dp),
        onClick = {} // TODO Сделать переход по ссылке
    ) {
        Box(
            modifier = Modifier.fillMaxSize()
        ) {
            if (overridePainter != null) {
                Image(
                    painter = overridePainter,
                    contentDescription = null,
                    contentScale = ContentScale.Crop
                )
            } else {
                AsyncImage(
                    model = trailerUrl,
                    contentDescription = null,
                    contentScale = ContentScale.Crop
                )
            }
            Box(
                modifier = Modifier
                    .padding(4.dp)
                    .clip(CircleShape)
                    .size(18.dp)
                    .alpha(0.7f)
                    .background(Color.Black)
                    .padding(2.dp)
            ) {
                Icon(
                    imageVector = ImageVector.vectorResource(R.drawable.globe_icon),
                    contentDescription = null,
                    tint = Color.White
                )
            }
        }
    }
}

@Preview
@Composable
private fun TrailerCardPreview() {
    MoviesTheme {
        TrailerCard(overridePainter = painterResource(R.drawable.thumbnail))
    }
}
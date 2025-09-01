package com.example.movies.presentation

import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.FlowRow
import androidx.compose.foundation.layout.Spacer
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.width
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.ui.Modifier
import androidx.compose.ui.tooling.preview.Preview
import androidx.compose.ui.unit.dp
import com.example.movies.presentation.ui.theme.MoviesTheme

@Composable
fun TestGrid(modifier: Modifier = Modifier) {
    val year = "2025"
    val countries = listOf("USA", "Canada", "China")
    val length = "123 min"
    val genres = listOf("Comedy", "Action", "Thriller")
    val ageRating = "18+"
    Column(
        modifier = Modifier.width(150.dp)
    ) {
        FlowRow(
            modifier = Modifier.fillMaxWidth()
        ) {
            Text(year)
            Spacer(modifier = Modifier.width(8.dp))
            countries.forEachIndexed { index, country ->
                Text(country + if (index < countries.size - 1) ", " else "")
            }
            Spacer(modifier = Modifier.width(6.dp))
            Text(length)
        }
        FlowRow(
            modifier = Modifier.fillMaxWidth()
        ) {
            genres.forEachIndexed { index, country ->
                Text(country + if (index < countries.size - 1) ", " else "")
            }
            Spacer(modifier = Modifier.width(6.dp))
            Text(ageRating)
        }
    }
}

@Preview
@Composable
private fun TestGridPreview() {
    MoviesTheme {
        TestGrid()
    }
}
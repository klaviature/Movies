package com.example.movies.presentation

import android.os.Bundle
import androidx.activity.ComponentActivity
import androidx.activity.compose.setContent
import androidx.activity.enableEdgeToEdge
import androidx.compose.foundation.layout.PaddingValues
import androidx.compose.foundation.layout.consumeWindowInsets
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.lazy.LazyColumn
import androidx.compose.foundation.lazy.grid.GridCells
import androidx.compose.foundation.lazy.grid.LazyVerticalGrid
import androidx.compose.material3.ExperimentalMaterial3Api
import androidx.compose.material3.Scaffold
import androidx.compose.material3.SearchBar
import androidx.compose.material3.SearchBarDefaults
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.ui.Modifier
import androidx.compose.ui.tooling.preview.Devices
import androidx.compose.ui.tooling.preview.Preview
import androidx.compose.ui.unit.dp
import com.example.movies.domain.model.Movie
import com.example.movies.domain.model.Rating
import com.example.movies.presentation.ui.MovieCard
import com.example.movies.presentation.ui.MoviesList
import com.example.movies.presentation.ui.theme.MoviesTheme
import com.example.movies.presentation.ui.MyNavigationBar
import dev.chrisbanes.haze.HazeProgressive
import dev.chrisbanes.haze.HazeStyle
import dev.chrisbanes.haze.hazeEffect
import dev.chrisbanes.haze.hazeSource
import dev.chrisbanes.haze.materials.HazeMaterials
import dev.chrisbanes.haze.rememberHazeState

class MainActivity2 : ComponentActivity() {
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        enableEdgeToEdge()
        setContent {
            MoviesTheme {
                MainScreen()
            }
        }
    }
}

@OptIn(ExperimentalMaterial3Api::class)
@Composable
fun MainScreen() {
    val hazeState = rememberHazeState(blurEnabled = true)
    Scaffold(
        modifier = Modifier.fillMaxSize(),
        bottomBar = { MyNavigationBar(hazeState = hazeState) },
        topBar = {
            SearchBar(
                inputField = {
                    SearchBarDefaults.InputField(
                        query = "",
                        onQueryChange = {},
                        onSearch = {},
                        expanded = false,
                        onExpandedChange = {}
                    )
                },
                expanded = false,
                onExpandedChange = {},
                modifier = Modifier
                    .hazeEffect(state = hazeState, style = HazeMaterials.ultraThin()) {
                        progressive =
                            HazeProgressive.verticalGradient(startIntensity = 1f, endIntensity = 0f)
                    }
                    .padding(horizontal = 16.dp)
                    .fillMaxWidth()
            ) {

            }
        }
    ) { innerPadding ->
        MoviesList(
            modifier = Modifier
                .hazeSource(hazeState)
                .consumeWindowInsets(innerPadding),
            contentPadding = innerPadding,
            movies = listOf(
                Movie(0),
                Movie(0),
                Movie(0),
                Movie(0),
                Movie(0),
                Movie(0),
                Movie(0),
                Movie(0),
            )
        )
    }
}

@Preview(showBackground = true, device = Devices.PIXEL_6A, showSystemUi = true)
@Composable
fun MainPreview() {
    MoviesTheme {
        MainScreen()
    }
}
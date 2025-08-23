package com.example.movies.presentation

import android.os.Bundle
import androidx.activity.ComponentActivity
import androidx.activity.compose.setContent
import androidx.activity.enableEdgeToEdge
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.material3.ExperimentalMaterial3Api
import androidx.compose.material3.Scaffold
import androidx.compose.runtime.Composable
import androidx.compose.ui.Modifier
import androidx.compose.ui.tooling.preview.Devices
import androidx.compose.ui.tooling.preview.Preview
import com.example.movies.presentation.ui.MyNavigationBar
import com.example.movies.presentation.ui.home.HomeScreen
import com.example.movies.presentation.ui.moviedetailscreen.MovieDetailScreen
import com.example.movies.presentation.ui.theme.MoviesTheme
import dev.chrisbanes.haze.rememberHazeState

class MainActivity2 : ComponentActivity() {
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        enableEdgeToEdge()
        setContent {
            MoviesTheme {
//                MainScreen()
                MovieDetailScreen()
            }
        }
    }
}

@OptIn(ExperimentalMaterial3Api::class)
@Composable
fun MainScreen() {
    val hazeState = rememberHazeState(blurEnabled = true)
    Scaffold(
        modifier = Modifier
            .fillMaxSize(),
        bottomBar = { MyNavigationBar(hazeState = hazeState) }
    ) { innerPadding ->
        HomeScreen(
            contentPadding = innerPadding,
            hazeState = hazeState
        )
    }
}

@Composable
fun MainScreenContent(modifier: Modifier = Modifier) {

}

@Preview(showBackground = true, device = Devices.PIXEL_6A, showSystemUi = true)
@Composable
fun MainPreview() {
    MoviesTheme {
        MainScreen()
    }
}
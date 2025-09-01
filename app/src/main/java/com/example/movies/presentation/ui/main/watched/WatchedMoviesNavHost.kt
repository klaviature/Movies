package com.example.movies.presentation.ui.main.watched

import androidx.compose.foundation.layout.PaddingValues
import androidx.compose.material3.SnackbarHostState
import androidx.compose.runtime.Composable
import androidx.compose.runtime.remember
import androidx.compose.ui.Modifier
import androidx.compose.ui.unit.dp
import androidx.navigation.compose.NavHost
import androidx.navigation.compose.composable
import androidx.navigation.compose.rememberNavController
import androidx.navigation.toRoute
import com.example.movies.presentation.ui.moviedetail.MovieDetailScreen
import kotlinx.serialization.Serializable

@Serializable
sealed interface WatchedNavGraph {
    @Serializable
    data object Watched : WatchedNavGraph
    @Serializable
    data class MovieDetails(val movieId: Int) : WatchedNavGraph
}

@Composable
fun WatchedMoviesNavHost(
    modifier: Modifier = Modifier,
    contentPadding: PaddingValues = PaddingValues(0.dp),
    snackbarHostState: SnackbarHostState = remember { SnackbarHostState() }
) {
    val navController = rememberNavController()
    val startDestination = WatchedNavGraph.Watched

    NavHost(
        modifier = modifier,
        navController = navController,
        startDestination = startDestination
    ) {
        composable<WatchedNavGraph.Watched> {
            WatchedMoviesScreen(
                navController = navController,
                contentPadding = contentPadding,
                snackbarHostState = snackbarHostState
            )
        }
        composable<WatchedNavGraph.MovieDetails> {
            MovieDetailScreen(
                navController = navController,
                contentPadding = contentPadding,
                snackbarHostState = snackbarHostState
            )
        }
    }
}
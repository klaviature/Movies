package com.example.movies.presentation.ui.main.favourites

import androidx.compose.foundation.layout.PaddingValues
import androidx.compose.material3.SnackbarHostState
import androidx.compose.runtime.Composable
import androidx.compose.runtime.remember
import androidx.compose.ui.Modifier
import androidx.compose.ui.unit.dp
import androidx.navigation.compose.NavHost
import androidx.navigation.compose.composable
import androidx.navigation.compose.rememberNavController
import com.example.movies.presentation.ui.moviedetail.MovieDetailScreen
import kotlinx.serialization.Serializable

@Serializable
sealed interface FavouritesNavGraph {
    @Serializable
    data object Favourites : FavouritesNavGraph
    @Serializable
    data class MovieDetails(val movieId: Int) : FavouritesNavGraph
}

@Composable
fun FavouriteMoviesNavHost(
    modifier: Modifier = Modifier,
    contentPadding: PaddingValues = PaddingValues(0.dp),
    snackbarHostState: SnackbarHostState = remember { SnackbarHostState() }
) {
    val navController = rememberNavController()
    val startDestination = FavouritesNavGraph.Favourites

    NavHost(
        modifier = modifier,
        navController = navController,
        startDestination = startDestination
    ) {
        composable<FavouritesNavGraph.Favourites> {
            FavouriteMoviesScreen(
                navController = navController,
                contentPadding = contentPadding,
                snackbarHostState = snackbarHostState
            )
        }
        composable<FavouritesNavGraph.MovieDetails> {
            MovieDetailScreen(
                navController = navController,
                contentPadding = contentPadding,
                snackbarHostState = snackbarHostState
            )
        }
    }
}
package com.example.movies.presentation.navigation

import androidx.compose.runtime.Composable
import androidx.navigation.NavHostController
import androidx.navigation.compose.NavHost
import androidx.navigation.compose.composable
import androidx.navigation.compose.rememberNavController
import androidx.navigation.toRoute
import com.example.movies.presentation.ui.main.MainScreen
import com.example.movies.presentation.ui.moviedetail.MovieDetailScreen
import kotlinx.serialization.Serializable

@Serializable
sealed class RootNavGraph {
    @Serializable
    data object Main : RootNavGraph()

    @Serializable
    data class MovieDetail(val movieId: Int) : RootNavGraph()
}

@Composable
fun AppNavHost(
    navController: NavHostController = rememberNavController()
) {
    NavHost(
        navController = navController,
        startDestination = RootNavGraph.Main
    ) {
        composable<RootNavGraph.Main> {
            MainScreen(
                onMovieClick = { movieId ->
                    navController.navigate(RootNavGraph.MovieDetail(movieId)) {
                        launchSingleTop = true
                        restoreState = true
                    }
                }
            )
        }
    }
}
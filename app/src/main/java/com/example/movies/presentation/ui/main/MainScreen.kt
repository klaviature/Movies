package com.example.movies.presentation.ui.main

import android.util.Log
import androidx.annotation.DrawableRes
import androidx.annotation.StringRes
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.filled.Favorite
import androidx.compose.material.icons.filled.Home
import androidx.compose.material.icons.filled.Settings
import androidx.compose.material.icons.outlined.FavoriteBorder
import androidx.compose.material.icons.outlined.Home
import androidx.compose.material.icons.outlined.Settings
import androidx.compose.material3.ExperimentalMaterial3Api
import androidx.compose.material3.Icon
import androidx.compose.material3.NavigationBar
import androidx.compose.material3.NavigationBarDefaults
import androidx.compose.material3.NavigationBarItem
import androidx.compose.material3.Scaffold
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.runtime.LaunchedEffect
import androidx.compose.runtime.getValue
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.vector.ImageVector
import androidx.compose.ui.res.stringResource
import androidx.compose.ui.res.vectorResource
import androidx.compose.ui.tooling.preview.Devices
import androidx.compose.ui.tooling.preview.Preview
import androidx.navigation.NavDestination.Companion.hasRoute
import androidx.navigation.NavDestination.Companion.hierarchy
import androidx.navigation.NavGraph.Companion.findStartDestination
import androidx.navigation.NavHostController
import androidx.navigation.compose.NavHost
import androidx.navigation.compose.composable
import androidx.navigation.compose.currentBackStackEntryAsState
import androidx.navigation.compose.rememberNavController
import com.example.movies.R
import com.example.movies.presentation.ui.main.home.HomeScreen
import com.example.movies.presentation.ui.theme.MoviesTheme
import dev.chrisbanes.haze.hazeEffect
import dev.chrisbanes.haze.materials.ExperimentalHazeMaterialsApi
import dev.chrisbanes.haze.materials.HazeMaterials
import dev.chrisbanes.haze.rememberHazeState
import kotlinx.serialization.Serializable

sealed class AppIcon {
    data class Vector(
        val imageVector: ImageVector
    ) : AppIcon()
    data class Resource(
        @DrawableRes val resId: Int
    ) : AppIcon()
}
private enum class NavigationBarDestination(
    val route: MainNavGraph,
    @StringRes val labelRes: Int,
    val unselectedIcon: AppIcon,
    val selectedIcon: AppIcon,
    val contentDescription: String?
) {
    HOME(
        route = MainNavGraph.Home,
        labelRes = R.string.home_label,
        unselectedIcon = AppIcon.Vector(Icons.Outlined.Home),
        selectedIcon = AppIcon.Vector(Icons.Filled.Home),
        contentDescription = null
    ),
    WATCHED(
        route = MainNavGraph.Watched,
        labelRes = R.string.watched_label,
        unselectedIcon = AppIcon.Resource(R.drawable.eye_outlined),
        selectedIcon = AppIcon.Resource(R.drawable.eye_filled),
        contentDescription = null
    ),
    FAVOURITES(
        route = MainNavGraph.Favourites,
        labelRes = R.string.favourites_label,
        unselectedIcon = AppIcon.Vector(Icons.Outlined.FavoriteBorder),
        selectedIcon = AppIcon.Vector(Icons.Filled.Favorite),
        contentDescription = null
    ),
    SETTINGS(
        route = MainNavGraph.Settings,
        labelRes = R.string.settings_label,
        unselectedIcon = AppIcon.Vector(Icons.Outlined.Settings),
        selectedIcon = AppIcon.Vector(Icons.Filled.Settings),
        contentDescription = null
    ),
}

@Serializable
sealed class MainNavGraph {
    @Serializable
    data object Home : MainNavGraph()

    @Serializable
    data object Watched : MainNavGraph()

    @Serializable
    data object Favourites : MainNavGraph()

    @Serializable
    data object Settings : MainNavGraph()
}

@OptIn(ExperimentalMaterial3Api::class, ExperimentalHazeMaterialsApi::class)
@Composable
fun MainScreen(
    navController: NavHostController = rememberNavController(),
    onMovieClick: (Int) -> Unit = {}
) {
    LaunchedEffect(Unit) {
        Log.d("MainScreen", "Screen was created")
    }
    val startDestination = NavigationBarDestination.HOME

    val backStackEntry by navController.currentBackStackEntryAsState()
    val currentDestination = backStackEntry?.destination

//    val homeViewModel: HomeScreenViewModel = viewModel()
    // val watchedViewModel: WatchedScreenViewModel = viewModel()
    // val favouritesViewModel: FavouritesScreenViewModel = viewModel()
    // val settingViewModel: SettingsScreenViewModel = viewModel()

    val hazeState = rememberHazeState(blurEnabled = true)
    Scaffold(
        modifier = Modifier
            .fillMaxSize(),
        bottomBar = {
            NavigationBar(
                containerColor = NavigationBarDefaults.containerColor.copy(alpha = 0.5f),
                modifier = Modifier
                    .hazeEffect(state = hazeState, style = HazeMaterials.ultraThin())
            ) {
                NavigationBarDestination.entries.forEachIndexed { index, destination ->
                    val selected = currentDestination?.hierarchy?.any {
                        it.hasRoute(destination.route::class)
                    } == true
                    NavigationBarItem(
                        selected = selected,
                        onClick = {
                            navController.navigate(destination.route) {
                                popUpTo(navController.graph.findStartDestination().id)
                                launchSingleTop = true
                            }
                        },
                        icon = {
                            Icon(
                                imageVector = if (selected) {
                                    when (destination.selectedIcon) {
                                        is AppIcon.Resource ->
                                            ImageVector.vectorResource(destination.selectedIcon.resId)
                                        is AppIcon.Vector -> destination.selectedIcon.imageVector
                                    }
                                } else {
                                    when (destination.unselectedIcon) {
                                        is AppIcon.Resource ->
                                            ImageVector.vectorResource(destination.unselectedIcon.resId)
                                        is AppIcon.Vector -> destination.unselectedIcon.imageVector
                                    }
                                },
                                contentDescription = destination.contentDescription
                            )
                        },
                        label = { Text(text = stringResource(destination.labelRes)) }
                    )
                }
            }
        }
    ) { innerPadding ->
        NavHost(
            navController = navController,
            startDestination = startDestination.route
        ) {
            composable<MainNavGraph.Home> {
                HomeScreen(
                    contentPadding = innerPadding,
                    hazeState = hazeState,
                    onMovieClick = onMovieClick
                )
            }
            composable<MainNavGraph.Watched> {  }
            composable<MainNavGraph.Favourites> {  }
            composable<MainNavGraph.Settings> {  }
        }
    }
}

@Preview(showBackground = true, device = Devices.PIXEL_6A, showSystemUi = true)
@Composable
fun MainPreview() {
    MoviesTheme {
        MainScreen()
    }
}
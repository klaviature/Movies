package com.example.movies.presentation.ui.main.favourites

import androidx.compose.foundation.layout.PaddingValues
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.padding
import androidx.compose.material3.ExperimentalMaterial3Api
import androidx.compose.material3.MediumTopAppBar
import androidx.compose.material3.Scaffold
import androidx.compose.material3.SnackbarHostState
import androidx.compose.material3.Text
import androidx.compose.material3.TopAppBarDefaults
import androidx.compose.material3.pulltorefresh.PullToRefreshBox
import androidx.compose.material3.pulltorefresh.PullToRefreshDefaults
import androidx.compose.material3.pulltorefresh.rememberPullToRefreshState
import androidx.compose.material3.rememberTopAppBarState
import androidx.compose.runtime.Composable
import androidx.compose.runtime.LaunchedEffect
import androidx.compose.runtime.remember
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.input.nestedscroll.nestedScroll
import androidx.compose.ui.tooling.preview.Preview
import androidx.compose.ui.unit.dp
import androidx.hilt.navigation.compose.hiltViewModel
import androidx.lifecycle.compose.collectAsStateWithLifecycle
import androidx.navigation.NavHostController
import com.example.movies.domain.model.Movie
import com.example.movies.presentation.ui.MoviesList
import com.example.movies.presentation.ui.theme.MoviesTheme

@Composable
fun FavouriteMoviesScreen(
    navController: NavHostController,
    contentPadding: PaddingValues = PaddingValues(0.dp),
    viewModel: FavouritesScreenViewModel = hiltViewModel(),
    snackbarHostState: SnackbarHostState = remember { SnackbarHostState() }
) {
    val state = viewModel.state.collectAsStateWithLifecycle()

    LaunchedEffect(state.value.error) {
        state.value.error?.let {
            when(it) {
                FavouritesScreenState.Error.Critical -> {
                    snackbarHostState.showSnackbar("Ошибка чтения данных")
                }
            }
            viewModel.errorShown()
        }
    }
    LaunchedEffect(state.value.message) {
        state.value.message?.let {
            viewModel.messageShown()
        }
    }

    FavouriteMoviesScreenContent(
        contentPadding = contentPadding,
        isLoading = state.value.isLoading,
        movies = state.value.movies,
        onMovieClick = { movieId ->
            navController.navigate(FavouritesNavGraph.MovieDetails(movieId))
        }
    )
}

@OptIn(ExperimentalMaterial3Api::class)
@Composable
fun FavouriteMoviesScreenContent(
    modifier: Modifier = Modifier,
    contentPadding: PaddingValues = PaddingValues(0.dp),
    isLoading: Boolean,
    movies: List<Movie>,
    onMovieClick: (Int) -> Unit
) {
    val scrollBehavior = TopAppBarDefaults.enterAlwaysScrollBehavior(rememberTopAppBarState())
    val pullToRefreshState = rememberPullToRefreshState()

    Scaffold(
        modifier = modifier
            .nestedScroll(scrollBehavior.nestedScrollConnection)
            .fillMaxSize(),
        topBar = {
            MediumTopAppBar(
                title = {
                    Text(text = "Избранные фильмы")
                },
                scrollBehavior = scrollBehavior
            )
        }
    ) { innerPadding ->
        PullToRefreshBox(
            modifier = Modifier.fillMaxSize(),
            isRefreshing = isLoading,
            onRefresh = {},
            indicator = {
                PullToRefreshDefaults.Indicator(
                    state = pullToRefreshState,
                    isRefreshing = isLoading,
                    modifier = Modifier
                        .align(Alignment.TopCenter)
                        .padding(innerPadding)
                )
            }
        ) {
            MoviesList(
                modifier = Modifier.fillMaxSize(),
                contentPadding = PaddingValues(
                    top = innerPadding.calculateTopPadding(),
                    bottom = contentPadding.calculateBottomPadding()
                ),
                onMovieClick = onMovieClick,
                movies = movies
            )
        }
    }
}

@Preview
@Composable
private fun FavouriteMoviesScreenPreview() {
    MoviesTheme {
        FavouriteMoviesScreenContent(
            isLoading = true,
            movies = emptyList(),
            onMovieClick = {}
        )
    }
}
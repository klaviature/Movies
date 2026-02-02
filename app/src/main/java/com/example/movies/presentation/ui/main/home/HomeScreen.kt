package com.example.movies.presentation.ui.main.home

import androidx.compose.animation.core.animateDpAsState
import androidx.compose.foundation.layout.PaddingValues
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.lazy.grid.rememberLazyGridState
import androidx.compose.material3.ExperimentalMaterial3Api
import androidx.compose.material3.Scaffold
import androidx.compose.material3.SearchBar
import androidx.compose.material3.SearchBarDefaults
import androidx.compose.material3.SnackbarHostState
import androidx.compose.material3.pulltorefresh.PullToRefreshBox
import androidx.compose.material3.pulltorefresh.PullToRefreshDefaults
import androidx.compose.material3.pulltorefresh.rememberPullToRefreshState
import androidx.compose.runtime.Composable
import androidx.compose.runtime.LaunchedEffect
import androidx.compose.runtime.derivedStateOf
import androidx.compose.runtime.getValue
import androidx.compose.runtime.remember
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.platform.LocalDensity
import androidx.compose.ui.tooling.preview.Preview
import androidx.compose.ui.unit.Dp
import androidx.compose.ui.unit.dp
import androidx.hilt.navigation.compose.hiltViewModel
import androidx.lifecycle.compose.collectAsStateWithLifecycle
import androidx.navigation.NavHostController
import androidx.navigation.compose.NavHost
import androidx.navigation.compose.composable
import androidx.navigation.compose.rememberNavController
import com.example.movies.data.api.model.MovieType
import com.example.movies.domain.model.Image
import com.example.movies.domain.model.Movie
import com.example.movies.domain.model.MovieRating
import com.example.movies.presentation.ui.MoviesList
import com.example.movies.presentation.ui.moviedetail.MovieDetailScreen
import com.example.movies.presentation.ui.theme.MoviesTheme
import kotlinx.serialization.Serializable
import kotlin.math.min

@Serializable
sealed interface HomeNavGraph {
    @Serializable
    data object Home : HomeNavGraph
    @Serializable
    data class MovieDetails(val movieId: Int) : HomeNavGraph
}

@Composable
fun HomeScreenNav(
    modifier: Modifier = Modifier,
    contentPadding: PaddingValues = PaddingValues(0.dp),
    snackbarHostState: SnackbarHostState = remember { SnackbarHostState() }
) {
    val navController = rememberNavController()
    val startDestination = HomeNavGraph.Home

    NavHost(
        navController = navController,
        startDestination = startDestination
    ) {
        composable<HomeNavGraph.Home> {
            HomeScreen(
                navController = navController,
                contentPadding = contentPadding,
                snackbarHostState = snackbarHostState
            )
        }
        composable<HomeNavGraph.MovieDetails> {
            MovieDetailScreen(
                navController = navController,
                contentPadding = contentPadding,
                snackbarHostState = snackbarHostState
            )
        }
    }
}

@Composable
fun HomeScreen(
    navController: NavHostController,
    contentPadding: PaddingValues = PaddingValues(0.dp),
    viewModel: HomeScreenViewModel = hiltViewModel(),
    snackbarHostState: SnackbarHostState = remember { SnackbarHostState() }
) {
    val state = viewModel.state.collectAsStateWithLifecycle().value
    LaunchedEffect(state.error) {
        state.error?.let {
            when (state.error) {
                HomeScreenState.Error.Forbidden -> {
                    snackbarHostState.showSnackbar("Ошибка: нет доступа к запрашиваемому ресурсу")
                }
                HomeScreenState.Error.NotFound -> {
                    snackbarHostState.showSnackbar("Ошибка: ресурс не найден")
                }
                HomeScreenState.Error.Unauthorized -> {
                    snackbarHostState.showSnackbar("Ошибка авторизации")
                }
                is HomeScreenState.Error.Unknown -> {
                    snackbarHostState.showSnackbar("Неизвестная ошибка: ${state.error.message}")
                }
            }
            viewModel.errorShown()
        }
    }

    HomeScreenContent(
        movies = state.movies,
        isRefreshing = state.isRefreshing,
        onRefresh = {
            viewModel.load()
        },
        modifier = Modifier
            .fillMaxSize(),
        contentPadding = contentPadding,
        onMovieClick = {
            navController.navigate(HomeNavGraph.MovieDetails(it))
        }
    )
}

@OptIn(ExperimentalMaterial3Api::class)
@Composable
fun HomeScreenContent(
    modifier: Modifier = Modifier,
    movies: List<Movie>,
    isRefreshing: Boolean = false,
    onRefresh: () -> Unit = {},
    contentPadding: PaddingValues = PaddingValues(0.dp),
    onMovieClick: (Int) -> Unit = {}
) {
    val listState = rememberLazyGridState()

    val searchBarHeight = with(LocalDensity.current) {
        SearchBarDefaults.InputFieldHeight.toPx()
    }
    val firstItemIndex by remember {
        derivedStateOf { listState.firstVisibleItemIndex }
    }
    val firstItemOffset by remember {
        derivedStateOf { listState.firstVisibleItemScrollOffset }
    }

    val scrollProgress = remember(firstItemIndex, firstItemOffset) {
        when {
            firstItemIndex > 0 -> 1f
            else -> min(firstItemOffset / searchBarHeight, 1f)
        }
    }
    val searchBarElevation by animateDpAsState(
        targetValue = Dp(8f * scrollProgress)
    )

    val pullToRefreshState = rememberPullToRefreshState()

    Scaffold(
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
                    .padding(horizontal = 16.dp)
                    .fillMaxWidth(),
                shadowElevation = searchBarElevation,
//                windowInsets = WindowInsets(top = contentPadding.calculateTopPadding())
            ) {}
        }
    ) { innerPadding ->
        PullToRefreshBox(
//            modifier = Modifier.padding(innerPadding),
            state = pullToRefreshState,
            isRefreshing = isRefreshing,
            onRefresh = onRefresh,
            indicator = {
                PullToRefreshDefaults.Indicator(
                    state = pullToRefreshState,
                    isRefreshing = isRefreshing,
                    modifier = Modifier
                        .align(Alignment.TopCenter)
                        .padding(innerPadding)
                )
            }
        ) {
            MoviesList(
                modifier = modifier,
                movies = movies,
                contentPadding = PaddingValues(
                    top = innerPadding.calculateTopPadding(),
                    bottom = contentPadding.calculateBottomPadding()
                ),
                state = listState,
                onMovieClick = onMovieClick
            )
        }
    }
}

@Preview(showSystemUi = true)
@Composable
private fun HomeScreenPreview() {
    MoviesTheme {
        HomeScreenContent(
            movies = List(10) { index ->
                Movie(
                    id = index,
                    name = "Name $index",
                    type = MovieType.MOVIE,
                    year = 1990 + index,
                    description = "",
                    rating = MovieRating(5.1, 6.4),
                    ageRating = 18,
                    logo = Image(null, null),
                    poster = Image(null, null),
                    backdrop = Image(null, null),
                    videos = null,
                    genres = emptyList(),
                    countries = emptyList(),
                    persons = emptyList(),
                    reviewInfo = null,
                    budget = null,
                    fees = null,
                    similarMovies = null,
                    sequelsAndPrequels = null,
                    top10 = null,
                    top250 = null,
                    isTicketsOnSale = false,
                    totalSeriesLength = null,
                    averageSeriesLength = null,
                    isSeries = false,
                    status = null,
                    length = index * 10
                )
            }
        )
    }
}
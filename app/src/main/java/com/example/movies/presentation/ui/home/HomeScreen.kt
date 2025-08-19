package com.example.movies.presentation.ui.home

import androidx.compose.animation.core.animateDpAsState
import androidx.compose.foundation.layout.PaddingValues
import androidx.compose.foundation.layout.WindowInsets
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.lazy.grid.rememberLazyGridState
import androidx.compose.material3.ExperimentalMaterial3Api
import androidx.compose.material3.SearchBar
import androidx.compose.material3.SearchBarDefaults
import androidx.compose.material3.pulltorefresh.PullToRefreshBox
import androidx.compose.material3.pulltorefresh.PullToRefreshDefaults
import androidx.compose.material3.pulltorefresh.rememberPullToRefreshState
import androidx.compose.runtime.Composable
import androidx.compose.runtime.collectAsState
import androidx.compose.runtime.derivedStateOf
import androidx.compose.runtime.getValue
import androidx.compose.runtime.remember
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.platform.LocalDensity
import androidx.compose.ui.tooling.preview.Preview
import androidx.compose.ui.unit.Dp
import androidx.compose.ui.unit.dp
import androidx.lifecycle.viewmodel.compose.viewModel
import com.example.movies.domain.model.Movie
import com.example.movies.presentation.ui.MoviesList
import com.example.movies.presentation.ui.theme.MoviesTheme
import dev.chrisbanes.haze.HazeState
import dev.chrisbanes.haze.hazeSource
import dev.chrisbanes.haze.rememberHazeState
import kotlin.math.min

@Composable
fun HomeScreen(
    contentPadding: PaddingValues = PaddingValues(0.dp),
    hazeState: HazeState = rememberHazeState(),
    viewModel: HomeScreenViewModel = viewModel(),
) {
    val movies = viewModel.movies.collectAsState().value
//    val movies = testMovies
    HomeScreenContent(
        movies = movies.movies,
        isRefreshing = movies.isRefreshing,
        onRefresh = { viewModel.getRecommendedMovies() },
        modifier = Modifier
            .hazeSource(hazeState)
            .fillMaxSize(),
        contentPadding = contentPadding
    )
}

@OptIn(ExperimentalMaterial3Api::class)
@Composable
fun HomeScreenContent(
    modifier: Modifier = Modifier,
    movies: List<Movie>,
    isRefreshing: Boolean = false,
    onRefresh: () -> Unit = {},
    contentPadding: PaddingValues = PaddingValues(0.dp)
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
    PullToRefreshBox(
        state = pullToRefreshState,
        isRefreshing = isRefreshing,
        onRefresh = onRefresh,
        indicator = {
            PullToRefreshDefaults.Indicator(
                state = pullToRefreshState,
                isRefreshing = isRefreshing,
                modifier = Modifier
                    .align(Alignment.TopCenter)
                    .padding(
                        top = contentPadding.calculateTopPadding() + SearchBarDefaults.InputFieldHeight
                    )
            )
        }
    ) {
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
            windowInsets = WindowInsets(top = contentPadding.calculateTopPadding())
        ) {}
        MoviesList(
            modifier = modifier,
            movies = movies,
            contentPadding = PaddingValues(
                top = contentPadding.calculateTopPadding() +
                        SearchBarDefaults.InputFieldHeight +
                        8.dp,
                bottom = contentPadding.calculateBottomPadding()
            ),
            state = listState
        )
    }
}

@Preview(showSystemUi = true)
@Composable
private fun HomeScreenPreview() {
    MoviesTheme {
//        HomeScreenContent(
//            movies = testMovies
//        )
    }
}
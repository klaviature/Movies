package com.example.movies.presentation.ui.moviedetail

import android.content.res.Configuration
import android.util.Log
import androidx.compose.animation.AnimatedVisibility
import androidx.compose.animation.animateColorAsState
import androidx.compose.animation.core.tween
import androidx.compose.foundation.background
import androidx.compose.foundation.clickable
import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.FlowRow
import androidx.compose.foundation.layout.PaddingValues
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.aspectRatio
import androidx.compose.foundation.layout.consumeWindowInsets
import androidx.compose.foundation.layout.fillMaxHeight
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.size
import androidx.compose.foundation.layout.width
import androidx.compose.foundation.lazy.LazyRow
import androidx.compose.foundation.lazy.items
import androidx.compose.foundation.lazy.rememberLazyListState
import androidx.compose.foundation.rememberScrollState
import androidx.compose.foundation.shape.CircleShape
import androidx.compose.foundation.verticalScroll
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.automirrored.filled.ArrowBack
import androidx.compose.material.icons.filled.Favorite
import androidx.compose.material.icons.outlined.FavoriteBorder
import androidx.compose.material3.CircularProgressIndicator
import androidx.compose.material3.ExperimentalMaterial3Api
import androidx.compose.material3.Icon
import androidx.compose.material3.IconButton
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.PlainTooltip
import androidx.compose.material3.Scaffold
import androidx.compose.material3.SnackbarHostState
import androidx.compose.material3.Text
import androidx.compose.material3.TooltipBox
import androidx.compose.material3.TooltipDefaults
import androidx.compose.material3.TopAppBar
import androidx.compose.material3.TopAppBarDefaults
import androidx.compose.material3.rememberTooltipState
import androidx.compose.runtime.Composable
import androidx.compose.runtime.LaunchedEffect
import androidx.compose.runtime.collectAsState
import androidx.compose.runtime.derivedStateOf
import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.remember
import androidx.compose.runtime.saveable.rememberSaveable
import androidx.compose.runtime.setValue
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.draw.clip
import androidx.compose.ui.graphics.Brush
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.graphics.Color.Companion.White
import androidx.compose.ui.graphics.vector.ImageVector
import androidx.compose.ui.input.nestedscroll.nestedScroll
import androidx.compose.ui.layout.ContentScale
import androidx.compose.ui.res.vectorResource
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.text.style.TextAlign
import androidx.compose.ui.text.style.TextOverflow
import androidx.compose.ui.tooling.preview.Preview
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import androidx.hilt.navigation.compose.hiltViewModel
import androidx.lifecycle.compose.collectAsStateWithLifecycle
import androidx.lifecycle.viewmodel.compose.viewModel
import androidx.navigation.NavHostController
import androidx.navigation.compose.rememberNavController
import coil3.compose.SubcomposeAsyncImage
import com.example.movies.R
import com.example.movies.convertUtcToLocal
import com.example.movies.domain.model.PersonMovie
import com.example.movies.domain.model.Review
import com.example.movies.domain.model.Video
import com.example.movies.presentation.navigation.RootNavGraph
import com.example.movies.presentation.ui.ExpandableText
import com.example.movies.presentation.ui.ImdbRatingCard
import com.example.movies.presentation.ui.KpRatingCard
import com.example.movies.presentation.ui.ReviewCard
import com.example.movies.presentation.ui.TrailerCard
import com.example.movies.presentation.ui.main.home.HomeNavGraph
import com.example.movies.presentation.ui.theme.MoviesTheme
import dev.chrisbanes.haze.materials.ExperimentalHazeMaterialsApi

// movieId for previews: 535341 (1 + 1 movie)
@Composable
fun MovieDetailScreen(
    navController: NavHostController = rememberNavController(),
    contentPadding: PaddingValues = PaddingValues(0.dp),
    viewModel: MovieDetailViewModel = hiltViewModel(),
    snackbarHostState: SnackbarHostState = remember { SnackbarHostState() }
) {
    val state = viewModel.state.collectAsStateWithLifecycle()
    LaunchedEffect(state.value.message) {
        state.value.message?.let {
            snackbarHostState.showSnackbar(it)
            viewModel.messageShown()
        }
    }
    LaunchedEffect(state.value.error) {
        state.value.error?.let {
            snackbarHostState.showSnackbar(
                when (it) {
                    MovieDetailState.Error.Local.Critical -> "Ошибка базы данных"
                    MovieDetailState.Error.Network.Forbidden -> "Ошибка доступа к ресурсу"
                    MovieDetailState.Error.Network.NotFound -> "Ресурс не найден"
                    MovieDetailState.Error.Network.Unauthorized -> "Ошибка авторизации"
                    is MovieDetailState.Error.Network.Unknown -> "Неизвестная ошибка: ${it.message}"
                }
            )
            viewModel.errorShown()
        }
    }

    MovieDetailScreenContent(
        uiState = state.value,
        contentPadding = contentPadding,
        onBackPressed = {
            navController.popBackStack()
        },
        onAddToFavouritesClick = {
            viewModel.toggleFavouriteStatus()
        },
        onAddToWatchedClick = {
            viewModel.toggleWatchedStatus()
        },
        onLinkedMovieClick = {
            navController.navigate(HomeNavGraph.MovieDetails(it))
        },
        onReviewsListReachEnd = {
            viewModel.loadReviews()
        }
    )
}

@OptIn(ExperimentalMaterial3Api::class, ExperimentalHazeMaterialsApi::class)
@Composable
fun MovieDetailScreenContent(
    contentPadding: PaddingValues = PaddingValues(0.dp),
    uiState: MovieDetailState,
    onBackPressed: () -> Unit = { },
    onAddToFavouritesClick: () -> Unit = { },
    onAddToWatchedClick: () -> Unit = { },
    onLinkedMovieClick: (Int) -> Unit = { },
    onReviewsListReachEnd: () -> Unit = {}
) {
    var displayedReview: Review? by remember { mutableStateOf(null) }

    val scrollBehavior = TopAppBarDefaults.pinnedScrollBehavior()
    val showAppBarContent by remember {
        derivedStateOf { scrollBehavior.state.contentOffset < -500 }
    }
    val backgroundColor by animateColorAsState(
        targetValue = if (showAppBarContent)
            MaterialTheme.colorScheme.surfaceContainer
        else
            Color.Transparent,
        animationSpec = tween(durationMillis = 300)
    )

    Scaffold(
        modifier = Modifier
            .nestedScroll(connection = scrollBehavior.nestedScrollConnection),
        topBar = {
            TopAppBar(
                title = {
                    AnimatedVisibility(visible = showAppBarContent) {
                        Text(text = uiState.movie.name, color = MaterialTheme.colorScheme.onSurfaceVariant)
                    }
                },
                scrollBehavior = scrollBehavior,
                colors = TopAppBarDefaults.topAppBarColors(
                    containerColor = backgroundColor,
                    scrolledContainerColor = backgroundColor
                ),
                navigationIcon = {
                    IconButton(onClick = onBackPressed) {
                        Icon(
                            imageVector = Icons.AutoMirrored.Default.ArrowBack,
                            contentDescription = null,
                            tint = MaterialTheme.colorScheme.onSurfaceVariant
                        )
                    }
                },
                actions = {
                    TooltipBox(
                        positionProvider = TooltipDefaults.rememberPlainTooltipPositionProvider(),
                        tooltip = {
                            PlainTooltip {
                                Text(
                                    text = if (uiState.isFavourite) {
                                        "В избранном"
                                    } else {
                                        "Добавить в избранное"
                                    }
                                )
                            }
                        },
                        state = rememberTooltipState()
                    ) {
                        IconButton(
                            onClick = onAddToFavouritesClick,
                            enabled = !uiState.isFavouriteLoading
                        ) {
                            if (uiState.isFavouriteLoading) {
                                CircularProgressIndicator(modifier = Modifier.size(24.dp))
                            } else {
                                Icon(
                                    imageVector = if (uiState.isFavourite) {
                                        Icons.Filled.Favorite
                                    } else {
                                        Icons.Outlined.FavoriteBorder
                                    },
                                    contentDescription = null
                                )
                            }
                        }
                    }
                    TooltipBox(
                        positionProvider = TooltipDefaults.rememberPlainTooltipPositionProvider(),
                        tooltip = {
                            PlainTooltip {
                                Text(
                                    text = if (uiState.isWatched) {
                                        "Просмотрено"
                                    } else {
                                        "Добавить в просмотренное"
                                    }
                                )
                            }
                        },
                        state = rememberTooltipState()
                    ) {
                        IconButton(
                            onClick = onAddToWatchedClick,
                            enabled = !uiState.isWatchedLoading
                        ) {
                            if (uiState.isWatchedLoading) {
                                CircularProgressIndicator(modifier = Modifier.size(24.dp))
                            } else {
                                Icon(
                                    imageVector = if (uiState.isWatched) {
                                        ImageVector.vectorResource(R.drawable.eye_filled)
                                    } else {
                                        ImageVector.vectorResource(R.drawable.eye_outlined)
                                    },
                                    contentDescription = null
                                )
                            }
                        }
                    }
                }
            )
        }
    ) { innerPadding ->
        val scrollState = rememberScrollState()

        if (uiState.isLoading) {
            Box(modifier = Modifier.fillMaxSize(), contentAlignment = Alignment.Center) {
                CircularProgressIndicator()
            }
        } else {
            SubcomposeAsyncImage(
                modifier = Modifier
                    .fillMaxWidth()
                    .height(450.dp),
                model = uiState.movie.backdropUrl ?: uiState.movie.posterUrl,
                contentDescription = null,
                contentScale = ContentScale.Crop
            )
            Box(
                modifier = Modifier
                    .fillMaxWidth()
                    .verticalScroll(state = scrollState)
                    .padding(innerPadding)
                    .padding(contentPadding)
                    .padding(top = 150.dp)
                    .background(
                        brush = Brush.verticalGradient(
                            colors = listOf(
                                Color.Transparent,
                                MaterialTheme.colorScheme.background
                            ),
                            startY = 90f,
                            endY = 450f
                        )
                    )
            ) {
                Column(
                    verticalArrangement = Arrangement.spacedBy(16.dp)
                ) {
                    MovieHeader(
                        modifier = Modifier
                            .padding(horizontal = 12.dp)
                            .consumeWindowInsets(innerPadding), // TODO РЕШИТЬ ПРОБЛЕМУ В Горизонтальной ориентации
                        ratingKp = uiState.movie.ratingKp,
                        ratingImdb = uiState.movie.ratingImdb,
                        year = uiState.movie.year,
                        countries = uiState.movie.countries,
                        length = uiState.movie.length,
                        genres = uiState.movie.genres,
                        logoUrl = uiState.movie.logoUrl,
                        name = uiState.movie.name,
                        ageRating = uiState.movie.ageRating
                    )
                    MovieDescription(
                        modifier = Modifier.padding(horizontal = 12.dp),
                        description = uiState.movie.description
                    )
                    if (uiState.movie.persons.isNotEmpty()) {
                        PersonList(
                            modifier = Modifier.fillMaxWidth(),
                            persons = uiState.movie.persons,
                            onClick = {},
                            contentPadding = PaddingValues(horizontal = 12.dp)
                        )
                    }
                    if (uiState.movie.trailers?.isNotEmpty() ?: false) {
                        TrailersList(
                            trailers = uiState.movie.trailers,
                            contentPadding = PaddingValues(horizontal = 12.dp)
                        )
                    }
                    if (uiState.reviews.isNotEmpty()) {
                        ReviewsList(
                            reviews = uiState.reviews,
                            onItemClick = { review ->
                                displayedReview = review
                            },
                            contentPadding = PaddingValues(horizontal = 12.dp),
                            onReachEnd = onReviewsListReachEnd
                        )
                    }
                    if (uiState.movie.sequelsAndPrequels.isNotEmpty()) {
                        Column(
                            modifier = Modifier.fillMaxWidth(),
                            verticalArrangement = Arrangement.spacedBy(4.dp)
                        ) {
                            Text(
                                modifier = Modifier.padding(PaddingValues(horizontal = 12.dp)),
                                text = "Сиквелы и приквелы",
                                fontWeight = FontWeight.Bold,
                                fontSize = 24.sp
                            )
                            LinkedMovieList(
                                movies = uiState.movie.sequelsAndPrequels,
                                modifier = Modifier.fillMaxWidth(),
                                contentPadding = PaddingValues(horizontal = 12.dp),
                                onClick = onLinkedMovieClick
                            )
                        }
                    }
                }
            }
            displayedReview?.let {
                ReviewDetailSheet(
                    onDismissRequest = { displayedReview = null },
                    title = it.title,
                    text = it.text,
                    type = it.type,
                    date = it.date,
                    author = it.author,
                    authorRating = it.authorRating,
                    likes = it.likes,
                    dislikes = it.dislikes
                )
            }
        }
    }
}

@Composable
private fun MovieHeader(
    modifier: Modifier = Modifier,
    ratingKp: String?,
    ratingImdb: String?,
    year: String,
    countries: List<String>?,
    length: String?,
    genres: List<String>?,
    logoUrl: String?,
    name: String,
    ageRating: String
) {
    Column(
        modifier = modifier,
        verticalArrangement = Arrangement.spacedBy(4.dp)
    ) {
        Box(
            modifier = Modifier
                .height(80.dp)
                .padding(bottom = 8.dp),
            contentAlignment = Alignment.BottomStart
        ) {
            if (logoUrl != null) {
                SubcomposeAsyncImage(
                    modifier = Modifier.fillMaxHeight(),
                    model = logoUrl,
                    contentDescription = null,
                    alignment = Alignment.BottomStart
                )
            } else {
                Text(
                    text = name,
                    maxLines = 2,
                    overflow = TextOverflow.Ellipsis,
                    fontSize = 32.sp,
                    fontWeight = FontWeight.Bold,
                    color = White
                )
            }
        }
        Row(
            horizontalArrangement = Arrangement.spacedBy(6.dp),
            verticalAlignment = Alignment.CenterVertically
        ) {
            ratingKp?.let { KpRatingCard(rating = it) }
            ratingImdb?.let { ImdbRatingCard(rating = it) }
        }
        FlowRow(
            horizontalArrangement = Arrangement.spacedBy(6.dp),
            verticalArrangement = Arrangement.Center
        ) {
            Text(
                text = year,
                fontSize = 14.sp,
                fontWeight = FontWeight.Bold,
                color = MaterialTheme.colorScheme.onSurfaceVariant,
                lineHeight = 16.sp
            )
            Text(
                text = countries?.joinToString() ?: "",
                fontSize = 14.sp,
                fontWeight = FontWeight.Bold,
                color = MaterialTheme.colorScheme.onSurfaceVariant,
                lineHeight = 16.sp
            )
            Text(
                text = length ?: "",
                fontSize = 14.sp,
                fontWeight = FontWeight.Bold,
                color = MaterialTheme.colorScheme.onSurfaceVariant,
                lineHeight = 16.sp
            )
        }
        FlowRow(
            horizontalArrangement = Arrangement.spacedBy(6.dp),
            verticalArrangement = Arrangement.Center
        ) {
            Text(
                text = genres?.joinToString() ?: "",
                fontSize = 14.sp,
                fontWeight = FontWeight.Bold,
                color = MaterialTheme.colorScheme.onSurfaceVariant,
                lineHeight = 16.sp
            )
            Text(
                text = ageRating,
                fontSize = 14.sp,
                fontWeight = FontWeight.Bold,
                color = MaterialTheme.colorScheme.onSurfaceVariant,
                lineHeight = 16.sp
            )
        }
    }
}

@Composable
private fun MovieDescription(
    modifier: Modifier = Modifier,
    description: String
) {
    var isExpanded by rememberSaveable { mutableStateOf(false) }
    Column(
        modifier = modifier,
        verticalArrangement = Arrangement.spacedBy(4.dp)
    ) {
        Text(
            text = "Описание",
            fontWeight = FontWeight.Bold,
            fontSize = 24.sp
        )
        ExpandableText(
            text = description,
            showMoreText = "Показать еще",
            showLessText = "Свернуть",
            isExpanded = isExpanded,
            onExpandedChange = { isExpanded = it },
            collapsedMaxLines = 5
        )
    }
}

@Composable
fun TrailersList(
    modifier: Modifier = Modifier,
    trailers: List<Video>,
    contentPadding: PaddingValues = PaddingValues()
) {
    Column(
        modifier = modifier,
        verticalArrangement = Arrangement.spacedBy(4.dp)
    ) {
        Text(
            modifier = Modifier.padding(contentPadding),
            text = "Трейлеры",
            fontWeight = FontWeight.Bold,
            fontSize = 24.sp
        )
        LazyRow(
            horizontalArrangement = Arrangement.spacedBy(12.dp),
            contentPadding = contentPadding
        ) {
            items(trailers) {
                TrailerCard(trailerUrl = it.url)
            }
        }
    }
}

@Composable
fun ReviewsList(
    modifier: Modifier = Modifier,
    reviews: List<Review>,
    onItemClick: (Review) -> Unit,
    contentPadding: PaddingValues = PaddingValues(),
    onReachEnd: () -> Unit = {}
) {
    val lazyListState = rememberLazyListState()

    val shouldLoadMore by remember {
        derivedStateOf {
            val lastVisibleItem = lazyListState.layoutInfo.visibleItemsInfo.lastOrNull()
                ?: return@derivedStateOf false

            lastVisibleItem.index >= lazyListState.layoutInfo.totalItemsCount - 1
        }
    }

    LaunchedEffect(shouldLoadMore) {
        if (shouldLoadMore) {
            Log.d("MovieDetailsScreen", "достигнут конец списка рецензий")
            onReachEnd()
        }
    }

    Column(
        modifier = modifier,
        verticalArrangement = Arrangement.spacedBy(4.dp)
    ) {
        Text(
            modifier = Modifier.padding(contentPadding),
            text = "Рецензии",
            fontWeight = FontWeight.Bold,
            fontSize = 24.sp
        )
        LazyRow(
            state = lazyListState,
            horizontalArrangement = Arrangement.spacedBy(12.dp),
            contentPadding = contentPadding
        ) {
            items(reviews) {
                ReviewCard(
                    onClick = { onItemClick(it) },
                    title = it.title,
                    text = it.text,
                    date = it.date.convertUtcToLocal("dd MMMM yyyy"),
                    author = it.author,
                    type = it.type,
                    likes = it.likes,
                    dislikes = it.dislikes
                )
            }
        }
    }
}

@Composable
fun PersonList(
    modifier: Modifier = Modifier,
    persons: List<MovieDetailState.MoviePersonUi>,
    onClick: (Int) -> Unit,
    contentPadding: PaddingValues = PaddingValues()
) {
    Column(
        modifier = modifier,
        verticalArrangement = Arrangement.spacedBy(4.dp)
    ) {
        Text(
            modifier = Modifier.padding(contentPadding),
            text = "Актеры и режжисеры",
            fontWeight = FontWeight.Bold,
            fontSize = 24.sp
        )
        LazyRow(
            modifier = Modifier.fillMaxWidth(),
            horizontalArrangement = Arrangement.spacedBy(12.dp),
            contentPadding = contentPadding
        ) {
            items(persons) {
                MoviePerson(
                    person = MovieDetailState.MoviePersonUi(
                        id = it.id,
                        name = it.name,
                        photoUrl = it.photoUrl,
                        profession = it.profession,
                        description = it.description
                    ),
                    onClick = onClick
                )
            }
        }
    }
}

@Composable
fun MoviePerson(
    modifier: Modifier = Modifier,
    person: MovieDetailState.MoviePersonUi,
    onClick: (Int) -> Unit
) {
    Column(
        modifier = modifier
            .size(width = 100.dp, height = 170.dp)
            .clickable(onClick = { onClick(person.id) }),
        horizontalAlignment = Alignment.CenterHorizontally
    ) {
        SubcomposeAsyncImage(
            modifier = Modifier
                .padding(4.dp)
                .aspectRatio(1f)
                .clip(CircleShape)
                .background(MaterialTheme.colorScheme.surfaceContainer)
            ,
            model = person.photoUrl,
            contentDescription = null,
            contentScale = ContentScale.Crop,
            loading = {
                Box(
                    modifier = Modifier.fillMaxSize()
                ) { }
            },
            error = {
                Box(
                    modifier = Modifier.fillMaxSize()
                ) { }
            }
        )
        Text(
            text = person.name,
            maxLines = 2,
            overflow = TextOverflow.Ellipsis,
            lineHeight = 16.sp,
            textAlign = TextAlign.Center
        )
        person.profession?.let {
            Text(
                text = it,
                maxLines = 1,
                overflow = TextOverflow.Ellipsis,
                lineHeight = 16.sp,
                textAlign = TextAlign.Center
            )
        }
    }
}

@Preview(
    showSystemUi = true,
    uiMode = Configuration.UI_MODE_NIGHT_NO or Configuration.UI_MODE_TYPE_UNDEFINED
)
@Composable
private fun MovieDetailScreenPreview() {
    MoviesTheme {
        MovieDetailScreenContent(
            uiState = MovieDetailState(
                isLoading = false,
                movie = MovieDetailState.MovieUi(
                    name = "1 + 1",
                    type = "",
                    year = "2026",
                    description = "Some description",
                    length = "120 min",
                    ageRating = "18+",
                    posterUrl = "",
                    backdropUrl = "",
                    logoUrl = "",
                    ratingKp = "7.3",
                    ratingImdb = "7.3",
                    trailers = emptyList(),
                    genres = emptyList(),
                    countries = emptyList(),
                    persons = listOf(
                        MovieDetailState.MoviePersonUi(
                            id = 0,
                            name = "Egor Fomin Pavlovich",
                            photoUrl = "",
                            profession = "Actor",
                            description = ""
                        )
                    ),
                    sequelsAndPrequels = List(5) { index ->
                        LinkedMovieUi(
                            id = index,
                            name = "Movie $index",
                            posterUrl = "",
                            ratingKp = (index + 3).toDouble(),
                            ratingImdb = (index + 3).toDouble()
                        )
                    }
                ),
                reviews = emptyList(),
                error = null,
                message = null,
                isWatchedLoading = false,
                isFavouriteLoading = false,
                isWatched = false,
                isFavourite = false
            )
        )
    }
}

@Preview(
    showSystemUi = true,
    uiMode = Configuration.UI_MODE_NIGHT_YES or Configuration.UI_MODE_TYPE_UNDEFINED
)
@Composable
private fun MovieDetailScreenDarkThemePreview() {
    MoviesTheme {
        MovieDetailScreenContent(
            uiState = MovieDetailState(
                isLoading = false,
                movie = MovieDetailState.MovieUi(
                    name = "1 + 1",
                    type = "",
                    year = "2026",
                    description = "Some description",
                    length = "120 min",
                    ageRating = "18+",
                    posterUrl = "",
                    backdropUrl = "",
                    logoUrl = "",
                    ratingKp = "7.3",
                    ratingImdb = "7.3",
                    trailers = emptyList(),
                    genres = emptyList(),
                    countries = emptyList()
                ),
                reviews = emptyList(),
                error = null,
                message = null,
                isWatchedLoading = false,
                isFavouriteLoading = false,
                isWatched = false,
                isFavourite = false
            )
        )
    }
}
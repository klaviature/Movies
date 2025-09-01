package com.example.movies.presentation.ui.moviedetail

import android.content.res.Configuration
import androidx.compose.animation.AnimatedVisibility
import androidx.compose.animation.animateColorAsState
import androidx.compose.animation.core.tween
import androidx.compose.foundation.background
import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.FlowRow
import androidx.compose.foundation.layout.PaddingValues
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.consumeWindowInsets
import androidx.compose.foundation.layout.fillMaxHeight
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.size
import androidx.compose.foundation.lazy.LazyRow
import androidx.compose.foundation.lazy.items
import androidx.compose.foundation.rememberScrollState
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
import androidx.compose.ui.graphics.Brush
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.graphics.Color.Companion.White
import androidx.compose.ui.graphics.vector.ImageVector
import androidx.compose.ui.input.nestedscroll.nestedScroll
import androidx.compose.ui.layout.ContentScale
import androidx.compose.ui.res.vectorResource
import androidx.compose.ui.text.font.FontWeight
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
import com.example.movies.domain.model.Review
import com.example.movies.domain.model.Video
import com.example.movies.presentation.ui.ExpandableText
import com.example.movies.presentation.ui.ImdbRatingCard
import com.example.movies.presentation.ui.KpRatingCard
import com.example.movies.presentation.ui.ReviewCard
import com.example.movies.presentation.ui.TrailerCard
import com.example.movies.presentation.ui.theme.MoviesTheme
import dev.chrisbanes.haze.materials.ExperimentalHazeMaterialsApi

// movieId for previews: 535341 (1 + 1 movie)
@Composable
fun MovieDetailScreen(
    navController: NavHostController = rememberNavController(),
    movieId: Int,
    contentPadding: PaddingValues = PaddingValues(0.dp),
    viewModel: MovieDetailViewModel = hiltViewModel(),
    snackbarHostState: SnackbarHostState = remember { SnackbarHostState() }
) {
    val state = viewModel.state.collectAsState()
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
        contentPadding = contentPadding,
        isLoading = state.value.isLoading,
        name = state.value.movie.name,
        type = state.value.movie.type,
        year = state.value.movie.year,
        description = state.value.movie.description,
        length = state.value.movie.length,
        ageRating = state.value.movie.ageRating,
        posterUrl = state.value.movie.posterUrl,
        ratingKp = state.value.movie.ratingKp,
        ratingImdb = state.value.movie.ratingImdb,
        trailers = state.value.movie.trailers ?: emptyList(),
        genres = state.value.movie.genres,
        countries = state.value.movie.countries,
        reviews = state.value.reviews,
        backdropUrl = state.value.movie.backdropUrl,
        logoUrl = state.value.movie.logoUrl,
        isFavourite = state.value.isFavourite,
        isWatched = state.value.isWatched,
        isFavouriteLoading = state.value.isFavouriteLoading,
        isWatchedLoading = state.value.isWatchedLoading,
        onBackPressed = {
            navController.popBackStack()
        },
        onAddToFavouritesClick = {
            viewModel.toggleFavouriteStatus()
        },
        onAddToWatchedClick = {
            viewModel.toggleWatchedStatus()
        }
    )
}

@OptIn(ExperimentalMaterial3Api::class, ExperimentalHazeMaterialsApi::class)
@Composable
fun MovieDetailScreenContent(
    contentPadding: PaddingValues = PaddingValues(0.dp),
    name: String,
    type: String,
    year: String,
    description: String,
    length: String?,
    ageRating: String,
    posterUrl: String?,
    backdropUrl: String?,
    logoUrl: String?,
    ratingKp: String?,
    ratingImdb: String?,
    trailers: List<Video>,
    genres: List<String>?,
    countries: List<String>?,
    reviews: List<Review>,
    isLoading: Boolean = false,
    isFavourite: Boolean = false,
    isWatched: Boolean = false,
    isFavouriteLoading: Boolean = false,
    isWatchedLoading: Boolean = false,
    onBackPressed: () -> Unit = { },
    onAddToFavouritesClick: () -> Unit = { },
    onAddToWatchedClick: () -> Unit = { }
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
                        Text(text = name, color = MaterialTheme.colorScheme.onSurfaceVariant)
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
                                    text = if (isFavourite) {
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
                            enabled = !isFavouriteLoading
                        ) {
                            if (isFavouriteLoading) {
                                CircularProgressIndicator(modifier = Modifier.size(24.dp))
                            } else {
                                Icon(
                                    imageVector = if (isFavourite) {
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
                                    text = if (isWatched) {
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
                            enabled = !isWatchedLoading
                        ) {
                            if (isWatchedLoading) {
                                CircularProgressIndicator(modifier = Modifier.size(24.dp))
                            } else {
                                Icon(
                                    imageVector = if (isWatched) {
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

        if (isLoading) {
            Box(modifier = Modifier.fillMaxSize(), contentAlignment = Alignment.Center) {
                CircularProgressIndicator()
            }
        } else {
            SubcomposeAsyncImage(
                modifier = Modifier
                    .fillMaxWidth()
                    .height(450.dp),
                model = backdropUrl ?: posterUrl,
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
                        ratingKp = ratingKp,
                        ratingImdb = ratingImdb,
                        year = year,
                        countries = countries,
                        length = length,
                        genres = genres,
                        logoUrl = logoUrl,
                        name = name,
                        ageRating = ageRating
                    )
                    MovieDescription(
                        modifier = Modifier.padding(horizontal = 12.dp),
                        description = description
                    )
                    if (trailers.isNotEmpty()) {
                        TrailersList(
                            trailers = trailers,
                            contentPadding = PaddingValues(horizontal = 12.dp)
                        )
                    }
                    if (reviews.isNotEmpty()) {
                        ReviewsList(
                            reviews = reviews,
                            onItemClick = { review ->
                                displayedReview = review
                            },
                            contentPadding = PaddingValues(horizontal = 12.dp)
                        )
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
    contentPadding: PaddingValues = PaddingValues()
) {
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

@Preview(
    showSystemUi = true,
    uiMode = Configuration.UI_MODE_NIGHT_NO or Configuration.UI_MODE_TYPE_UNDEFINED
)
@Composable
private fun MovieDetailScreenPreview() {
    MoviesTheme {
        MovieDetailScreenContent(
            isLoading = false,
            name = "1 + 1",
            type = "",
            year = "2025",
            description = "Пострадав в результате несчастного случая, богатый аристократ Филипп " +
                    "нанимает в помощники человека, который менее всего подходит для этой работы, " +
                    "– молодого жителя предместья Дрисса, только что освободившегося из тюрьмы. " +
                    "Несмотря на то, что Филипп прикован к инвалидному креслу, Дриссу удается " +
                    "привнести в размеренную жизнь аристократа дух приключений.",
            length = "123 мин",
            ageRating = "18+",
            posterUrl = "",
            backdropUrl = "",
            logoUrl = "",
            ratingKp = "9.3",
            ratingImdb = "9.3",
            trailers = emptyList(),
            genres = listOf("Драма", "Комедия"),
            countries = listOf("Франция", "США", "Япония", "Чехия"),
            reviews = emptyList(),
            isFavourite = false,
            isWatched = false
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
            isLoading = false,
            name = "1 + 1",
            type = "",
            year = "2025",
            description = "Пострадав в результате несчастного случая, богатый аристократ Филипп " +
                    "нанимает в помощники человека, который менее всего подходит для этой работы, " +
                    "– молодого жителя предместья Дрисса, только что освободившегося из тюрьмы. " +
                    "Несмотря на то, что Филипп прикован к инвалидному креслу, Дриссу удается " +
                    "привнести в размеренную жизнь аристократа дух приключений.",
            length = "123",
            ageRating = "18+",
            posterUrl = "",
            backdropUrl = "",
            logoUrl = "",
            ratingKp = "9.3",
            ratingImdb = "9.3",
            trailers = emptyList(),
            genres = listOf("Драма", "Комедия"),
            countries = listOf("Франция", "США"),
            reviews = emptyList(),
            isFavourite = false,
            isWatched = false
        )
    }
}
package com.example.movies.presentation.ui.moviedetail

import android.content.res.Configuration
import androidx.compose.animation.AnimatedVisibility
import androidx.compose.animation.animateColorAsState
import androidx.compose.animation.core.tween
import androidx.compose.foundation.background
import androidx.compose.foundation.isSystemInDarkTheme
import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.PaddingValues
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.consumeWindowInsets
import androidx.compose.foundation.layout.fillMaxHeight
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.lazy.LazyRow
import androidx.compose.foundation.lazy.items
import androidx.compose.foundation.rememberScrollState
import androidx.compose.foundation.verticalScroll
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.automirrored.filled.ArrowBack
import androidx.compose.material.icons.filled.Favorite
import androidx.compose.material.icons.outlined.FavoriteBorder
import androidx.compose.material3.ExperimentalMaterial3Api
import androidx.compose.material3.Icon
import androidx.compose.material3.IconButton
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.PlainTooltip
import androidx.compose.material3.Scaffold
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
import androidx.lifecycle.viewmodel.compose.viewModel
import androidx.navigation.NavHostController
import androidx.navigation.compose.rememberNavController
import coil3.compose.AsyncImage
import com.example.movies.R
import com.example.movies.domain.model.Review
import com.example.movies.domain.model.Video
import com.example.movies.presentation.ui.ImdbRatingCard
import com.example.movies.presentation.ui.KpRatingCard
import com.example.movies.presentation.ui.ReviewCard
import com.example.movies.presentation.ui.TrailerCard
import com.example.movies.presentation.ui.theme.MoviesTheme
import dev.chrisbanes.haze.HazeProgressive
import dev.chrisbanes.haze.hazeEffect
import dev.chrisbanes.haze.hazeSource
import dev.chrisbanes.haze.materials.ExperimentalHazeMaterialsApi
import dev.chrisbanes.haze.materials.HazeMaterials
import dev.chrisbanes.haze.rememberHazeState

// movieId for previews: 535341 (1 + 1 movie)
@Composable
fun MovieDetailScreen(
    navController: NavHostController = rememberNavController(),
    movieId: Int,
    viewModel: MovieDetailViewModel = viewModel()
) {
    val state = viewModel.state.collectAsState()
    LaunchedEffect(movieId) {
        viewModel.loadMovieInfo(movieId)
        viewModel.loadReviews(movieId)
    }

    MovieDetailScreenContent(
        name = state.value.name,
        type = state.value.type,
        year = state.value.year,
        description = state.value.description,
        length = state.value.length,
        ageRating = state.value.ageRating,
        posterUrl = state.value.posterUrl,
        ratingKp = state.value.ratingKp,
        ratingImdb = state.value.ratingImdb,
        trailers = state.value.trailers ?: emptyList(),
        genres = state.value.genres,
        countries = state.value.countries,
        reviews = state.value.reviews,
        backdropUrl = state.value.backdropUrl,
        logoUrl = state.value.logoUrl,
        isFavourite = state.value.isFavourite,
        isWatched = state.value.isWatched,
        onBackPressed = {
            navController.popBackStack()
        }
    )
}

@OptIn(ExperimentalMaterial3Api::class, ExperimentalHazeMaterialsApi::class)
@Composable
fun MovieDetailScreenContent(
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
    isFavourite: Boolean = false,
    isWatched: Boolean = false,
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

    val hazeState = rememberHazeState(blurEnabled = true)

    Scaffold(
        modifier = Modifier
            .nestedScroll(connection = scrollBehavior.nestedScrollConnection),
        topBar = {
            TopAppBar(
                modifier = Modifier
                    .hazeEffect(state = hazeState, style = HazeMaterials.ultraThin()) {
                        progressive = HazeProgressive.verticalGradient(
                            startIntensity = 0.3f, endIntensity = 0f
                        )
                    },
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
                        IconButton(onClick = onAddToFavouritesClick) {
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
                        state = rememberTooltipState(initialIsVisible = true)
                    ) {
                        IconButton(onClick = onAddToWatchedClick) {
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
            )
        }
    ) { innerPadding ->
        val scrollState = rememberScrollState()

        AsyncImage(
            modifier = Modifier
                .hazeSource(hazeState)
                .fillMaxWidth()
                .height(450.dp),
            model = backdropUrl ?: posterUrl,
            contentDescription = null,
            contentScale = ContentScale.Crop
        )
        Box(
            modifier = Modifier
                .hazeSource(state = hazeState)
                .fillMaxWidth()
                .verticalScroll(state = scrollState)
                .padding(innerPadding)
                .padding(top = 140.dp)
                .background(
                    brush = Brush.verticalGradient(
                        colors = listOf(
                            Color.Transparent,
                            MaterialTheme.colorScheme.background
                        ),
                        startY = 200f,
                        endY = 450f
                    )
                )
        ) {
            Column(
                verticalArrangement = Arrangement.spacedBy(12.dp)
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
                AsyncImage(
                    modifier = Modifier.fillMaxHeight(),
                    model = logoUrl,
                    contentDescription = null
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
        Row(
            horizontalArrangement = Arrangement.spacedBy(6.dp),
            verticalAlignment = Alignment.CenterVertically
        ) {
            Text(
                text = year,
                fontSize = 14.sp,
                fontWeight = FontWeight.Bold,
                color = if (isSystemInDarkTheme()) {
                    Color(0xFF7E7E7E)
                } else {
                    Color(0xFF4D4D4D)
                },
                lineHeight = 16.sp
            )
            Text(
                text = countries?.joinToString() ?: "",
                fontSize = 14.sp,
                fontWeight = FontWeight.Bold,
                color =  if (isSystemInDarkTheme()) {
                    Color(0xFF7E7E7E)
                } else {
                    Color(0xFF4D4D4D)
                },
                lineHeight = 16.sp
            )
            Text(
                text = length ?: "",
                fontSize = 14.sp,
                fontWeight = FontWeight.Bold,
                color = if (isSystemInDarkTheme()) {
                    Color(0xFF7E7E7E)
                } else {
                    Color(0xFF4D4D4D)
                },
                lineHeight = 16.sp
            )
        }
        Row(
            horizontalArrangement = Arrangement.spacedBy(6.dp),
            verticalAlignment = Alignment.CenterVertically
        ) {
            Text(
                text = genres?.joinToString() ?: "",
                fontSize = 14.sp,
                fontWeight = FontWeight.Bold,
                color = if (isSystemInDarkTheme()) {
                    Color(0xFF7E7E7E)
                } else {
                    Color(0xFF4D4D4D)
                },
                lineHeight = 16.sp
            )
            Text(
                text = ageRating,
                fontSize = 14.sp,
                fontWeight = FontWeight.Bold,
                color = if (isSystemInDarkTheme()) {
                    Color(0xFF7E7E7E)
                } else {
                    Color(0xFF4D4D4D)
                },
                lineHeight = 16.sp
            )
        }
    }
}

@Composable
fun MovieDescription(
    modifier: Modifier = Modifier,
    description: String
) {
    Column(
        modifier = modifier
    ) {
        Text(
            text = "Description",
            fontWeight = FontWeight.Bold,
            fontSize = 24.sp
        )
        Text(text = description, fontSize = 14.sp)
    }
}

@Composable
private fun TrailersList(
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
            text = "Trailers",
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
            text = "Reviews",
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
                    date = it.date,
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
//        MovieDetailScreenContent(
//            name = "Третий лишний",
//            rating = Rating(kp = 4.5, imdb = 5.3),
//            year = 2023.toString(),
//            length = "1ч 23мин",
//            genres = listOf(Genre(name = "Боевик"), Genre(name = "Драма")),
//            countries = listOf(Country(name = "Россия"), Country(name = "США")),
//            description = "Пострадав в результате несчастного случая, богатый аристократ Филипп " +
//                    "нанимает в помощники человека, который менее всего подходит для этой работы, " +
//                    "– молодого жителя предместья Дрисса, только что освободившегося из тюрьмы. " +
//                    "Несмотря на то, что Филипп прикован к инвалидному креслу, Дриссу удается " +
//                    "привнести в размеренную жизнь аристократа дух приключений.",
//            posterUrl = "https://image.openmoviedb.com/tmdb-images/original/hvptcK6WmDqu4xZZ0WuBQ3vYInE.png",
//            trailers = listOf(
//                Trailer(),
//                Trailer(),
//                Trailer()
//            ),
//            reviews = listOf(
//                Review(
//                    id = 1,
//                    movieId = 101,
//                    title = "Оставил сильное впечатление",
//                    review = "Фильм приятно удивил своей глубиной. Сюжет хорошо выстроен, а актёрская игра вызывает доверие. " +
//                            "Особенно хочется отметить музыкальное сопровождение — оно добавляет атмосферы и усиливает эмоциональные сцены. \n" +
//                            "Редкий случай, когда всё складывается в цельную картину, которую хочется пересмотреть.",
//                    type = "Позитивный",
//                    date = "6 июня, 2025",
//                    author = "Егор Фомин",
//                    userRating = 9,
//                    likes = 132,
//                    dislikes = 11
//                ),
//                Review(
//                    id = 2,
//                    movieId = 101,
//                    title = "Обычное кино",
//                    review = "Фильм получился довольно стандартным. Никаких откровений, но и провалов тоже нет. " +
//                            "Сюжет предсказуемый, местами скучноват, но в целом смотрибельно. \n" +
//                            "Можно глянуть под настроение, но в памяти не задержится надолго.",
//                    type = "Нейтральный",
//                    date = "10 июля, 2025",
//                    author = "Лена Степанова",
//                    userRating = 6,
//                    likes = 64,
//                    dislikes = 27
//                ),
//                Review(
//                    id = 3,
//                    movieId = 101,
//                    title = "Норм, но не более",
//                    review = "Кино как кино. Были хорошие сцены, но общая динамика провисает. " +
//                            "Диалоги местами казались неестественными, персонажи недоработаны. \n" +
//                            "Не жалею, что посмотрел, но второй раз вряд ли захочу.",
//                    type = "Нейтральный",
//                    date = "16 июля, 2025",
//                    author = "Сергей Головин",
//                    userRating = 5,
//                    likes = 49,
//                    dislikes = 32
//                ),
//                Review(
//                    id = 4,
//                    movieId = 101,
//                    title = "Можно посмотреть",
//                    review = "Если не ждать многого, фильм зайдёт. Простенький сюжет, пара удачных актёрских ролей, и в целом — ок. " +
//                            "Визуально выглядит неплохо, но ощущение, что чего-то не хватает. \n" +
//                            "Для вечернего просмотра подойдёт.",
//                    type = "Нейтральный",
//                    date = "21 июля, 2025",
//                    author = "Марина Ветрова",
//                    userRating = 6,
//                    likes = 58,
//                    dislikes = 19
//                ),
//                Review(
//                    id = 5,
//                    movieId = 101,
//                    title = "Разочарование",
//                    review = "Слишком затянуто и местами откровенно скучно. Ожидания были выше — трейлер обещал больше, чем дал сам фильм. \n" +
//                            "Актёры старались, но слабый сценарий всё испортил. После просмотра остаётся только недоумение и сожаление о потраченном времени.",
//                    type = "Негативный",
//                    date = "29 июля, 2025",
//                    author = "Кирилл Орлов",
//                    userRating = 3,
//                    likes = 22,
//                    dislikes = 67
//                )
//            )
//        )
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
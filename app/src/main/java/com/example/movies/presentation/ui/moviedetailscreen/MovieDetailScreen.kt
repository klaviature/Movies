package com.example.movies.presentation.ui.moviedetailscreen

import android.content.res.Configuration
import androidx.compose.animation.AnimatedVisibility
import androidx.compose.animation.animateColorAsState
import androidx.compose.animation.core.animateFloatAsState
import androidx.compose.animation.core.tween
import androidx.compose.foundation.Image
import androidx.compose.foundation.background
import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.lazy.LazyRow
import androidx.compose.foundation.lazy.items
import androidx.compose.foundation.rememberScrollState
import androidx.compose.foundation.verticalScroll
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.automirrored.filled.ArrowBack
import androidx.compose.material3.ExperimentalMaterial3Api
import androidx.compose.material3.Icon
import androidx.compose.material3.IconButton
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.Scaffold
import androidx.compose.material3.Text
import androidx.compose.material3.TopAppBar
import androidx.compose.material3.TopAppBarDefaults
import androidx.compose.runtime.Composable
import androidx.compose.runtime.derivedStateOf
import androidx.compose.runtime.getValue
import androidx.compose.runtime.remember
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Brush
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.input.nestedscroll.nestedScroll
import androidx.compose.ui.layout.ContentScale
import androidx.compose.ui.res.painterResource
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.tooling.preview.Preview
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import androidx.lifecycle.ViewModel
import com.example.movies.R
import com.example.movies.domain.model.Country
import com.example.movies.domain.model.Genre
import com.example.movies.domain.model.Rating
import com.example.movies.domain.model.Review
import com.example.movies.domain.model.Trailer
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

@Composable
fun MovieDetailScreen(
    viewModel: ViewModel
) {

}

@OptIn(ExperimentalMaterial3Api::class, ExperimentalHazeMaterialsApi::class)
@Composable
fun MovieDetailScreenContent(
    name: String = "",
    type: String = "",
    year: String = "",
    description: String = "",
    length: String = "",
    ageRating: String = "",
    posterUrl: String = "",
    rating: Rating? = null,
    trailers: List<Trailer>? = null,
    genres: List<Genre>? = null,
    countries: List<Country>? = null,
    reviews: List<Review>? = null
) {
    val scrollBehavior = TopAppBarDefaults.pinnedScrollBehavior()
    val showAppBarContent by remember {
        derivedStateOf { scrollBehavior.state.contentOffset < -150 }
    }
    val backgroundColor by animateColorAsState(
        targetValue = if (showAppBarContent)
            MaterialTheme.colorScheme.surfaceContainer.copy(alpha = 0.5f)
        else
            Color.Transparent,
        animationSpec = tween(durationMillis = 300)
    )
    val blurIntensity by animateFloatAsState(
        targetValue = if (showAppBarContent) 1f else 0f,
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
                            startIntensity = 1f, endIntensity = 0f
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
                    IconButton(onClick = {}) {
                        Icon(
                            imageVector = Icons.AutoMirrored.Default.ArrowBack,
                            contentDescription = null,
                            tint = MaterialTheme.colorScheme.onSurfaceVariant
                        )
                    }
                }
            )
        }
    ) { innerPadding ->
        val scrollState = rememberScrollState()
        Image(
            modifier = Modifier
                .hazeSource(state = hazeState)
                .fillMaxWidth()
                .height(300.dp),
            painter = painterResource(R.drawable.movie_backdrop),
            contentDescription = null,
            contentScale = ContentScale.Crop
        )
        Box(
            modifier = Modifier
                .hazeSource(state = hazeState)
                .fillMaxWidth()
                .verticalScroll(state = scrollState)
                .padding(innerPadding)
                .padding(top = 100.dp)
                .background(
                    brush = Brush.verticalGradient(
                        colors = listOf(
                            Color.Transparent,
                            MaterialTheme.colorScheme.background
                        ),
                        startY = 70f,
                        endY = 180f
                    )
                )
        ) {
            Column(
                modifier = Modifier.padding(horizontal = 12.dp),
                verticalArrangement = Arrangement.spacedBy(12.dp)
            ) {
                MovieHeader(
                    modifier = Modifier.padding(start = 12.dp),
                    rating = rating,
                    year = year,
                    countries = countries,
                    length = length,
                    genres = genres
                )
                MovieDescription(
                    description = description
                )
                TrailersList(
                    trailers = trailers
                )
                ReviewsList(
                    reviews = reviews
                )
                for (i in 0..100) {
                    Text(text = "Hello, Movies!")
                }
            }
        }
    }
}

@Composable
private fun MovieHeader(
    modifier: Modifier = Modifier,
    rating: Rating? = null,
    year: String,
    countries: List<Country>? = null,
    length: String,
    genres: List<Genre>? = null
) {
    Column(
        modifier = modifier
    ) {
        Image(
            modifier = Modifier
                .height(80.dp)
                .padding(bottom = 8.dp),
            painter = painterResource(R.drawable.movie_logo),
            contentDescription = null
        )
        Row(
            horizontalArrangement = Arrangement.spacedBy(6.dp),
            verticalAlignment = Alignment.CenterVertically
        ) {
            if (rating != null) {
                KpRatingCard(rating = rating.kp)
                ImdbRatingCard(rating = rating.imdb)
            }
        }
        Row(
            horizontalArrangement = Arrangement.spacedBy(6.dp),
            verticalAlignment = Alignment.CenterVertically
        ) {
            Text(
                text = year,
                fontSize = 14.sp,
                fontWeight = FontWeight.Bold,
                color = Color(0xFF7E7E7E)
            )
            Text(
                text = countries?.joinToString() ?: "",
                fontSize = 14.sp,
                fontWeight = FontWeight.Bold,
                color = Color(0xFF7E7E7E)
            )
            Text(
                text = length,
                fontSize = 14.sp,
                fontWeight = FontWeight.Bold,
                color = Color(0xFF7E7E7E)
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
                color = Color(0xFF7E7E7E)
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
//            modifier = Modifier.padding(horizontal = 16.dp),
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
    trailers: List<Trailer>? = null
) {
    Column(
        modifier = modifier,
        verticalArrangement = Arrangement.spacedBy(4.dp)
    ) {
        Text(
//            modifier = Modifier.padding(horizontal = 16.dp),
            text = "Trailers",
            fontWeight = FontWeight.Bold,
            fontSize = 24.sp
        )
        LazyRow(
            horizontalArrangement = Arrangement.spacedBy(12.dp)
        ) {
            if (trailers != null) {
                items(trailers) {
                    TrailerCard(trailerUrl = it.url.toString())
                }
            }
        }
    }
}

@Composable
fun ReviewsList(
    modifier: Modifier = Modifier,
    reviews: List<Review>? = null
) {
    Column(
        modifier = modifier,
        verticalArrangement = Arrangement.spacedBy(4.dp)
    ) {
        Text(
//            modifier = Modifier.padding(horizontal = 16.dp),
            text = "Reviews",
            fontWeight = FontWeight.Bold,
            fontSize = 24.sp
        )
        LazyRow(
            horizontalArrangement = Arrangement.spacedBy(12.dp)
        ) {
            if (reviews != null) {
                items(reviews) {
                    ReviewCard(
                        title = it.title,
                        text = it.review,
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
}

@Preview(
    showSystemUi = true,
    uiMode = Configuration.UI_MODE_NIGHT_NO or Configuration.UI_MODE_TYPE_UNDEFINED
)
@Composable
private fun MovieDetailScreenPreview() {
    MoviesTheme {
        MovieDetailScreenContent(
            name = "Третий лишний",
            rating = Rating(kp = 4.5, imdb = 5.3),
            year = 2023.toString(),
            length = "1ч 23мин",
            genres = listOf(Genre(name = "Боевик"), Genre(name = "Драма")),
            countries = listOf(Country(name = "Россия"), Country(name = "США")),
            description = "Пострадав в результате несчастного случая, богатый аристократ Филипп " +
                    "нанимает в помощники человека, который менее всего подходит для этой работы, " +
                    "– молодого жителя предместья Дрисса, только что освободившегося из тюрьмы. " +
                    "Несмотря на то, что Филипп прикован к инвалидному креслу, Дриссу удается " +
                    "привнести в размеренную жизнь аристократа дух приключений.",
            posterUrl = "https://image.openmoviedb.com/tmdb-images/original/hvptcK6WmDqu4xZZ0WuBQ3vYInE.png",
            trailers = listOf(
                Trailer(),
                Trailer(),
                Trailer()
            ),
            reviews = listOf(
                Review(
                    id = 1,
                    movieId = 101,
                    title = "Оставил сильное впечатление",
                    review = "Фильм приятно удивил своей глубиной. Сюжет хорошо выстроен, а актёрская игра вызывает доверие. " +
                            "Особенно хочется отметить музыкальное сопровождение — оно добавляет атмосферы и усиливает эмоциональные сцены. \n" +
                            "Редкий случай, когда всё складывается в цельную картину, которую хочется пересмотреть.",
                    type = "Позитивный",
                    date = "6 июня, 2025",
                    author = "Егор Фомин",
                    userRating = 9,
                    likes = 132,
                    dislikes = 11
                ),
                Review(
                    id = 2,
                    movieId = 101,
                    title = "Обычное кино",
                    review = "Фильм получился довольно стандартным. Никаких откровений, но и провалов тоже нет. " +
                            "Сюжет предсказуемый, местами скучноват, но в целом смотрибельно. \n" +
                            "Можно глянуть под настроение, но в памяти не задержится надолго.",
                    type = "Нейтральный",
                    date = "10 июля, 2025",
                    author = "Лена Степанова",
                    userRating = 6,
                    likes = 64,
                    dislikes = 27
                ),
                Review(
                    id = 3,
                    movieId = 101,
                    title = "Норм, но не более",
                    review = "Кино как кино. Были хорошие сцены, но общая динамика провисает. " +
                            "Диалоги местами казались неестественными, персонажи недоработаны. \n" +
                            "Не жалею, что посмотрел, но второй раз вряд ли захочу.",
                    type = "Нейтральный",
                    date = "16 июля, 2025",
                    author = "Сергей Головин",
                    userRating = 5,
                    likes = 49,
                    dislikes = 32
                ),
                Review(
                    id = 4,
                    movieId = 101,
                    title = "Можно посмотреть",
                    review = "Если не ждать многого, фильм зайдёт. Простенький сюжет, пара удачных актёрских ролей, и в целом — ок. " +
                            "Визуально выглядит неплохо, но ощущение, что чего-то не хватает. \n" +
                            "Для вечернего просмотра подойдёт.",
                    type = "Нейтральный",
                    date = "21 июля, 2025",
                    author = "Марина Ветрова",
                    userRating = 6,
                    likes = 58,
                    dislikes = 19
                ),
                Review(
                    id = 5,
                    movieId = 101,
                    title = "Разочарование",
                    review = "Слишком затянуто и местами откровенно скучно. Ожидания были выше — трейлер обещал больше, чем дал сам фильм. \n" +
                            "Актёры старались, но слабый сценарий всё испортил. После просмотра остаётся только недоумение и сожаление о потраченном времени.",
                    type = "Негативный",
                    date = "29 июля, 2025",
                    author = "Кирилл Орлов",
                    userRating = 3,
                    likes = 22,
                    dislikes = 67
                )
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
            name = "Третий лишний",
            rating = Rating(kp = 4.5, imdb = 5.3),
            year = 2023.toString(),
            length = "1ч 23мин",
            genres = listOf(Genre(name = "Боевик"), Genre(name = "Драма")),
            countries = listOf(Country(name = "Россия"), Country(name = "США")),
            description = "Пострадав в результате несчастного случая, богатый аристократ Филипп " +
                    "нанимает в помощники человека, который менее всего подходит для этой работы, " +
                    "– молодого жителя предместья Дрисса, только что освободившегося из тюрьмы. " +
                    "Несмотря на то, что Филипп прикован к инвалидному креслу, Дриссу удается " +
                    "привнести в размеренную жизнь аристократа дух приключений.",
            posterUrl = "https://image.openmoviedb.com/tmdb-images/original/hvptcK6WmDqu4xZZ0WuBQ3vYInE.png",
            trailers = listOf(
                Trailer(),
                Trailer(),
                Trailer()
            ),
            reviews = listOf(
                Review(
                    id = 1,
                    movieId = 101,
                    title = "Оставил сильное впечатление",
                    review = "Фильм приятно удивил своей глубиной. Сюжет хорошо выстроен, а актёрская игра вызывает доверие. " +
                            "Особенно хочется отметить музыкальное сопровождение — оно добавляет атмосферы и усиливает эмоциональные сцены. \n" +
                            "Редкий случай, когда всё складывается в цельную картину, которую хочется пересмотреть.",
                    type = "Позитивный",
                    date = "6 июня, 2025",
                    author = "Егор Фомин",
                    userRating = 9,
                    likes = 132,
                    dislikes = 11
                ),
                Review(
                    id = 2,
                    movieId = 101,
                    title = "Обычное кино",
                    review = "Фильм получился довольно стандартным. Никаких откровений, но и провалов тоже нет. " +
                            "Сюжет предсказуемый, местами скучноват, но в целом смотрибельно. \n" +
                            "Можно глянуть под настроение, но в памяти не задержится надолго.",
                    type = "Нейтральный",
                    date = "10 июля, 2025",
                    author = "Лена Степанова",
                    userRating = 6,
                    likes = 64,
                    dislikes = 27
                ),
                Review(
                    id = 3,
                    movieId = 101,
                    title = "Норм, но не более",
                    review = "Кино как кино. Были хорошие сцены, но общая динамика провисает. " +
                            "Диалоги местами казались неестественными, персонажи недоработаны. \n" +
                            "Не жалею, что посмотрел, но второй раз вряд ли захочу.",
                    type = "Нейтральный",
                    date = "16 июля, 2025",
                    author = "Сергей Головин",
                    userRating = 5,
                    likes = 49,
                    dislikes = 32
                ),
                Review(
                    id = 4,
                    movieId = 101,
                    title = "Можно посмотреть",
                    review = "Если не ждать многого, фильм зайдёт. Простенький сюжет, пара удачных актёрских ролей, и в целом — ок. " +
                            "Визуально выглядит неплохо, но ощущение, что чего-то не хватает. \n" +
                            "Для вечернего просмотра подойдёт.",
                    type = "Нейтральный",
                    date = "21 июля, 2025",
                    author = "Марина Ветрова",
                    userRating = 6,
                    likes = 58,
                    dislikes = 19
                ),
                Review(
                    id = 5,
                    movieId = 101,
                    title = "Разочарование",
                    review = "Слишком затянуто и местами откровенно скучно. Ожидания были выше — трейлер обещал больше, чем дал сам фильм. \n" +
                            "Актёры старались, но слабый сценарий всё испортил. После просмотра остаётся только недоумение и сожаление о потраченном времени.",
                    type = "Негативный",
                    date = "29 июля, 2025",
                    author = "Кирилл Орлов",
                    userRating = 3,
                    likes = 22,
                    dislikes = 67
                )
            )
        )
    }
}
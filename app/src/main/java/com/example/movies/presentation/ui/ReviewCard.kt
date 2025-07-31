package com.example.movies.presentation.ui

import androidx.compose.foundation.background
import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.PaddingValues
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.Spacer
import androidx.compose.foundation.layout.fillMaxHeight
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.size
import androidx.compose.foundation.layout.width
import androidx.compose.foundation.lazy.LazyRow
import androidx.compose.foundation.lazy.items
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.filled.KeyboardArrowDown
import androidx.compose.material.icons.filled.KeyboardArrowUp
import androidx.compose.material3.Card
import androidx.compose.material3.CardDefaults
import androidx.compose.material3.HorizontalDivider
import androidx.compose.material3.Icon
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.Text
import androidx.compose.material3.TextButton
import androidx.compose.runtime.Composable
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.focus.focusModifier
import androidx.compose.ui.graphics.Brush
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.text.style.TextOverflow
import androidx.compose.ui.tooling.preview.Preview
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import com.example.movies.domain.model.Review
import com.example.movies.presentation.ui.theme.MoviesTheme

@Composable
fun ReviewCard(
    modifier: Modifier = Modifier,
    title: String,
    text: String,
    date: String,
    author: String,
    type: String,
    likes: Int,
    dislikes: Int,
    onClick: () -> Unit = {}
) {
    Card(
        modifier = modifier
            .size(width = 300.dp, height = 260.dp),
        onClick = onClick
    ) {
        Column(
            modifier = Modifier
                .fillMaxSize()
                .background(
                    brush = Brush.verticalGradient(
                        listOf(
                            MaterialTheme.colorScheme.surfaceContainerLow,
                            when (type) {
                                "Негативный" -> Color(0xFFFCDEDE)
                                "Позитивный" -> Color(0xFFDEFCE0)
                                else -> MaterialTheme.colorScheme.surfaceContainerLow
                            }
                        ),
                        startY = 80f,
                        endY = 700f
                    )
                )
                .padding(start = 12.dp, end = 12.dp, top = 12.dp, bottom = 4.dp),
        ) {
            Row {
                Column(modifier = Modifier.weight(1f)) {
                    Text(
                        text = title,
                        fontSize = 20.sp,
                        maxLines = 2,
                        overflow = TextOverflow.Ellipsis
                    )
                    Text(
                        text = "$author • $date",
                        fontSize = 12.sp
                    )
                }
                Row(verticalAlignment = Alignment.CenterVertically) {
                    Icon(
                        modifier = Modifier.size(20.dp),
                        imageVector = Icons.Default.KeyboardArrowUp,
                        contentDescription = null
                    )
                    Text(text = likes.toString(), fontSize = 12.sp)
                }
                Row(verticalAlignment = Alignment.CenterVertically) {
                    Icon(
                        modifier = Modifier.size(20.dp),
                        imageVector = Icons.Default.KeyboardArrowDown,
                        contentDescription = null
                    )
                    Text(text = dislikes.toString(), fontSize = 12.sp)
                }
            }
            Spacer(modifier = Modifier.height(12.dp))
            Text(
                modifier = Modifier.weight(1f),
                text = text,
                fontSize = 14.sp,
                lineHeight = 18.sp,
                overflow = TextOverflow.Ellipsis,
                maxLines = 7
            )
            TextButton(
                modifier = Modifier.align(Alignment.CenterHorizontally),
                onClick = onClick
            ) {
                Text(text = "Подробнее")
            }
        }
    }
}

@Preview
@Composable
private fun ReviewCardPositivePreview() {
    MoviesTheme {
        ReviewCard(
            title = "Хороший фильм на самом деле, ха наебал",
            text = "Фильм приятно удивил своей глубиной и вниманием к деталям. " +
                    "Сюжет развивается плавно, но при этом держит в напряжении, " +
                    "не давая отвлечься ни на минуту. Особенно хочется отметить игру " +
                    "актёров — она естественная, правдивая и очень эмоциональная. \nОтличная работа " +
                    "режиссёра, которому удалось создать атмосферу, в которой хочется остаться " +
                    "подольше. После просмотра остаётся чувство завершённости и лёгкой тоски, как " +
                    "после чего-то по-настоящему стоящего.",
            date = "6 июня, 2025",
            author = "Egor Fomin",
            type = "Позитивный",
            likes = 124,
            dislikes = 43
        )
    }
}

@Preview
@Composable
private fun ReviewCardNeutralPreview() {
    MoviesTheme {
        ReviewCard(
            title = "Хороший фильм",
            text = "Фильм приятно удивил своей глубиной и вниманием к деталям. " +
                    "Сюжет развивается плавно, но при этом держит в напряжении, " +
                    "не давая отвлечься ни на минуту. Особенно хочется отметить игру " +
                    "актёров — она естественная, правдивая и очень эмоциональная. \nОтличная работа " +
                    "режиссёра, которому удалось создать атмосферу, в которой хочется остаться " +
                    "подольше. После просмотра остаётся чувство завершённости и лёгкой тоски, как " +
                    "после чего-то по-настоящему стоящего.",
            date = "6 июня, 2025",
            author = "Egor Fomin",
            type = "Нейтральный",
            likes = 124,
            dislikes = 43
        )
    }
}

@Preview
@Composable
private fun ReviewCardNegativePreview() {
    MoviesTheme {
        ReviewCard(
            title = "Хороший фильм",
            text = "Фильм приятно удивил своей глубиной и вниманием к деталям. " +
                    "Сюжет развивается плавно, но при этом держит в напряжении, " +
                    "не давая отвлечься ни на минуту. Особенно хочется отметить игру " +
                    "актёров — она естественная, правдивая и очень эмоциональная. \nОтличная работа " +
                    "режиссёра, которому удалось создать атмосферу, в которой хочется остаться " +
                    "подольше. После просмотра остаётся чувство завершённости и лёгкой тоски, как " +
                    "после чего-то по-настоящему стоящего.",
            date = "6 июня, 2025",
            author = "Egor Fomin",
            type = "Негативный",
            likes = 124,
            dislikes = 43
        )
    }
}

@Preview
@Composable
private fun ListOfReviewCard() {
    val fakeReviews = listOf(
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
    MoviesTheme {
        LazyRow(
            contentPadding = PaddingValues(8.dp),
            horizontalArrangement = Arrangement.spacedBy(8.dp)
        ) {
            items(fakeReviews) {
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
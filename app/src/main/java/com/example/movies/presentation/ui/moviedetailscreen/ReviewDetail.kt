package com.example.movies.presentation.ui.moviedetailscreen

import androidx.compose.foundation.background
import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.Spacer
import androidx.compose.foundation.layout.WindowInsets
import androidx.compose.foundation.layout.asPaddingValues
import androidx.compose.foundation.layout.fillMaxHeight
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.navigationBars
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.safeContent
import androidx.compose.foundation.layout.size
import androidx.compose.foundation.layout.statusBars
import androidx.compose.foundation.layout.statusBarsPadding
import androidx.compose.foundation.layout.systemBars
import androidx.compose.foundation.layout.systemBarsPadding
import androidx.compose.foundation.layout.width
import androidx.compose.foundation.rememberScrollState
import androidx.compose.foundation.shape.CornerSize
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.foundation.verticalScroll
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.filled.KeyboardArrowDown
import androidx.compose.material.icons.filled.KeyboardArrowUp
import androidx.compose.material3.BottomSheetDefaults
import androidx.compose.material3.ExperimentalMaterial3Api
import androidx.compose.material3.Icon
import androidx.compose.material3.ModalBottomSheet
import androidx.compose.material3.SheetState
import androidx.compose.material3.Text
import androidx.compose.material3.rememberModalBottomSheetState
import androidx.compose.runtime.Composable
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.draw.clip
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.platform.LocalDensity
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.text.style.TextOverflow
import androidx.compose.ui.tooling.preview.Preview
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import com.example.movies.domain.model.Review
import com.example.movies.domain.model.ReviewType
import com.example.movies.presentation.ui.theme.MoviesTheme

@OptIn(ExperimentalMaterial3Api::class)
@Composable
fun ReviewDetailSheet(
    modifier: Modifier = Modifier,
    onDismissRequest: () -> Unit,
    state: SheetState = rememberModalBottomSheetState(),
    title: String,
    text: String,
    type: ReviewType,
    date: String,
    author: String,
    authorRating: Int,
    likes: Int,
    dislikes: Int
) {
    ModalBottomSheet(
        modifier = modifier
            .statusBarsPadding(),
        onDismissRequest = onDismissRequest,
        sheetState = state,
        contentWindowInsets = {
            WindowInsets()
        }
        // TODO Maybe add paddingValues as contentWindowInsets
    ) {
        ReviewDetailContent(
            modifier = Modifier
                .verticalScroll(rememberScrollState())
                .padding(
                    bottom = WindowInsets.navigationBars.asPaddingValues().calculateBottomPadding()
                ),
            title = title,
            text = text,
            type = type,
            date = date,
            author = author,
            authorRating = authorRating,
            likes = likes,
            dislikes = dislikes
        )
    }
}

@Composable
private fun ReviewDetailContent(
    modifier: Modifier = Modifier,
    title: String,
    text: String,
    type: ReviewType,
    date: String,
    author: String,
    authorRating: Int,
    likes: Int,
    dislikes: Int
) {
    Column(
        modifier = modifier
            .padding(horizontal = 12.dp)
            .fillMaxSize()
    ) {
        Row(
            modifier = Modifier.fillMaxWidth(),
            horizontalArrangement = Arrangement.spacedBy(4.dp),
            verticalAlignment = Alignment.CenterVertically
        ) {
            Column(
                modifier = Modifier.weight(1f)
            ) {
                Row(
                    horizontalArrangement = Arrangement.spacedBy(4.dp)
                ) {
                    Text(
                        text = author,
                        fontSize = 18.sp
                    )
                    Box(
                        modifier = Modifier
                            .clip(RoundedCornerShape(4.dp))
                            .background(Color(0xFFC4C4C4))
                            .padding(horizontal = 8.dp),
                        contentAlignment = Alignment.Center
                    ) {
                        Text(
                            text = authorRating.toString(),
                            fontWeight = FontWeight.Bold
                        )
                    }
                }
                Text(
                    text = date
                )
            }
            Row(
                horizontalArrangement = Arrangement.End
            ) {
                Row(verticalAlignment = Alignment.CenterVertically) {
                    Icon(
                        imageVector = Icons.Default.KeyboardArrowUp,
                        contentDescription = null
                    )
                    Text(text = likes.toString())
                }
                Row(verticalAlignment = Alignment.CenterVertically) {
                    Icon(
                        imageVector = Icons.Default.KeyboardArrowDown,
                        contentDescription = null
                    )
                    Text(text = dislikes.toString())
                }
            }
        }
        Spacer(modifier = Modifier.height(24.dp))
        Column(
            verticalArrangement = Arrangement.spacedBy(4.dp)
        ) {
            Text(
                text = title,
                fontSize = 26.sp
            )
            Text(
                text = text
            )
        }
    }
}

@OptIn(ExperimentalMaterial3Api::class)
@Preview(showSystemUi = false, showBackground = false)
@Composable
private fun ReviewDetailSheetPreview() {
    val exampleReview = Review(
        id = 3,
        movieId = 101,
        title = "Норм, но не более",
        text = "Кино как кино. Были хорошие сцены, но общая динамика провисает. " +
                "Диалоги местами казались неестественными, персонажи недоработаны. \n" +
                "Не жалею, что посмотрел, но второй раз вряд ли захочу.",
        type = ReviewType.NEUTRAL,
        date = "16 июля, 2025",
        author = "Сергей Головин",
        authorRating = 10,
        likes = 49,
        dislikes = 32
    )
    MoviesTheme {
        ReviewDetailContent(
            modifier = Modifier
                .padding(WindowInsets.systemBars.asPaddingValues()),
            title = exampleReview.title,
            text = exampleReview.text,
            type = exampleReview.type,
            date = exampleReview.date,
            author = exampleReview.author,
            authorRating = exampleReview.authorRating,
            likes = exampleReview.likes,
            dislikes = exampleReview.dislikes
        )
    }
}
package com.example.movies.presentation.ui

import androidx.compose.animation.AnimatedVisibility
import androidx.compose.foundation.lazy.LazyColumn
import androidx.compose.foundation.lazy.items
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.automirrored.filled.ArrowBack
import androidx.compose.material3.ExperimentalMaterial3Api
import androidx.compose.material3.Icon
import androidx.compose.material3.IconButton
import androidx.compose.material3.Scaffold
import androidx.compose.material3.Text
import androidx.compose.material3.TopAppBar
import androidx.compose.material3.TopAppBarDefaults
import androidx.compose.material3.rememberTopAppBarState
import androidx.compose.runtime.Composable
import androidx.compose.runtime.derivedStateOf
import androidx.compose.runtime.getValue
import androidx.compose.runtime.remember
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.input.nestedscroll.nestedScroll
import androidx.compose.ui.tooling.preview.Preview
import com.example.movies.presentation.ui.theme.MoviesTheme

@OptIn(ExperimentalMaterial3Api::class)
@Composable
fun Meme() {
    val scrollBehavior = TopAppBarDefaults.pinnedScrollBehavior(rememberTopAppBarState())
    val showTitle by remember {
        derivedStateOf { scrollBehavior.state.contentOffset < 0 }
    }

    Scaffold(
        modifier = Modifier.nestedScroll(connection = scrollBehavior.nestedScrollConnection),
        topBar = {
            TopAppBar(
                title = {
                    AnimatedVisibility(showTitle) {
                        Text(text = "Title")
                    }
                },
                navigationIcon = {
                    IconButton(onClick = {}) {
                        Icon(
                            imageVector = Icons.AutoMirrored.Default.ArrowBack,
                            contentDescription = null
                        )
                    }
                },
                scrollBehavior = scrollBehavior
            )
        }
    ) { innerPadding ->
        LazyColumn(
            modifier = Modifier.nestedScroll(connection = scrollBehavior.nestedScrollConnection),
            contentPadding = innerPadding
        ) {
            items(50) {
                Text(text = "Text $it")
            }
        }
    }
}

@Preview(showSystemUi = true)
@Composable
private fun MemePreview() {
    MoviesTheme { Meme() }
}
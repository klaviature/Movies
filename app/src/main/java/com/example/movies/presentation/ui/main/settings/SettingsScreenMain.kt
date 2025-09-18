package com.example.movies.presentation.ui.main.settings

import android.content.res.Configuration
import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.PaddingValues
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.size
import androidx.compose.foundation.rememberScrollState
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.foundation.verticalScroll
import androidx.compose.material3.Card
import androidx.compose.material3.ExperimentalMaterial3Api
import androidx.compose.material3.Icon
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.MediumTopAppBar
import androidx.compose.material3.Scaffold
import androidx.compose.material3.Text
import androidx.compose.material3.TopAppBarDefaults
import androidx.compose.material3.rememberTopAppBarState
import androidx.compose.runtime.Composable
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.vector.ImageVector
import androidx.compose.ui.input.nestedscroll.nestedScroll
import androidx.compose.ui.res.vectorResource
import androidx.compose.ui.text.style.TextOverflow
import androidx.compose.ui.tooling.preview.Preview
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import androidx.navigation.NavHostController
import androidx.navigation.compose.rememberNavController
import com.example.movies.presentation.ui.main.AppIcon
import com.example.movies.presentation.ui.theme.MoviesTheme

@OptIn(ExperimentalMaterial3Api::class)
@Composable
fun SettingsScreenMain(
    navController: NavHostController,
    modifier: Modifier = Modifier,
    contentPadding: PaddingValues = PaddingValues(0.dp),
) {
    val scrollBehavior = TopAppBarDefaults.enterAlwaysScrollBehavior(rememberTopAppBarState())

    Scaffold(
        modifier = Modifier.nestedScroll(scrollBehavior.nestedScrollConnection),
        topBar = {
            MediumTopAppBar(
                title = {
                    Text(text = "Настройки", fontSize = 28.sp)
                },
                scrollBehavior = scrollBehavior,
                navigationIcon = {
//                    IconButton(onClick = onBackPressed) {
//                        Icon(
//                            imageVector = Icons.AutoMirrored.Filled.ArrowBack,
//                            contentDescription = null
//                        )
//                    }
                }
            )
        }
    ) { innerPadding ->
        Column(
            modifier = modifier
                .verticalScroll(rememberScrollState())
                .padding(innerPadding)
                .padding(contentPadding)
                .padding(horizontal = 8.dp)
                .fillMaxSize(),
            verticalArrangement = Arrangement.spacedBy(16.dp)
        ) {
            SettingsGroup.entries.forEachIndexed { groupIndex, group ->
                Column(
                    modifier = Modifier.fillMaxWidth(),
                    verticalArrangement = Arrangement.spacedBy(4.dp)
                ) {
                    if (group.title != null) {
                        Text(
                            modifier = Modifier.padding(start = 16.dp),
                            text = group.title,
                            color = MaterialTheme.colorScheme.onSurfaceVariant
                        )
                    }
                    group.items.forEachIndexed { index, item ->
                        Card(
                            modifier = Modifier
                                .fillMaxWidth(),
                            shape = when (index) {
                                0 -> RoundedCornerShape(
                                    topStart = 16.dp,
                                    topEnd = 16.dp,
                                    bottomStart = if (group.items.size == 1) {
                                        16.dp
                                    } else {
                                        4.dp
                                    },
                                    bottomEnd = if (group.items.size == 1) {
                                        16.dp
                                    } else {
                                        4.dp
                                    }
                                )

                                group.items.lastIndex -> RoundedCornerShape(
                                    topStart = 4.dp,
                                    topEnd = 4.dp,
                                    bottomStart = 16.dp,
                                    bottomEnd = 16.dp
                                )

                                else -> RoundedCornerShape(4.dp)
                            },
                            onClick = {
                                navController.navigate(item.route)
                            }
                        ) {
                            Row(
                                modifier = Modifier
                                    .fillMaxSize()
                                    .padding(horizontal = 11.dp, vertical = 16.dp),
                                verticalAlignment = Alignment.CenterVertically,
                                horizontalArrangement = Arrangement.spacedBy(11.dp)
                            ) {
                                if (item.icon != null) {
                                    Icon(
                                        modifier = Modifier
                                            .size(24.dp),
                                        imageVector = when (item.icon) {
                                            is AppIcon.Resource -> ImageVector.vectorResource(item.icon.resId)
                                            is AppIcon.Vector -> item.icon.imageVector
                                        },
                                        contentDescription = null
                                    )
                                }
                                Column(
                                    modifier = Modifier
                                        .weight(1f)
                                ) {
                                    Text(text = item.title)
                                    if (item.description != null) {
                                        Text(
                                            text = item.description,
                                            maxLines = 2,
                                            overflow = TextOverflow.Ellipsis,
                                            fontSize = 14.sp,
                                            lineHeight = 16.sp,
                                            color = MaterialTheme.colorScheme.onSurfaceVariant
                                        )
                                    }
                                }
                            }
                        }
                    }
                }
            }
        }
    }
}

@Preview
@Composable
private fun SettingsScreenMainPreview() {
    MoviesTheme {
        SettingsScreenMain(
            navController = rememberNavController()
        )
    }
}

@Preview(uiMode = Configuration.UI_MODE_NIGHT_YES or Configuration.UI_MODE_TYPE_NORMAL)
@Composable
private fun SettingScreenMainPreviewDarkTheme() {
    MoviesTheme {
        SettingsScreenMain(
            navController = rememberNavController()
        )
    }
}
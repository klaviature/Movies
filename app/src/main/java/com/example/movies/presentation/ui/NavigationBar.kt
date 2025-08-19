package com.example.movies.presentation.ui

import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.filled.CheckCircle
import androidx.compose.material.icons.filled.Favorite
import androidx.compose.material.icons.filled.Home
import androidx.compose.material.icons.outlined.CheckCircle
import androidx.compose.material.icons.outlined.FavoriteBorder
import androidx.compose.material.icons.outlined.Home
import androidx.compose.material3.Icon
import androidx.compose.material3.NavigationBar
import androidx.compose.material3.NavigationBarDefaults
import androidx.compose.material3.NavigationBarItem
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableIntStateOf
import androidx.compose.runtime.remember
import androidx.compose.runtime.setValue
import androidx.compose.ui.Modifier
import androidx.compose.ui.tooling.preview.Preview
import com.example.movies.presentation.ui.theme.MoviesTheme
import dev.chrisbanes.haze.HazeState
import dev.chrisbanes.haze.hazeEffect
import dev.chrisbanes.haze.materials.HazeMaterials

@Composable
fun MyNavigationBar(
    hazeState: HazeState? = null
) {
    var selectedItem by remember { mutableIntStateOf(0) }
    val items = listOf("Главная", "Избранное", "Просмотренное")
    val selectedIcons =
        listOf(Icons.Filled.Home, Icons.Filled.Favorite, Icons.Filled.CheckCircle)
    val unselectedIcons =
        listOf(Icons.Outlined.Home, Icons.Outlined.FavoriteBorder, Icons.Outlined.CheckCircle)

    NavigationBar(
        containerColor = NavigationBarDefaults.containerColor.copy(alpha = 0.5f),
        modifier = Modifier
            .hazeEffect(state = hazeState, style = HazeMaterials.ultraThin())
    ) {
        items.forEachIndexed { index, item ->
            NavigationBarItem(
                icon = {
                    Icon(
                        if (selectedItem == index) selectedIcons[index] else unselectedIcons[index],
                        contentDescription = item
                    )
                },
                label = { Text(item) },
                selected = selectedItem == index,
                onClick = { selectedItem = index }
            )
        }
    }
}

@Composable
@Preview(showBackground = true)
fun NavigationBarPreview() {
    MoviesTheme {
        MyNavigationBar()
    }
}
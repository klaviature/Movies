package com.example.movies.presentation.ui.main.settings

import androidx.compose.foundation.layout.PaddingValues
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.filled.Info
import androidx.compose.material3.ExperimentalMaterial3Api
import androidx.compose.material3.SnackbarHostState
import androidx.compose.runtime.Composable
import androidx.compose.runtime.remember
import androidx.compose.ui.Modifier
import androidx.compose.ui.tooling.preview.Preview
import androidx.compose.ui.tooling.preview.Wallpapers
import androidx.compose.ui.unit.dp
import androidx.navigation.compose.NavHost
import androidx.navigation.compose.composable
import androidx.navigation.compose.rememberNavController
import com.example.movies.R
import com.example.movies.presentation.ui.main.AppIcon
import com.example.movies.presentation.ui.main.settings.api.ApiSettingsScreen
import com.example.movies.presentation.ui.main.settings.theme.ThemeSettingsScreen
import com.example.movies.presentation.ui.theme.MoviesTheme
import kotlinx.serialization.Serializable

@Serializable
sealed interface SettingsNavGraph {
    @Serializable
    data object Main : SettingsNavGraph

    @Serializable
    data object Api : SettingsNavGraph

    @Serializable
    data object Personalization : SettingsNavGraph

    @Serializable
    data object Storage : SettingsNavGraph

    @Serializable
    data object UserManual : SettingsNavGraph

    @Serializable
    data object About : SettingsNavGraph
}

enum class SettingsGroup(
    val title: String?,
    val items: List<SettingsItem>
) {
    GENERAL("Общие", listOf(SettingsItem.API, SettingsItem.PERSONALIZATION)),
    DATA_AND_STORAGE("Данные и хранилище", listOf(SettingsItem.STORAGE)),
    HELP_AND_INFO("Помощь и информация", listOf(SettingsItem.USER_MANUAL, SettingsItem.ABOUT))
}

enum class SettingsItem(
    val title: String,
    val description: String? = null,
    val icon: AppIcon? = null,
    val route: SettingsNavGraph
) {
    API(
        title = "Настройка API",
        description = "Ключ для получения данных с сервера, проверка ключа",
        icon = AppIcon.Resource(R.drawable.globe_icon),
        route = SettingsNavGraph.Api
    ),
    PERSONALIZATION(
        title = "Оформление",
        description = "Персонализация внешнего вида приложения",
        icon = AppIcon.Resource(R.drawable.palette_filled),
        route = SettingsNavGraph.Personalization
    ),
    STORAGE(
        title = "Управление памятью",
        description = "Информация о занимаемом пространстве и очистка кэша",
        icon = AppIcon.Resource(R.drawable.database_filled),
        route = SettingsNavGraph.Storage
    ),
    USER_MANUAL(
        title = "Руководство пользователя",
        description = "Как пользоваться приложением",
        icon = AppIcon.Resource(R.drawable.help_filled),
        route = SettingsNavGraph.UserManual
    ),
    ABOUT(
        title = "О приложении",
        description = "Версия, разработчик и другая информация",
        icon = AppIcon.Vector(Icons.Filled.Info),
        route = SettingsNavGraph.About
    )
}

@OptIn(ExperimentalMaterial3Api::class)
@Composable
fun SettingsScreen(
    modifier: Modifier = Modifier,
    contentPadding: PaddingValues = PaddingValues(0.dp),
    snackbarHostState: SnackbarHostState = remember { SnackbarHostState() }
) {
    val navController = rememberNavController()
    val startDestination = SettingsNavGraph.Main

    NavHost(
        navController = navController,
        startDestination = startDestination,
        modifier = modifier
    ) {
        composable<SettingsNavGraph.Main> {
            SettingsScreenMain(
                navController = navController,
                contentPadding = contentPadding
            )
        }
        composable<SettingsNavGraph.Api> {
            ApiSettingsScreen(
                navController = navController,
                contentPadding = contentPadding,
                snackbarHostState = snackbarHostState
            )
        }
        composable<SettingsNavGraph.Personalization> {
            ThemeSettingsScreen(
                navController = navController,
                contentPadding = contentPadding
            )
        }
        composable<SettingsNavGraph.Storage> {

        }
        composable<SettingsNavGraph.UserManual> {

        }
        composable<SettingsNavGraph.About> {

        }
    }
}

@Preview(
    device = "id:pixel_6a", showSystemUi = true,
    wallpaper = Wallpapers.YELLOW_DOMINATED_EXAMPLE
)
@Composable
private fun SettingsScreenPreview() {
    MoviesTheme {
        SettingsScreen()
    }
}























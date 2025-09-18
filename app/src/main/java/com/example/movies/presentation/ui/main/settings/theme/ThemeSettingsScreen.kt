package com.example.movies.presentation.ui.main.settings.theme

import androidx.compose.foundation.clickable
import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.PaddingValues
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.rememberScrollState
import androidx.compose.foundation.verticalScroll
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.automirrored.filled.ArrowBack
import androidx.compose.material.icons.filled.Check
import androidx.compose.material3.AlertDialog
import androidx.compose.material3.BasicAlertDialog
import androidx.compose.material3.ExperimentalMaterial3Api
import androidx.compose.material3.Icon
import androidx.compose.material3.IconButton
import androidx.compose.material3.MediumTopAppBar
import androidx.compose.material3.RadioButton
import androidx.compose.material3.Scaffold
import androidx.compose.material3.Switch
import androidx.compose.material3.Text
import androidx.compose.material3.TopAppBarDefaults
import androidx.compose.material3.rememberTopAppBarState
import androidx.compose.runtime.Composable
import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.remember
import androidx.compose.runtime.setValue
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.input.nestedscroll.nestedScroll
import androidx.compose.ui.tooling.preview.Preview
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import androidx.compose.ui.window.Dialog
import androidx.compose.ui.window.DialogProperties
import androidx.hilt.navigation.compose.hiltViewModel
import androidx.lifecycle.compose.collectAsStateWithLifecycle
import androidx.navigation.NavHostController
import com.example.movies.domain.model.ThemeMode
import com.example.movies.presentation.ui.theme.MoviesTheme

@Composable
fun ThemeSettingsScreen(
    navController: NavHostController,
    modifier: Modifier = Modifier,
    contentPadding: PaddingValues = PaddingValues(0.dp),
    viewModel: ThemeSettingsViewModel = hiltViewModel()
) {
    val state by viewModel.settings.collectAsStateWithLifecycle()

    ThemeSettingsScreenContent(
        contentPadding = contentPadding,
        state = state,
        onBackPressed = {
            navController.popBackStack()
        },
        onThemeModeChange = { themeMode ->
            viewModel.setThemeMode(themeMode)
        },
        onDynamicColorChange = { isDynamicColor ->
            viewModel.setDynamicColorPreference(isDynamicColor)
        }
    )
}

@OptIn(ExperimentalMaterial3Api::class)
@Composable
fun ThemeSettingsScreenContent(
    modifier: Modifier = Modifier,
    contentPadding: PaddingValues = PaddingValues(0.dp),
    onBackPressed: () -> Unit,
    state: ThemeSettingsUiState,
    onThemeModeChange: (ThemeMode) -> Unit = {},
    onDynamicColorChange: (Boolean) -> Unit = {}
) {
    val scrollBehavior = TopAppBarDefaults.enterAlwaysScrollBehavior(rememberTopAppBarState())

    var showThemeModeDialog by remember { mutableStateOf(false) }

    Scaffold(
        modifier = Modifier.nestedScroll(scrollBehavior.nestedScrollConnection),
        topBar = {
            MediumTopAppBar(
                title = {
                    Text(text = "Оформление")
                },
                scrollBehavior = scrollBehavior,
                navigationIcon = {
                    IconButton(onClick = onBackPressed) {
                        Icon(
                            imageVector = Icons.AutoMirrored.Filled.ArrowBack,
                            contentDescription = null
                        )
                    }
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
            Column(
                modifier = Modifier
                    .clickable {
                        showThemeModeDialog = true
                    }
                    .fillMaxWidth()
            ) {
                Text(text = "Тема")
                Text(text = state.themeMode.name)
            }
            Row {
                Text(text = "Динамические цвета")
                Switch(
                    checked = state.isDynamicColorEnabled,
                    onCheckedChange = onDynamicColorChange,
                    thumbContent = {
                        if (state.isDynamicColorEnabled) {
                            Icon(
                                imageVector = Icons.Default.Check,
                                contentDescription = null
                            )
                        }
                    }
                )
            }
        }
    }

    if (showThemeModeDialog) {
        ThemeModeDialog(
            onDismissRequest = {
                showThemeModeDialog = false
            },
            onThemeModeChange = {
                onThemeModeChange(it)
                showThemeModeDialog = false
            },
            currentThemeMode = state.themeMode,
            themeModes = ThemeMode.entries
        )
    }
}

@Preview
@Composable
private fun ThemeSettingsScreenPreview() {
    var state by remember {
        mutableStateOf(
            ThemeSettingsUiState(
                themeMode = ThemeMode.SYSTEM,
                isDynamicColorEnabled = true
            )
        )
    }
    MoviesTheme {
        ThemeSettingsScreenContent(
            state = state,
            onBackPressed = {},
            onThemeModeChange = {
                println(it)
                state = state.copy(themeMode = it)
            },
            onDynamicColorChange = {
                println(it)
                state = state.copy(isDynamicColorEnabled = it)
            }
        )
    }
}

@OptIn(ExperimentalMaterial3Api::class)
@Composable
fun ThemeModeDialog(
    onDismissRequest: () -> Unit = {},
    onThemeModeChange: (ThemeMode) -> Unit = {},
    currentThemeMode: ThemeMode,
    themeModes: List<ThemeMode>
) {
    var chosenThemeMode by remember { mutableStateOf(currentThemeMode) }
    AlertDialog(
        onDismissRequest = onDismissRequest,
        confirmButton = {
            Text(
                modifier = Modifier
                    .clickable {
                        onThemeModeChange(chosenThemeMode)
                    },
                text = "Ок"
            )
        },
        dismissButton = {
            Text(
                modifier = Modifier
                    .clickable {
                        onDismissRequest()
                    },
                text = "Отмена"
            )
        },
        title = {
            Text("Выбор темы")
        },
        text = {
            Column(
                modifier = Modifier
                    .fillMaxWidth()
            ) {
                themeModes.forEach { themeMode ->
                    Row(
                        modifier = Modifier
                            .clickable {
                                chosenThemeMode = themeMode
                            }
                            .fillMaxWidth(),
                        verticalAlignment = Alignment.CenterVertically
                    ) {
                        RadioButton(
                            selected = themeMode == chosenThemeMode,
                            onClick = {
                                chosenThemeMode = themeMode
                            }
                        )
                        Text(
                            text = themeMode.name
                        )
                    }
                }
            }
        }
    )
}

@Preview(showSystemUi = false, showBackground = false)
@Composable
private fun ThemeModeDialogPreview() {
    MoviesTheme {
        ThemeModeDialog(
            onDismissRequest = {},
            onThemeModeChange = {},
            currentThemeMode = ThemeMode.DARK,
            themeModes = ThemeMode.entries
        )
    }
}
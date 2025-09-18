package com.example.movies.presentation.ui.main.settings.api

import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.PaddingValues
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.size
import androidx.compose.foundation.progressSemantics
import androidx.compose.foundation.rememberScrollState
import androidx.compose.foundation.verticalScroll
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.automirrored.filled.ArrowBack
import androidx.compose.material3.Button
import androidx.compose.material3.CircularProgressIndicator
import androidx.compose.material3.ExperimentalMaterial3Api
import androidx.compose.material3.Icon
import androidx.compose.material3.IconButton
import androidx.compose.material3.MediumTopAppBar
import androidx.compose.material3.OutlinedTextField
import androidx.compose.material3.Scaffold
import androidx.compose.material3.SnackbarHostState
import androidx.compose.material3.Text
import androidx.compose.material3.TopAppBarDefaults
import androidx.compose.material3.rememberTopAppBarState
import androidx.compose.runtime.Composable
import androidx.compose.runtime.LaunchedEffect
import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.remember
import androidx.compose.runtime.setValue
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.vector.ImageVector
import androidx.compose.ui.input.nestedscroll.nestedScroll
import androidx.compose.ui.res.vectorResource
import androidx.compose.ui.text.input.PasswordVisualTransformation
import androidx.compose.ui.text.input.VisualTransformation
import androidx.compose.ui.tooling.preview.Preview
import androidx.compose.ui.unit.dp
import androidx.hilt.navigation.compose.hiltViewModel
import androidx.lifecycle.compose.collectAsStateWithLifecycle
import androidx.navigation.NavHostController
import com.example.movies.R
import com.example.movies.presentation.ui.theme.MoviesTheme

@Composable
fun ApiSettingsScreen(
    navController: NavHostController,
    modifier: Modifier = Modifier,
    contentPadding: PaddingValues = PaddingValues(0.dp),
    viewModel: ApiSettingsViewModel = hiltViewModel(),
    snackbarHostState: SnackbarHostState = remember { SnackbarHostState() }
) {
    val state by viewModel.state.collectAsStateWithLifecycle()

    LaunchedEffect(state.error) {
        state.error?.let {
            when (it) {
                ApiSettingsScreenUiState.Error.Local -> {
                    snackbarHostState.showSnackbar("Ошибка чтения/записи")
                    viewModel.errorShown()
                }
                is ApiSettingsScreenUiState.Error.Network.Unknown -> {
                    snackbarHostState.showSnackbar("Ошибка сети")
                    viewModel.errorShown()
                }
                else -> {}
            }
        }
    }
    LaunchedEffect(state.message) {
        state.message?.let {
            when (it) {
                ApiSettingsScreenUiState.Message.Success -> {
                    snackbarHostState.showSnackbar("API ключ успешно обновлен!")
                }
            }
            viewModel.messageShown()
        }
    }

    ApiSettingsScreenContent(
        state = state,
        contentPadding = contentPadding,
        onSetApiKey = {
            viewModel.setApiKey(apiKey = it)
        },
        onBackPressed = {
            navController.popBackStack()
        }
    )
}

@OptIn(ExperimentalMaterial3Api::class)
@Composable
fun ApiSettingsScreenContent(
    modifier: Modifier = Modifier,
    contentPadding: PaddingValues = PaddingValues(0.dp),
    state: ApiSettingsScreenUiState,
    onSetApiKey: (String) -> Unit = {},
    onBackPressed: () -> Unit = {}
) {
    val scrollBehavior = TopAppBarDefaults.enterAlwaysScrollBehavior(rememberTopAppBarState())

    var currentApiKey by remember(state.apiKey) { mutableStateOf(state.apiKey) }
    var keyVisible by remember { mutableStateOf(false) }

    Scaffold(
        modifier = modifier
            .nestedScroll(scrollBehavior.nestedScrollConnection),
        topBar = {
            MediumTopAppBar(
                title = {
                    Text(text = "Настройка API")
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
                .padding(horizontal = 8.dp),
            verticalArrangement = Arrangement.spacedBy(16.dp)
        ) {
            OutlinedTextField(
                modifier = Modifier.fillMaxWidth(),
                value = currentApiKey,
                onValueChange = {
                    currentApiKey = it
                },
                label = {
                    Text(text = "API ключ")
                },
                placeholder = {
                    Text(text = "Введите ваш API ключ")
                },
                singleLine = true,
                leadingIcon = {
                    Icon(
                        imageVector = ImageVector.vectorResource(R.drawable.key_outlined),
                        contentDescription = null
                    )
                },
                trailingIcon = {
                    IconButton(onClick = { keyVisible = !keyVisible }) {
                        Icon(
                            imageVector = if (keyVisible) {
                                ImageVector.vectorResource(R.drawable.eye_crossed_outlined)
                            } else {
                                ImageVector.vectorResource(R.drawable.eye_outlined)
                            },
                            contentDescription = null
                        )
                    }
                },
                visualTransformation = if (keyVisible) {
                    VisualTransformation.None
                } else {
                    PasswordVisualTransformation()
                },
                isError = state.error is ApiSettingsScreenUiState.Error.Network.Unauthorized,
                supportingText = {
                    when (state.error) {
                        ApiSettingsScreenUiState.Error.Network.Unauthorized -> {
                            Text("Неправильный или недействительный API ключ")
                        }
                        else -> null
                    }
                },
                enabled = !state.isLoading
            )
            Button(
                onClick = { onSetApiKey(currentApiKey) },
                enabled = currentApiKey.isNotBlank() && !state.isLoading,
                modifier = Modifier
                    .size(150.dp, 50.dp)
            ) {
                if (state.isLoading) {
                    CircularProgressIndicator(
                        modifier = Modifier
                            .progressSemantics()
                            .size(30.dp)
                    )
                } else {
                    Text("Сохранить")
                }
            }
        }
    }
}

@Preview
@Composable
private fun ApiSettingsScreenPreview() {
    var state by remember {
        mutableStateOf(
            ApiSettingsScreenUiState(
//                isLoading = true
                error = ApiSettingsScreenUiState.Error.Network.Unauthorized
            )
        )
    }
    MoviesTheme {
        ApiSettingsScreenContent(
            state = state,
            onSetApiKey = {
                state = state.copy(apiKey = it)
            }
        )
    }
}
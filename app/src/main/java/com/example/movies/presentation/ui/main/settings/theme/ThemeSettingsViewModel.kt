package com.example.movies.presentation.ui.main.settings.theme

import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.example.movies.data.repository.SettingsRepository
import com.example.movies.domain.model.ThemeMode
import dagger.hilt.android.lifecycle.HiltViewModel
import kotlinx.coroutines.flow.SharingStarted
import kotlinx.coroutines.flow.map
import kotlinx.coroutines.flow.stateIn
import kotlinx.coroutines.launch
import javax.inject.Inject

@HiltViewModel
class ThemeSettingsViewModel @Inject constructor(
    private val repository: SettingsRepository
) : ViewModel() {
    val settings = repository.themeSettings
        .map { settings ->
            ThemeSettingsUiState(
                themeMode = settings.themeMode,
                isDynamicColorEnabled = settings.isDynamicColorEnabled
            )
        }
        .stateIn(
            scope = viewModelScope,
            started = SharingStarted.WhileSubscribed(5000),
            initialValue = ThemeSettingsUiState(
                themeMode = ThemeMode.SYSTEM,
                isDynamicColorEnabled = true
            )
        )

    fun setThemeMode(themeMode: ThemeMode) {
        viewModelScope.launch {
            repository.updateThemeMode(themeMode)
        }
    }

    fun setDynamicColorPreference(useDynamicColor: Boolean) {
        viewModelScope.launch {
            repository.updateDynamicColors(useDynamicColor)
        }
    }
}

data class ThemeSettingsUiState(
    val themeMode: ThemeMode,
    val isDynamicColorEnabled: Boolean
)
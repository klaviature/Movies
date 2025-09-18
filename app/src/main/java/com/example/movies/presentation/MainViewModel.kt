package com.example.movies.presentation

import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.example.movies.data.repository.SettingsRepository
import com.example.movies.domain.model.ThemeMode
import com.example.movies.domain.model.ThemeSettings
import dagger.hilt.android.lifecycle.HiltViewModel
import kotlinx.coroutines.flow.SharingStarted
import kotlinx.coroutines.flow.stateIn
import javax.inject.Inject

@HiltViewModel
class MainViewModel @Inject constructor(
    private val settingsRepository: SettingsRepository
) : ViewModel() {
    val settings = settingsRepository.themeSettings.stateIn(
        viewModelScope,
        SharingStarted.WhileSubscribed(5000),
        ThemeSettings(
            themeMode = ThemeMode.SYSTEM,
            isDynamicColorEnabled = true
        )
    )
}
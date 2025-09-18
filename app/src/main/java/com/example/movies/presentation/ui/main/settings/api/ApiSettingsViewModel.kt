package com.example.movies.presentation.ui.main.settings.api

import android.util.Log
import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.example.movies.data.repository.SettingsRepository
import com.example.movies.domain.model.DataError
import com.example.movies.domain.model.Result
import dagger.hilt.android.lifecycle.HiltViewModel
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.flow.SharingStarted
import kotlinx.coroutines.flow.asStateFlow
import kotlinx.coroutines.flow.map
import kotlinx.coroutines.flow.stateIn
import kotlinx.coroutines.flow.update
import kotlinx.coroutines.launch
import javax.inject.Inject

@HiltViewModel
class ApiSettingsViewModel @Inject constructor(
    private val repository: SettingsRepository
) : ViewModel() {
    private val _state = MutableStateFlow(ApiSettingsScreenUiState())
    val state get() = _state.asStateFlow()

    init {
        viewModelScope.launch {
            repository.apiKey.collect { apiKey ->
                Log.d("ApiSettingsViewModel", "apiKey: $apiKey")
                _state.update { it.copy(apiKey = apiKey ?: "") }
            }
        }
    }

    fun setApiKey(apiKey: String) {
        viewModelScope.launch {
            repository.updateApiKey(apiKey).collect { result ->
                when (result) {
                    is Result.Error -> {
                        when (result.error) {
                            is DataError.Network -> {
                                when (result.error) {
                                    is DataError.Network.Unauthorized -> _state.update {
                                        it.copy(error = ApiSettingsScreenUiState.Error.Network.Unauthorized)
                                    }

                                    else -> _state.update {
                                        it.copy(error = ApiSettingsScreenUiState.Error.Network.Unknown())
                                    }
                                }
                            }

                            is DataError.Local -> _state.update {
                                it.copy(error = ApiSettingsScreenUiState.Error.Local)
                            }
                        }
                        _state.update { it.copy(isLoading = false) }
                    }
                    Result.Loading -> _state.update { it.copy(isLoading = true) }
                    is Result.Success -> _state.update {
                        it.copy(
                            isLoading = false,
                            message = ApiSettingsScreenUiState.Message.Success,
                            error = null
                        )
                    }
                }
            }
        }
    }

    fun errorShown() = _state.update { it.copy(error = null) }

    fun messageShown() = _state.update { it.copy(message = null) }
}

data class ApiSettingsScreenUiState(
    val apiKey: String = "",
    val error: Error? = null,
    val message: Message? = null,
    val isLoading: Boolean = false
) {
    sealed interface Error {
        sealed interface Network : Error {
            data object Unauthorized : Network
            data class Unknown(val message: String? = null) : Network
        }

        data object Local : Error
    }

    sealed interface Message {
        data object Success : Message
    }
}
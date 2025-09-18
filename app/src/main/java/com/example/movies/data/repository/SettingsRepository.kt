package com.example.movies.data.repository

import androidx.datastore.core.DataStore
import androidx.datastore.preferences.core.Preferences
import androidx.datastore.preferences.core.booleanPreferencesKey
import androidx.datastore.preferences.core.edit
import androidx.datastore.preferences.core.stringPreferencesKey
import com.example.movies.data.api.ApiServiceCoroutines
import com.example.movies.domain.model.DataError
import com.example.movies.domain.model.Result
import com.example.movies.domain.model.ThemeMode
import com.example.movies.domain.model.ThemeSettings
import kotlinx.coroutines.flow.Flow
import kotlinx.coroutines.flow.flow
import kotlinx.coroutines.flow.map
import retrofit2.HttpException
import javax.inject.Inject

object PreferencesKeys {
    val THEME_MODE = stringPreferencesKey("theme_mode")
    val DYNAMIC_COLORS = booleanPreferencesKey("dynamic_colors")
    val API_KEY = stringPreferencesKey("api_key")
}

class SettingsRepository @Inject constructor(
    private val dataStore: DataStore<Preferences>,
    private val apiService: ApiServiceCoroutines
) {
    val themeSettings: Flow<ThemeSettings> = dataStore.data.map { preferences ->
        val themeMode = ThemeMode.valueOf(
            preferences[PreferencesKeys.THEME_MODE] ?: ThemeMode.SYSTEM.name
        )
        val isDynamicColorEnabled = preferences[PreferencesKeys.DYNAMIC_COLORS] ?: true
        ThemeSettings(themeMode, isDynamicColorEnabled)
    }

    val apiKey: Flow<String?> = dataStore.data.map { preferences ->
        preferences[PreferencesKeys.API_KEY]
    }

    suspend fun updateThemeMode(themeMode: ThemeMode) {
        dataStore.edit { preferences ->
            preferences[PreferencesKeys.THEME_MODE] = themeMode.name
        }
    }

    suspend fun updateDynamicColors(enabled: Boolean) {
        dataStore.edit { preferences ->
            preferences[PreferencesKeys.DYNAMIC_COLORS] = enabled
        }
    }

    suspend fun updateApiKey(apiKey: String): Flow<Result<Unit, DataError>> = flow {
        try {
            emit(Result.Loading)
            val response = apiService.validateApiKey(apiKey)
            if (response.isSuccessful) {
                dataStore.edit { preferences ->
                    preferences[PreferencesKeys.API_KEY] = apiKey
                }
                emit(Result.Success(Unit))
            } else {
                val message = response.message()
                emit(
                    when (response.code()) {
                        401 -> Result.Error(DataError.Network.Unauthorized(message))
                        403 -> Result.Error(DataError.Network.Forbidden(message))
                        404 -> Result.Error(DataError.Network.NotFound(message))
                        else -> Result.Error(DataError.Network.Unknown(message))
                    }
                )
            }
        } catch (e: HttpException) {
            emit(Result.Error(DataError.Network.Unknown(message = e.message)))
        } catch (e: Exception) {
            emit(Result.Error(DataError.Local.Critical))
        }
    }
}
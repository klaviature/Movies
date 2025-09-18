package com.example.movies.data.repository

import androidx.datastore.core.DataStore
import androidx.datastore.preferences.core.Preferences
import kotlinx.coroutines.flow.Flow
import kotlinx.coroutines.flow.map
import javax.inject.Inject
import javax.inject.Singleton

@Singleton
class ApiKeyProviderRepository @Inject constructor(
    private val dataStore: DataStore<Preferences>
) {
    val apiKey: Flow<String?> = dataStore.data.map { preferences ->
        preferences[PreferencesKeys.API_KEY]
    }
}
package com.n.githubsample.core

import android.content.Context
import androidx.datastore.core.DataStore
import androidx.datastore.preferences.core.Preferences
import androidx.datastore.preferences.core.edit
import androidx.datastore.preferences.core.stringPreferencesKey
import androidx.datastore.preferences.preferencesDataStore
import kotlinx.coroutines.flow.Flow
import kotlinx.coroutines.flow.map

class DataStoreManager(
    private val app: Context
) {
    private val Context.dataStore: DataStore<Preferences> by preferencesDataStore(name = DATASTORE_NAME)

    suspend fun setAccessToken(token: String) {
        app.dataStore.edit { pref ->
            pref[KEY_ACCESS_TOKE] = token
        }
    }

    fun getAccessToken(): Flow<String> =
        app.dataStore.data.map { pref -> pref[KEY_ACCESS_TOKE] ?: "" }

    companion object {
        private const val DATASTORE_NAME = "app_dataStore"

        private val KEY_ACCESS_TOKE = stringPreferencesKey("ca2cae91-7152-46c7-9640-fc09a48a8cab")
    }
}
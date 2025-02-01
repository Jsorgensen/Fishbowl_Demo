package com.example.fishbowl_demo.repositories

import android.content.Context
import androidx.datastore.core.DataStore
import androidx.datastore.preferences.core.Preferences
import androidx.datastore.preferences.core.edit
import androidx.datastore.preferences.core.stringPreferencesKey
import androidx.datastore.preferences.preferencesDataStore
import com.example.fishbowl_demo.data.model.Joke
import com.google.gson.Gson
import kotlinx.coroutines.flow.Flow
import kotlinx.coroutines.flow.map
import javax.inject.Inject

// At the top level of your kotlin file:
val Context.dataStore: DataStore<Preferences> by preferencesDataStore(name = "fishbowl_demo_preferences")

class LocalStorageRepository @Inject constructor(
    private val dataStore: DataStore<Preferences>,
    private val gson: Gson,
) {

    val JOKES_KEY = stringPreferencesKey("jokes_key")

    val jokesFlow: Flow<List<Joke>> = dataStore.data.map { preferences ->
        val json = preferences[JOKES_KEY]
        gson.fromJson(json, Array<Joke>::class.java).toList()
    }

    suspend fun setJokes(jokes: List<Joke>) {
        dataStore.edit { preferences ->
            preferences[JOKES_KEY] = gson.toJson(jokes)
        }
    }

}
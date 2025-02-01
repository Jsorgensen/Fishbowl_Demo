package com.example.fishbowl_demo.repositories

import android.util.Log
import com.example.fishbowl_demo.data.model.Joke
import com.example.fishbowl_demo.data.network.JokesService
import com.example.fishbowl_demo.util.coroutineScope
import com.example.fishbowl_demo.util.launchIO
import kotlinx.coroutines.flow.Flow
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.flow.asStateFlow
import kotlinx.coroutines.flow.firstOrNull
import javax.inject.Inject

class JokesRepository @Inject constructor(
    private val jokesService: JokesService,
    private val localStorageRepository: LocalStorageRepository,
) {

    private val _jokesFlow = MutableStateFlow<List<Joke>>(emptyList())
    val jokesFlow: Flow<List<Joke>> = _jokesFlow.asStateFlow()


    init {
        coroutineScope.launchIO {
            retrieveLocalStorageJokes()
        }
    }

    private suspend fun retrieveLocalStorageJokes() {
        val localJokes = localStorageRepository.jokesFlow
            .firstOrNull() ?: emptyList()
        _jokesFlow.emit(localJokes)

        loadJokes()
    }

    private suspend fun loadJokes() {
        if (_jokesFlow.value.size < JOKES_PER_PAGE) {
            val amountOfJokesToRequest = JOKES_PER_PAGE - _jokesFlow.value.size
            val newJokes = requestJokes(amountOfJokesToRequest)
            Log.d("JokesRepository", "newJokes: $newJokes")

            val jokes = _jokesFlow.value
                .toMutableList()
                .apply { addAll(newJokes) }

            _jokesFlow.emit(jokes)

            localStorageRepository.setJokes(jokes)

            loadJokes()
        }
    }

    private suspend fun requestJokes(
        amount: Int,
    ): List<Joke> {
        return jokesService.getJokes(amount) ?: emptyList()
    }

    companion object {
        private const val JOKES_PER_PAGE = 25
    }
}
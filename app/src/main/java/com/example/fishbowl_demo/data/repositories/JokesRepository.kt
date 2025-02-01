package com.example.fishbowl_demo.data.repositories

import android.util.Log
import com.example.fishbowl_demo.data.model.Joke
import com.example.fishbowl_demo.data.network.JokesService
import com.example.fishbowl_demo.util.coroutineScope
import com.example.fishbowl_demo.util.launchIO
import kotlinx.coroutines.flow.Flow
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.flow.asStateFlow
import kotlinx.coroutines.flow.firstOrNull
import kotlinx.coroutines.flow.map
import javax.inject.Inject

class JokesRepository @Inject constructor(
    private val jokesService: JokesService,
    private val localStorageRepository: LocalStorageRepository,
) {

    private val _jokesFlow = MutableStateFlow<List<Joke>>(emptyList())
    val jokesFlow: Flow<List<Joke>> = _jokesFlow.asStateFlow()

    val favoritesFlow by lazy {
        localStorageRepository.jokesFlow
    }

    private var currentPage = 0


    suspend fun requestBatchOfJokes(page: Int = ++currentPage) {
        Log.d("JokesRepository", "requestBatchOfJokes) currentPage: $page")
        val desiredJokeCount = page * JOKES_PER_PAGE
        if (_jokesFlow.value.size < desiredJokeCount) {
            val amountOfJokesToRequest = desiredJokeCount - _jokesFlow.value.size
            val newJokes = requestJokes(amountOfJokesToRequest)

            val jokes = _jokesFlow.value
                .toMutableList()
                .apply { addAll(newJokes) }

            _jokesFlow.emit(jokes)

            requestBatchOfJokes(page)
        }
    }

    private suspend fun requestJokes(
        amount: Int,
    ): List<Joke> {
        return jokesService.getJokes(amount) ?: emptyList()
    }

    fun getJoke(jokeId: Int?): Joke? {
        return _jokesFlow.value.find { it.id == jokeId }
    }

    suspend fun favoriteJoke(joke: Joke) {
        if (joke.isFavorite == true) {
            localStorageRepository.removeJoke(joke)
        } else {
            localStorageRepository.addJoke(joke)
        }

        val updatedJoke = joke.copy(isFavorite = !(joke.isFavorite ?: false))
        updateJoke(updatedJoke)
    }

    private suspend fun updateJoke(joke: Joke) {
        val jokes = _jokesFlow.value
            .toMutableList()
            .apply {
                val index = indexOfFirst { it.id == joke.id }
                if (index != -1) set(index, joke)
            }.toList()
        _jokesFlow.emit(jokes)
    }

    companion object {
        private const val JOKES_PER_PAGE = 25
    }
}
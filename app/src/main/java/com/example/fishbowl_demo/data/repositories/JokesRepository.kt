package com.example.fishbowl_demo.data.repositories

import android.util.Log
import com.example.fishbowl_demo.data.model.Joke
import com.example.fishbowl_demo.data.model.JokeCategory
import com.example.fishbowl_demo.data.network.JokesService
import com.example.fishbowl_demo.util.coroutineScope
import com.example.fishbowl_demo.util.launchIO
import kotlinx.coroutines.ExperimentalCoroutinesApi
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.flow.asStateFlow
import kotlinx.coroutines.flow.first
import kotlinx.coroutines.flow.flatMapLatest
import kotlinx.coroutines.flow.map
import javax.inject.Inject


class JokesRepository @Inject constructor(
    private val jokesService: JokesService,
    private val localStorageRepository: LocalStorageRepository,
) {

    private val _jokesFlow = MutableStateFlow<List<Joke>>(emptyList())

    private val _selectedCategoryFilter = MutableStateFlow(JokeCategory.Any)
    val selectedCategoryFilter = _selectedCategoryFilter.asStateFlow()

    @OptIn(ExperimentalCoroutinesApi::class)
    val filteredJokesFLow = selectedCategoryFilter.flatMapLatest { category ->
        _jokesFlow.map { jokes ->
            jokes.filter { joke ->
                when (category) {
                    JokeCategory.Any -> true
                    else -> joke.category == category
                }
            }
        }
    }

    val favoritesFlow by lazy {
        localStorageRepository.jokesFlow
    }

    private var currentPage = 0


    init {
        coroutineScope.launchIO {
            _selectedCategoryFilter.value = localStorageRepository.jokeCategoryFLow.first()
                ?: JokeCategory.Any
        }
    }

    suspend fun requestBatchOfJokes(page: Int = ++currentPage) {
        val desiredJokeCount = page * JOKES_PER_PAGE
        if (_jokesFlow.value.size < desiredJokeCount) {
            val amountOfJokesToRequest = desiredJokeCount - _jokesFlow.value.size
            val newJokes = requestJokes(amountOfJokesToRequest)
            Log.d("JokesRepository", "requestBatchOfJokes) newJokesCount: ${newJokes.size}")

            val jokes = _jokesFlow.value
                .toMutableList()
                .apply { addAll(newJokes) }

            _jokesFlow.emit(jokes)

            requestBatchOfJokes(page)
        }
    }

    suspend fun filterJokes(category: JokeCategory) {
        _selectedCategoryFilter.value = category
        localStorageRepository.setJokeCategory(category)
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
        private const val REQUEST_TIME_THRESHOLD = 2000L
    }
}
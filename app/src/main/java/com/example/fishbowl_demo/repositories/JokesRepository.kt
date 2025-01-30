package com.example.fishbowl_demo.repositories

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
        retrieveLocalStorageJokes()
    }

    private fun retrieveLocalStorageJokes() {
        coroutineScope.launchIO {
            val localJokes = localStorageRepository.jokesFlow.firstOrNull() ?: emptyList()
            _jokesFlow.emit(localJokes)
        }
    }
}
package com.example.fishbowl_demo.viewmodel

import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import androidx.navigation.NavController
import com.example.fishbowl_demo.data.model.Joke
import com.example.fishbowl_demo.repositories.JokesRepository
import com.example.fishbowl_demo.util.launchDefault
import dagger.hilt.android.lifecycle.HiltViewModel
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.flow.asStateFlow
import kotlinx.coroutines.flow.first
import javax.inject.Inject

@HiltViewModel
class JokeViewModel @Inject constructor(
    private val jokesRepository: JokesRepository,
): ViewModel() {

    private val _jokeFlow = MutableStateFlow(Joke.loading)
    val jokeFlow = _jokeFlow.asStateFlow()

    var navController: NavController? = null


    fun setJoke(jokeId: Int?) {
        viewModelScope.launchDefault {
            val joke = jokesRepository.jokesFlow.first().find { it.id == jokeId }
            _jokeFlow.emit(joke ?: Joke.failedToLoad)
        }
    }

}
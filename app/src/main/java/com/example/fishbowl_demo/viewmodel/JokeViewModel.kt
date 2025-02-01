package com.example.fishbowl_demo.viewmodel

import android.util.Log
import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import androidx.navigation.NavController
import com.example.fishbowl_demo.data.model.Joke
import com.example.fishbowl_demo.data.repositories.JokesRepository
import com.example.fishbowl_demo.util.launchDefault
import dagger.hilt.android.lifecycle.HiltViewModel
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.flow.asStateFlow
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
            val joke = jokesRepository.getJoke(jokeId)
            _jokeFlow.emit(joke ?: Joke.failedToLoad)
        }
    }

    fun onFavoriteAction() {
        viewModelScope.launchDefault {
            val joke = _jokeFlow.value
            jokesRepository.favoriteJoke(joke)
            setJoke(joke.id)
        }
    }

    fun onBackClicked() {
        navController?.popBackStack()
            ?: Log.w("JokeViewModel", "onBackClicked) no navController")
    }
}
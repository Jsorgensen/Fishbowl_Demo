package com.example.fishbowl_demo.viewmodel

import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import androidx.navigation.NavController
import com.example.fishbowl_demo.data.model.Joke
import com.example.fishbowl_demo.data.model.Screen
import com.example.fishbowl_demo.data.repositories.JokesRepository
import com.example.fishbowl_demo.util.launchIO
import dagger.hilt.android.lifecycle.HiltViewModel
import javax.inject.Inject

@HiltViewModel
class FavoritesViewModel @Inject constructor(
    private val jokesRepository: JokesRepository,
): ViewModel() {

    val jokesFlow by lazy {
        jokesRepository.favoritesFlow
    }

    var navController: NavController? = null


    fun onJokeSelected(joke: Joke) {
        navController?.navigate("joke/${joke.id}")
    }

    fun onJokeFavoriteSelected(joke: Joke) {
        viewModelScope.launchIO {
            jokesRepository.favoriteJoke(joke)
        }
    }

    fun onBackClicked() {
        navController?.popBackStack()
    }
}
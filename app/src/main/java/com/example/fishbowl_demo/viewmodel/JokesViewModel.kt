package com.example.fishbowl_demo.viewmodel

import androidx.lifecycle.ViewModel
import androidx.navigation.NavController
import com.example.fishbowl_demo.data.model.Joke
import com.example.fishbowl_demo.repositories.JokesRepository
import dagger.hilt.android.lifecycle.HiltViewModel
import javax.inject.Inject

@HiltViewModel
class JokesViewModel @Inject constructor(
    private val jokesRepository: JokesRepository,
): ViewModel() {

    val jokesFlow by lazy { jokesRepository.jokesFlow }

    var navController: NavController? = null


    fun onJokeSelected(joke: Joke) {
        navController?.navigate("joke/${joke.id}")
    }
}
package com.example.fishbowl_demo.viewmodel

import androidx.compose.runtime.mutableStateOf
import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import androidx.navigation.NavController
import com.example.fishbowl_demo.data.model.Joke
import com.example.fishbowl_demo.data.model.JokeCategory
import com.example.fishbowl_demo.data.model.Screen
import com.example.fishbowl_demo.data.repositories.JokesRepository
import com.example.fishbowl_demo.util.launchDefault
import com.example.fishbowl_demo.util.launchIO
import dagger.hilt.android.lifecycle.HiltViewModel
import javax.inject.Inject

@HiltViewModel
class JokesViewModel @Inject constructor(
    private val jokesRepository: JokesRepository,
): ViewModel() {

    val jokesFlow by lazy { jokesRepository.filteredJokesFLow }

    val displayCategoryFilterDialog by lazy { mutableStateOf(false) }

    val selectedCategoryFilter by lazy { jokesRepository.selectedCategoryFilter }

    var navController: NavController? = null


    init {
        viewModelScope.launchIO {
            jokesRepository.requestBatchOfJokes()
        }
    }

    fun onJokeSelected(joke: Joke) {
        navController?.navigate("joke/${joke.id}")
    }

    fun onJokeFavoriteSelected(joke: Joke) {
        viewModelScope.launchIO {
            jokesRepository.favoriteJoke(joke)
        }
    }

    fun onSelectCategory() {
        viewModelScope.launchDefault {
            displayCategoryFilterDialog.value = true
        }
    }

    fun onCategorySelected(jokeCategory: JokeCategory) {
        viewModelScope.launchDefault {
            displayCategoryFilterDialog.value = false
            jokesRepository.filterJokes(jokeCategory)
        }

    }

    fun navigateToFavorites() {
        navController?.navigate(Screen.Favorites.route)
    }

    fun requestBatchOfJokes() {
        viewModelScope.launchIO {
            jokesRepository.requestBatchOfJokes()
        }
    }
}
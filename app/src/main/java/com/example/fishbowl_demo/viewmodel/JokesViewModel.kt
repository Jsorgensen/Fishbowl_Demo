package com.example.fishbowl_demo.viewmodel

import androidx.compose.runtime.mutableStateOf
import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import androidx.navigation.NavController
import com.example.fishbowl_demo.data.model.Joke
import com.example.fishbowl_demo.data.model.JokeCategory
import com.example.fishbowl_demo.data.model.Screen
import com.example.fishbowl_demo.data.repositories.JokesRepository
import com.example.fishbowl_demo.data.repositories.LocalStorageRepository
import com.example.fishbowl_demo.util.launchDefault
import com.example.fishbowl_demo.util.launchIO
import com.example.fishbowl_demo.util.withContextIO
import dagger.hilt.android.lifecycle.HiltViewModel
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.flow.asStateFlow
import kotlinx.coroutines.flow.combine
import kotlinx.coroutines.flow.first
import kotlinx.coroutines.flow.map
import javax.inject.Inject

@HiltViewModel
class JokesViewModel @Inject constructor(
    private val jokesRepository: JokesRepository,
    private val localStorageRepository: LocalStorageRepository,
): ViewModel() {

    private val _selectedCategoryFilter = MutableStateFlow(JokeCategory.Any)
    val selectedCategoryFilter = _selectedCategoryFilter.asStateFlow()

    private val _searchText = MutableStateFlow("")
    val searchText = _searchText.asStateFlow()

    private val _filterText = MutableStateFlow("")
    private val filterText = _filterText.asStateFlow()

    val jokesFlow = selectedCategoryFilter
        .combine(filterText) { category, searchText ->
            Pair(category, searchText)
        }.combine(jokesRepository.jokesFlow) { (category, searchText), jokes ->
            Triple(category, searchText, jokes)
        }.map { (category, searchText, jokes) ->
            jokes.filter { joke ->
                when (category) {
                    JokeCategory.Any -> true
                    else -> joke.category == category
                } && when {
                    searchText.isBlank() -> true
                    else -> joke.jokeText.contains(searchText, ignoreCase = true)
                }
            }
        }

    val displayCategoryFilterDialog by lazy { mutableStateOf(false) }

    var navController: NavController? = null


    init {
        viewModelScope.launchIO {
            jokesRepository.requestBatchOfJokes()

            _selectedCategoryFilter.value = localStorageRepository.jokeCategoryFLow.first()
                ?: JokeCategory.Any

            _searchText.value = localStorageRepository.searchTextFlow.first()
            _filterText.value = searchText.value
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

    fun onDismissCategoryDialog() {
        displayCategoryFilterDialog.value = false
    }

    fun onCategorySelected(category: JokeCategory) {
        viewModelScope.launchDefault {
            displayCategoryFilterDialog.value = false

            _selectedCategoryFilter.value = category
            withContextIO { localStorageRepository.setJokeCategory(category) }
        }
    }

    fun onSearchTextChange(text: String) {
        _searchText.value = text
    }

    fun onSearchClick() {
        _filterText.value = searchText.value
        viewModelScope.launchIO { localStorageRepository.setSearchText(filterText.value) }
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
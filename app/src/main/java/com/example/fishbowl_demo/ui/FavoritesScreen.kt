package com.example.fishbowl_demo.ui

import androidx.compose.foundation.layout.padding
import androidx.compose.material3.Scaffold
import androidx.compose.runtime.Composable
import androidx.compose.runtime.State
import androidx.compose.runtime.collectAsState
import androidx.compose.ui.Modifier
import androidx.navigation.NavController
import com.example.fishbowl_demo.R
import com.example.fishbowl_demo.data.model.Joke
import com.example.fishbowl_demo.ui.components.HeaderRow
import com.example.fishbowl_demo.ui.components.JokesList
import com.example.fishbowl_demo.viewmodel.FavoritesViewModel

@Composable
fun FavoritesScreen(
    viewModel: FavoritesViewModel,
    navController: NavController,
) {
    viewModel.navController = navController
    FavoritesState(viewModel)
}

@Composable
fun FavoritesState(
    viewModel: FavoritesViewModel,
) {
    val jokesState = viewModel.jokesFlow.collectAsState(initial = emptyList())
    val onSelected: (Joke) -> Unit = { joke: Joke -> viewModel.onJokeSelected(joke) }
    val onFavorite: (Joke) -> Unit = { joke: Joke -> viewModel.onJokeFavoriteSelected(joke) }
    val onBackClicked: () -> Unit = { viewModel.onBackClicked() }

    FavoritesBody(
        jokesState,
        onSelected,
        onFavorite,
        onBackClicked,
    )
}

@Composable
fun FavoritesBody(
    jokesState: State<List<Joke>>,
    onSelected: (Joke) -> Unit,
    onFavorite: (Joke) -> Unit,
    onBackClicked: () -> Unit,
) {
    Scaffold(
        topBar = { HeaderRow(
            titleStringId = R.string.jokes,
            displayBack = true,
            onBackClicked = onBackClicked,
        ) }
    ) { innerPadding ->
        JokesList(
            modifier = Modifier.padding(innerPadding),
            jokesState = jokesState,
            onSelected = onSelected,
            onFavorite = onFavorite,
        )
    }
}
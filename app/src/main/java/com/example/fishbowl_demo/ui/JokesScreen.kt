package com.example.fishbowl_demo.ui

import androidx.compose.foundation.layout.padding
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.Scaffold
import androidx.compose.material3.Surface
import androidx.compose.runtime.Composable
import androidx.compose.runtime.State
import androidx.compose.runtime.collectAsState
import androidx.compose.ui.Modifier
import androidx.compose.ui.tooling.preview.Preview
import androidx.navigation.NavController
import com.example.fishbowl_demo.R
import com.example.fishbowl_demo.data.model.Joke
import com.example.fishbowl_demo.ui.components.HeaderAction
import com.example.fishbowl_demo.ui.components.HeaderRow
import com.example.fishbowl_demo.ui.components.JokesList
import com.example.fishbowl_demo.viewmodel.JokesViewModel
import kotlinx.coroutines.flow.flowOf

@Composable
fun JokesScreen(
    viewModel: JokesViewModel,
    navController: NavController,
) {
    viewModel.navController = navController
    JokesState(viewModel)
}

@Preview(showBackground = true)
@Composable
fun JokesScreenPreview() {
    MaterialTheme {
        Surface {
            val exampleList = listOf(Joke.example)
            val jokes = flowOf(exampleList).collectAsState(exampleList)
            JokesBody(jokes, {}, {}, {}, {})
        }
    }
}

@Composable
fun JokesState(
    viewModel: JokesViewModel
) {
    val jokesState = viewModel.jokesFlow.collectAsState(initial = emptyList())
    val onSelected: (Joke) -> Unit = { joke: Joke -> viewModel.onJokeSelected(joke) }
    val onFavorite: (Joke) -> Unit = { joke: Joke -> viewModel.onJokeFavoriteSelected(joke) }
    val onNavigateToFavorites = { viewModel.navigateToFavorites() }
    val onBottomReached = { viewModel.requestBatchOfJokes() }

    JokesBody(
        jokesState,
        onSelected,
        onFavorite,
        onNavigateToFavorites,
        onBottomReached,
    )
}

@Composable
fun JokesBody(
    jokesState: State<List<Joke>>,
    onSelected: (Joke) -> Unit,
    onFavorite: (Joke) -> Unit,
    onNavigateToFavorites: () -> Unit,
    onBottomReached: () -> Unit,
) {
    Scaffold(
        topBar = { HeaderRow(
            titleStringId = R.string.jokes,
            actions = {
                HeaderAction(
                    iconId = R.drawable.heart,
                    onClick = onNavigateToFavorites
                )
            }
        ) }
    ) { innerPadding ->
        JokesList(
            modifier = Modifier.padding(innerPadding),
            jokesState = jokesState,
            onSelected = onSelected,
            onFavorite = onFavorite,
            onBottomReached = onBottomReached,
        )
    }
}
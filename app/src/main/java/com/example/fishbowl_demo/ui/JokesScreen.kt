package com.example.fishbowl_demo.ui

import androidx.compose.foundation.background
import androidx.compose.foundation.clickable
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.padding
import androidx.compose.material3.HorizontalDivider
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.Scaffold
import androidx.compose.material3.Surface
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.runtime.State
import androidx.compose.runtime.collectAsState
import androidx.compose.ui.Modifier
import androidx.compose.ui.res.colorResource
import androidx.compose.ui.text.TextStyle
import androidx.compose.ui.tooling.preview.Preview
import androidx.compose.ui.unit.dp
import androidx.navigation.NavController
import com.example.fishbowl_demo.R
import com.example.fishbowl_demo.data.model.Joke
import com.example.fishbowl_demo.data.model.JokeCategory
import com.example.fishbowl_demo.ui.components.FavoriteButton
import com.example.fishbowl_demo.ui.components.HeaderRow
import com.example.fishbowl_demo.ui.components.JokeCategory
import com.example.fishbowl_demo.ui.components.LazyColumnWithBottomReached
import com.example.fishbowl_demo.ui.components.SwipeButton
import com.example.fishbowl_demo.ui.components.SwipeRow
import com.example.fishbowl_demo.ui.theme.roboRegular
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
            JokesBody(jokes, {}, {}, {})
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
    val onBottomReached = { viewModel.requestBatchOfJokes() }

    JokesBody(jokesState, onSelected, onFavorite, onBottomReached)
}

@Composable
fun JokesBody(
    jokesState: State<List<Joke>>,
    onSelected: (Joke) -> Unit,
    onFavorite: (Joke) -> Unit,
    onBottomReached: () -> Unit,
) {
    Scaffold(
        topBar = { HeaderRow(
            titleStringId = R.string.jokes,
            actions = {
                //TODO
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

@Composable
fun JokesList(
    modifier: Modifier,
    jokesState: State<List<Joke>>,
    onSelected: (Joke) -> Unit,
    onFavorite: (Joke) -> Unit,
    onBottomReached: () -> Unit,
) {
    val jokes = jokesState.value
    LazyColumnWithBottomReached(
        modifier = modifier,
        onBottomReached = onBottomReached
    ) {
        items(jokes.size) { i ->
            val joke = jokes[i]
            JokeItem(joke, onSelected, onFavorite)
            HorizontalDivider(modifier = Modifier.padding(start = 12.dp))
        }
    }
}

@Composable
fun JokeItem(
    joke: Joke,
    onSelected: (Joke) -> Unit = {},
    onFavorite: (Joke) -> Unit,
) {
    val jokeCategory = joke.category ?: JokeCategory.Undefined
    SwipeRow(
        modifier = Modifier
            .background(colorResource(R.color.red_500)),
        actions = {
            SwipeButton {
                FavoriteButton(
                    this,
                    isFavorite = joke.isFavorite ?: false,
                    tint = colorResource(R.color.white)
                )
            }
        },
        onAction = { onFavorite(joke) }
    ) {
        Column {
            Text(
                modifier = Modifier
                    .padding(12.dp)
                    .clickable { onSelected(joke) },
                text = joke.jokeText,
                style = TextStyle(
                    fontFamily = roboRegular
                )
            )
            Box(
                modifier = Modifier
                    .padding(start = 12.dp, top = 0.dp, end = 12.dp, bottom = 12.dp),
            ) {
                JokeCategory(jokeCategory)
            }
        }
    }
}
package com.example.fishbowl_demo.ui

import android.util.Log
import androidx.compose.foundation.background
import androidx.compose.foundation.clickable
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.lazy.LazyColumn
import androidx.compose.material3.HorizontalDivider
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.Surface
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.runtime.State
import androidx.compose.runtime.collectAsState
import androidx.compose.ui.Modifier
import androidx.compose.ui.res.colorResource
import androidx.compose.ui.text.TextStyle
import androidx.compose.ui.text.substring
import androidx.compose.ui.tooling.preview.Preview
import androidx.compose.ui.unit.dp
import androidx.navigation.NavController
import com.example.fishbowl_demo.R
import com.example.fishbowl_demo.data.model.Joke
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
            JokesBody(jokes, {}, {})
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

    JokesBody(jokesState, onSelected, onFavorite)
}

@Composable
fun JokesBody(
    jokesState: State<List<Joke>>,
    onSelected: (Joke) -> Unit,
    onFavorite: (Joke) -> Unit,
) {
    JokesList(jokesState, onSelected, onFavorite)
}

@Composable
fun JokesList(
    jokesState: State<List<Joke>>,
    onSelected: (Joke) -> Unit = {},
    onFavorite: (Joke) -> Unit,
) {
    val jokes = jokesState.value
    Log.d("JokesScreen", "JokesList) favorites: ${jokes.joinToString(",\n\t") { "${it.jokeText.substring(0, 30)}: ${it.isFavorite}"} }")
    LazyColumn {
        items(jokes.size) { i ->
            val joke = jokes[i]
            JokeItem(joke, onSelected, onFavorite)
            HorizontalDivider()
        }
    }
}

@Composable
fun JokeItem(
    joke: Joke,
    onSelected: (Joke) -> Unit = {},
    onFavorite: (Joke) -> Unit,
) {
    SwipeableRow(
        modifier = Modifier
            .background(colorResource(R.color.red_500)),
        actions = {
            FavoriteButton(
                isFavorite = joke.isFavorite ?: false,
            )
        },
        onAction = { onFavorite(joke) }
    ) {
        Text(
            modifier = Modifier
                .padding(12.dp)
                .clickable { onSelected(joke) },
            text = joke.jokeText,
            style = TextStyle(
                fontFamily = roboRegular
            )
        )
    }
}

@Composable
fun FavoriteButton(
    isFavorite: Boolean,
) {
    val drawableId = if (isFavorite) {
        R.drawable.heart
    } else {
        R.drawable.heart_outline
    }
    val contentDescription = if (isFavorite) {
        "favorite"
    } else {
        "not favorite"
    }
    SwipeableButton(
        drawableId = drawableId,
        contentDescription = contentDescription,
    )
}
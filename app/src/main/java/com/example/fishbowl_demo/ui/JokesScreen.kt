package com.example.fishbowl_demo.ui

import android.util.Log
import androidx.compose.foundation.clickable
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.lazy.LazyColumn
import androidx.compose.material3.HorizontalDivider
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.Surface
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.runtime.collectAsState
import androidx.compose.ui.Modifier
import androidx.compose.ui.text.TextStyle
import androidx.compose.ui.tooling.preview.Preview
import androidx.compose.ui.unit.dp
import androidx.navigation.NavController
import com.example.fishbowl_demo.data.model.Joke
import com.example.fishbowl_demo.ui.theme.roboRegular
import com.example.fishbowl_demo.viewmodel.JokesViewModel

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
            val jokes = listOf(Joke.example)
            JokesBody(jokes) {}
        }
    }
}

@Composable
fun JokesState(
    viewModel: JokesViewModel
) {
    val jokesState = viewModel.jokesFlow.collectAsState(initial = emptyList())
    val onSelected: (Joke) -> Unit = { joke: Joke -> viewModel.onJokeSelected(joke) }

    JokesBody(jokes = jokesState.value, onSelected)
}

@Composable
fun JokesBody(
    jokes: List<Joke>,
    onSelected: (Joke) -> Unit,
) {
    JokesList(jokes, onSelected)
}

@Composable
fun JokesList(
    jokes: List<Joke>,
    onSelected: (Joke) -> Unit = {},
) {
    LazyColumn {
        items(jokes.size) { i ->
            val joke = jokes[i]
            JokeItem(joke, onSelected)
            HorizontalDivider()
        }
    }
}

@Composable
fun JokeItem(
    joke: Joke,
    onSelected: (Joke) -> Unit = {},
) {
    Log.d("JokeItem", "joke: $joke")
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
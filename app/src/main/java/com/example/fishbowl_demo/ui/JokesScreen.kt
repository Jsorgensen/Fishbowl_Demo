package com.example.fishbowl_demo.ui

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
import com.example.fishbowl_demo.data.model.Joke
import com.example.fishbowl_demo.ui.theme.roboRegular
import com.example.fishbowl_demo.viewmodel.JokesViewModel

@Composable
fun JokesScreen(viewModel: JokesViewModel) {
    JokesState(viewModel)
}

@Preview(showBackground = true)
@Composable
fun JokesScreenPreview() {
    MaterialTheme {
        Surface {
            val jokes = listOf(Joke.example)
            JokesBody(jokes)
        }
    }
}

@Composable
fun JokesState(
    viewModel: JokesViewModel
) {
    val jokesState = viewModel.jokesFlow.collectAsState(initial = emptyList())
    JokesBody(jokes = jokesState.value)
}

@Composable
fun JokesBody(
    jokes: List<Joke>,
) {
    JokesList(jokes)
}

@Composable
fun JokesList(
    jokes: List<Joke>,
) {
    LazyColumn {
        items(jokes.size) { i ->
            val joke = jokes[i]
            JokeItem(joke)
            HorizontalDivider()
        }
    }
}

@Composable
fun JokeItem(joke: Joke) {
    val jokeText = when {
        joke.joke != null -> joke.joke
        joke.setup != null -> "${joke.setup}\n\n${joke.delivery}"
        else -> "No joke found"
    }
    Text(
        modifier = Modifier.padding(12.dp),
        text = jokeText,
        style = TextStyle(
            fontFamily = roboRegular
        )
    )
}
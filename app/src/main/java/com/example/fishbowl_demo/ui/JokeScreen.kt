package com.example.fishbowl_demo.ui

import androidx.compose.foundation.layout.padding
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.Surface
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.runtime.collectAsState
import androidx.compose.ui.Modifier
import androidx.compose.ui.text.TextStyle
import androidx.compose.ui.tooling.preview.Preview
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import androidx.navigation.NavController
import com.example.fishbowl_demo.data.model.Joke
import com.example.fishbowl_demo.ui.theme.roboRegular
import com.example.fishbowl_demo.viewmodel.JokeViewModel


@Composable
fun JokeScreen(
    jokeId: Int?,
    viewModel: JokeViewModel,
    navController: NavController,
) {
    viewModel.navController = navController
    viewModel.setJoke(jokeId)

    JokeState(viewModel)
}

@Preview
@Composable
fun JokeScreenPreview() {
    MaterialTheme {
        Surface {
            JokeBody(Joke.example)
        }
    }
}

@Composable
fun JokeState(
    viewModel: JokeViewModel,
) {
    val jokeState = viewModel.jokeFlow.collectAsState()
    JokeBody(jokeState.value)
}

@Composable
fun JokeBody(
    joke: Joke,
) {
    Text(
        modifier = Modifier.padding(16.dp),
        text = joke.jokeText,
        style = TextStyle(
            fontFamily = roboRegular,
            fontSize = 30.sp,
        )
    )
}
package com.example.fishbowl_demo.ui

import androidx.compose.foundation.Image
import androidx.compose.foundation.clickable
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.Spacer
import androidx.compose.foundation.layout.padding
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.Surface
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.runtime.collectAsState
import androidx.compose.ui.Modifier
import androidx.compose.ui.res.colorResource
import androidx.compose.ui.res.painterResource
import androidx.compose.ui.text.TextStyle
import androidx.compose.ui.tooling.preview.Preview
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import androidx.navigation.NavController
import com.example.fishbowl_demo.R
import com.example.fishbowl_demo.data.model.Joke
import com.example.fishbowl_demo.ui.components.FavoriteButton
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
    JokeBody(
        joke = jokeState.value,
        onFavoriteAction = { viewModel.onFavoriteAction() }
    )
}

@Composable
fun JokeBody(
    joke: Joke,
    onFavoriteAction: () -> Unit = {},
) {
    Column {
        QuoteRow()
        Text(
            modifier = Modifier.padding(16.dp),
            text = joke.jokeText,
            style = TextStyle(
                fontFamily = roboRegular,
                fontSize = 30.sp,
            )
        )
        BottomRow(
            isFavorite = joke.isFavorite ?: false,
            onFavoriteAction = onFavoriteAction,
        )
    }
}

@Composable
fun QuoteRow() {
    Row {
        Image(
            modifier = Modifier.padding(16.dp),
            painter = painterResource(R.drawable.format_quote_open),
            contentDescription = "opening quote"
        )
        Spacer(modifier = Modifier.weight(1f))
        Image(
            modifier = Modifier.padding(16.dp),
            painter = painterResource(R.drawable.format_quote_close),
            contentDescription = "opening quote"
        )
    }
}

@Composable
fun BottomRow(
    isFavorite: Boolean,
    onFavoriteAction: () -> Unit,
) {
    Row {
        Spacer(modifier = Modifier.weight(1f))
        Box(
            modifier = Modifier
                .padding(16.dp)
                .clickable { onFavoriteAction() }
        ) {
            FavoriteButton(
                this,
                isFavorite = isFavorite,
                tint = colorResource(R.color.black)
            )
        }
    }
}
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
import androidx.compose.runtime.State
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
import com.example.fishbowl_demo.data.model.JokeCategory
import com.example.fishbowl_demo.ui.components.FavoriteButton
import com.example.fishbowl_demo.ui.components.JokeCategory
import com.example.fishbowl_demo.ui.theme.roboRegular
import com.example.fishbowl_demo.viewmodel.JokeViewModel
import kotlinx.coroutines.flow.flowOf


@Composable
fun JokeScreen(
    jokeId: Int?,
    viewModel: JokeViewModel,
    navController: NavController,
) {
    viewModel.navController = navController
    viewModel.setJoke(jokeId)

    JokeState(
        jokeState = viewModel.jokeFlow.collectAsState(),
        onFavoriteAction = { viewModel.onFavoriteAction() },
    )
}

@Preview
@Composable
fun JokeScreenPreview() {
    val state = flowOf<Joke>().collectAsState(Joke.example)
    MaterialTheme {
        Surface {
            JokeState(
                state,
                {},
            )
        }
    }
}

@Composable
fun JokeState(
    jokeState: State<Joke>,
    onFavoriteAction: () -> Unit,
) {
    val joke = jokeState.value
    val jokeCategory = joke.category ?: JokeCategory.Undefined
    JokeBody(
        jokeText = joke.jokeText,
        isFavorite = joke.isFavorite ?: false,
        jokeCategory = jokeCategory,
        onFavoriteAction = onFavoriteAction
    )
}

@Composable
fun JokeBody(
    jokeText: String,
    isFavorite: Boolean,
    jokeCategory: JokeCategory,
    onFavoriteAction: () -> Unit = {},
) {
    Column(
        modifier = Modifier.padding(16.dp)
    ) {
        QuoteRow()
        Text(
            modifier = Modifier
                .padding(top = 12.dp),
            text = jokeText,
            style = TextStyle(
                fontFamily = roboRegular,
                fontSize = 30.sp,
            )
        )
        BottomRow(
            jokeCategory = jokeCategory,
            isFavorite = isFavorite,
            onFavoriteAction = onFavoriteAction,
        )
    }
}

@Composable
fun QuoteRow() {
    Row {
        Image(
            painter = painterResource(R.drawable.format_quote_open),
            contentDescription = "opening quote"
        )
        Spacer(modifier = Modifier.weight(1f))
        Image(
            painter = painterResource(R.drawable.format_quote_close),
            contentDescription = "opening quote"
        )
    }
}

@Composable
fun BottomRow(
    jokeCategory: JokeCategory,
    isFavorite: Boolean,
    onFavoriteAction: () -> Unit,
) {
    Row(
        modifier = Modifier
            .padding(top = 12.dp),
    ) {
        JokeCategory(jokeCategory)
        Spacer(modifier = Modifier.weight(1f))
        Box(
            modifier = Modifier
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
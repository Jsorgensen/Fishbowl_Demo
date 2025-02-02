package com.example.fishbowl_demo.ui.components

import androidx.compose.foundation.background
import androidx.compose.foundation.clickable
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.padding
import androidx.compose.material3.HorizontalDivider
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.runtime.State
import androidx.compose.ui.Modifier
import androidx.compose.ui.res.colorResource
import androidx.compose.ui.text.TextStyle
import androidx.compose.ui.unit.dp
import com.example.fishbowl_demo.R
import com.example.fishbowl_demo.data.model.Joke
import com.example.fishbowl_demo.data.model.JokeCategory
import com.example.fishbowl_demo.ui.theme.roboRegular


@Composable
fun JokesList(
    modifier: Modifier,
    jokesState: State<List<Joke>>,
    onSelected: (Joke) -> Unit,
    onFavorite: (Joke) -> Unit,
    onBottomReached: (() -> Unit)? = null,
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
    val jokeCategory = joke.category ?: JokeCategory.Any
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
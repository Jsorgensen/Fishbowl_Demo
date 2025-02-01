package com.example.fishbowl_demo.ui.components

import androidx.compose.foundation.Image
import androidx.compose.foundation.layout.BoxScope
import androidx.compose.runtime.Composable
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.graphics.ColorFilter
import androidx.compose.ui.res.painterResource
import com.example.fishbowl_demo.R


@Composable
fun FavoriteButton(
    scope: BoxScope,
    isFavorite: Boolean,
    tint: Color,
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
    scope.apply {
        Image(
            modifier = Modifier.align(Alignment.Center),
            painter = painterResource(drawableId),
            contentDescription = contentDescription,
            colorFilter = ColorFilter.tint(tint)
        )
    }
}
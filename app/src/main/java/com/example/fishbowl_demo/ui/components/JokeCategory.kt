package com.example.fishbowl_demo.ui.components

import androidx.compose.foundation.Image
import androidx.compose.foundation.background
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.heightIn
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.ColorFilter
import androidx.compose.ui.res.colorResource
import androidx.compose.ui.res.painterResource
import androidx.compose.ui.text.TextStyle
import androidx.compose.ui.tooling.preview.Preview
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import com.example.fishbowl_demo.data.model.JokeCategory
import com.example.fishbowl_demo.ui.theme.roboRegular

@Composable
fun JokeCategory(
    category: JokeCategory,
) {
    val iconId = category.categoryIconId
    val tint = colorResource(category.categoryColorId)
    Row(
        modifier = Modifier
            .background(
                color = tint.copy(alpha = 0.15f),
                shape = RoundedCornerShape(24.dp)
            )
            .padding(12.dp)
    ) {
        Image(
            modifier = Modifier
                .heightIn(max = 16.dp),
            painter = painterResource(iconId),
            contentDescription = category.name,
            colorFilter = ColorFilter.tint(tint)
        )
        Text(
            modifier = Modifier
                .align(Alignment.CenterVertically)
                .padding(horizontal = 4.dp),
            text = category.name,
            style = TextStyle(
                fontFamily = roboRegular,
                fontSize = 14.sp,
                color = tint,
            )
        )
    }
}

@Preview
@Composable
fun JokeCategoryPreview() {
    JokeCategory(
        JokeCategory.Spooky
    )
}
package com.example.fishbowl_demo.ui.components

import androidx.compose.foundation.Image
import androidx.compose.foundation.background
import androidx.compose.foundation.clickable
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.RowScope
import androidx.compose.foundation.layout.Spacer
import androidx.compose.foundation.layout.heightIn
import androidx.compose.foundation.layout.padding
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.graphics.ColorFilter
import androidx.compose.ui.res.colorResource
import androidx.compose.ui.res.painterResource
import androidx.compose.ui.res.stringResource
import androidx.compose.ui.text.TextStyle
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.tooling.preview.Preview
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import com.example.fishbowl_demo.R
import com.example.fishbowl_demo.ui.theme.roboRegular


@Composable
fun HeaderRow(
    titleStringId: Int,
    displayBack: Boolean = false,
    onBackClicked: (() -> Unit)? = null,
    actions: (@Composable RowScope.() -> Unit)? = null,
) {
    val title = stringResource(titleStringId)
    Row(
        modifier = Modifier
            .background(color = colorResource(R.color.grey_100))
            .padding(12.dp),
    ) {
        if (displayBack) {
            Text(
                modifier = Modifier
                    .padding(end = 24.dp)
                    .align(Alignment.CenterVertically)
                    .clickable { onBackClicked?.invoke() },
                text = "‹",
                style = TextStyle(
                    fontSize = 18.sp,
                    fontFamily = roboRegular,
                    fontWeight = FontWeight.Bold,
                )
            )
        }
        Text(
            modifier = Modifier
                .align(Alignment.CenterVertically),
            text = title,
            style = TextStyle(
                fontSize = 14.sp,
                fontFamily = roboRegular,
                fontWeight = FontWeight.Bold,
            )
        )
        Spacer(modifier = Modifier.weight(1f))
        actions?.invoke(this)
    }
}

@Preview
@Composable
fun PreviewHeaderRow() {
    HeaderRow(
        titleStringId = R.string.jokes,
        displayBack = true,
        actions = {
            HeaderAction(R.drawable.view_grid_outline)
            HeaderAction(R.drawable.heart)
        }
    )
}

@Composable
fun HeaderAction(
    iconId: Int,
    onClick: (() -> Unit)? = null,
) {
    Image(
        modifier = Modifier
            .padding(12.dp)
            .heightIn(max = 24.dp)
            .clickable { onClick?.invoke() },
        painter = painterResource(iconId),
        colorFilter = ColorFilter.tint(Color.Black),
        contentDescription = "Joke Category Selection"
    )
}
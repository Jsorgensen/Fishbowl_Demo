package com.example.fishbowl_demo.ui.components

import androidx.compose.foundation.Image
import androidx.compose.foundation.background
import androidx.compose.foundation.clickable
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.Spacer
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.lazy.LazyColumn
import androidx.compose.material3.Surface
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.ColorFilter
import androidx.compose.ui.res.colorResource
import androidx.compose.ui.res.painterResource
import androidx.compose.ui.text.TextStyle
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.tooling.preview.Preview
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import com.example.fishbowl_demo.R
import com.example.fishbowl_demo.data.model.JokeCategory
import com.example.fishbowl_demo.ui.theme.roboRegular


@Composable
fun CategoryList(
    categories: List<JokeCategory>,
    selectedCategory: JokeCategory?,
    onCategorySelected: (JokeCategory) -> Unit,
) {
    LazyColumn {
        items(categories.size) { index ->
            val category = categories[index]
            CategoryListItem(
                category = category,
                isSelected = category == selectedCategory,
                onCategorySelected = onCategorySelected,
            )
        }
    }
}

@Preview
@Composable
fun PreviewCategoryList() {
    val categories = JokeCategory.entries.toList()
    Surface {
        CategoryList(categories, JokeCategory.Misc, {})
    }
}

@Composable
fun CategoryListItem (
    category: JokeCategory,
    isSelected: Boolean,
    onCategorySelected: (JokeCategory) -> Unit,
) {
    val backgroundColor = colorResource(if(isSelected) R.color.grey_100 else R.color.white)
    val iconColor = colorResource(if(isSelected) R.color.black else R.color.unselected_grey)

    Row(
        modifier = Modifier
            .fillMaxWidth()
            .background(backgroundColor)
            .padding(16.dp)
            .clickable { onCategorySelected(category) },
    ) {
        Text(
            modifier = Modifier.align(Alignment.CenterVertically),
            text = category.name,
            style = TextStyle(
                fontSize = 16.sp,
                fontFamily = roboRegular,
                fontWeight = FontWeight.Bold,
            )
        )
        Spacer(Modifier.weight(1f))
        Image(
            painter = painterResource(category.categoryIconId),
            contentDescription = "${category.name} icon",
            colorFilter = ColorFilter.tint(iconColor)
        )
    }
}
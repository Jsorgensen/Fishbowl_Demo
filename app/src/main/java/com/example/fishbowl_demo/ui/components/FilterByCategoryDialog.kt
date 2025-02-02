package com.example.fishbowl_demo.ui.components

import android.util.Log
import androidx.compose.material3.BasicAlertDialog
import androidx.compose.material3.ExperimentalMaterial3Api
import androidx.compose.runtime.Composable
import androidx.compose.ui.tooling.preview.Preview
import com.example.fishbowl_demo.data.model.JokeCategory


@OptIn(ExperimentalMaterial3Api::class)
@Composable
fun FilterByCategoryDialog(
    showDialog: Boolean = false,
    selectedCategory: JokeCategory? = null,
    onDismiss: () -> Unit = {},
    onCategorySelected: (JokeCategory) -> Unit = {},
) {
    Log.d("FilterByCategoryDialog", "showDialog: $showDialog, selectedCategory: $selectedCategory")
    if (showDialog) {
        val categories = JokeCategory.entries.toList()
        BasicAlertDialog(
            onDismissRequest = onDismiss,
        ) {
            CategoryList(
                categories,
                selectedCategory,
                onCategorySelected,
            )
        }
    }
}

@Preview
@Composable
fun PreviewFilterByCategoryDialog() {
    FilterByCategoryDialog(true)
}
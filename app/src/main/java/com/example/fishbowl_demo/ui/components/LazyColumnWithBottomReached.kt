package com.example.fishbowl_demo.ui.components

import androidx.compose.foundation.layout.PaddingValues
import androidx.compose.foundation.lazy.LazyColumn
import androidx.compose.foundation.lazy.LazyListScope
import androidx.compose.foundation.lazy.rememberLazyListState
import androidx.compose.runtime.Composable
import androidx.compose.runtime.LaunchedEffect
import androidx.compose.runtime.derivedStateOf
import androidx.compose.runtime.getValue
import androidx.compose.runtime.remember
import androidx.compose.ui.Modifier
import androidx.compose.ui.unit.dp

@Composable
fun LazyColumnWithBottomReached(
    modifier: Modifier,
    onBottomReached: () -> Unit,
    content: LazyListScope.() -> Unit,
) {
    val listState = rememberLazyListState()
    val isAtBottom by remember {
        derivedStateOf {
            val layoutInfo = listState.layoutInfo
            val visibleItemsInfo = layoutInfo.visibleItemsInfo
            if (layoutInfo.totalItemsCount == 0) {
                false
            } else {
                val lastVisibleItem = visibleItemsInfo.lastOrNull()
                    ?: return@derivedStateOf false

                lastVisibleItem.index == layoutInfo.totalItemsCount - 1
            }
        }
    }

    LaunchedEffect(isAtBottom) {
        if (isAtBottom) {
            onBottomReached()
        }
    }

    LazyColumn(
        modifier = modifier,
        state = listState,
    ) {
        content()
    }
}
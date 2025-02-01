package com.example.fishbowl_demo.ui.components

import androidx.compose.animation.core.Animatable
import androidx.compose.animation.core.tween
import androidx.compose.foundation.background
import androidx.compose.foundation.clickable
import androidx.compose.foundation.gestures.Orientation
import androidx.compose.foundation.gestures.draggable
import androidx.compose.foundation.gestures.rememberDraggableState
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.BoxScope
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.RowScope
import androidx.compose.foundation.layout.fillMaxHeight
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.offset
import androidx.compose.foundation.layout.width
import androidx.compose.material3.MaterialTheme
import androidx.compose.runtime.Composable
import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.remember
import androidx.compose.runtime.rememberCoroutineScope
import androidx.compose.runtime.setValue
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.platform.LocalDensity
import androidx.compose.ui.unit.IntOffset
import androidx.compose.ui.unit.dp
import kotlinx.coroutines.launch
import kotlin.math.roundToInt


@Composable
fun SwipeRow(
    modifier: Modifier = Modifier,
    actions: @Composable RowScope.() -> Unit,
    onAction: () -> Unit,
    content: @Composable () -> Unit
) {
    val scope = rememberCoroutineScope()
    val density = LocalDensity.current
    val actionWidth = 100.dp
    val actionWidthPx = with(density) { actionWidth.toPx() }
    val swipeOffset = remember { Animatable(0f) }
    var isRevealed by remember { mutableStateOf(false) }

    Box(modifier = modifier) {
        // Actions
        Row(
            modifier = Modifier
                .fillMaxHeight()
                .align(Alignment.CenterEnd)
                .offset { IntOffset(x = (actionWidthPx + swipeOffset.value).roundToInt(), y = 0) }
                .width(actionWidth)
                .clickable {
                    if (isRevealed) {
                        scope.launch {
                            swipeOffset.animateTo(
                                targetValue = 0f,
                                animationSpec = tween(durationMillis = 300)
                            )
                            isRevealed = false
                        }
                    }
                    onAction()
                },
            content = actions
        )

        // Content
        Box(
            modifier = Modifier
                .fillMaxWidth()
                .offset { IntOffset(x = swipeOffset.value.roundToInt(), y = 0) }
                .background(MaterialTheme.colorScheme.surface)
                .draggable(
                    orientation = Orientation.Horizontal,
                    state = rememberDraggableState { delta ->
                        scope.launch {
                            val newOffset = swipeOffset.value + delta
                            if (newOffset <= 0 && newOffset >= -actionWidthPx) {
                                swipeOffset.snapTo(newOffset)
                            }
                        }
                    },
                    onDragStopped = { velocity ->
                        scope.launch {
                            val targetOffset = if (swipeOffset.value < -actionWidthPx / 2) {
                                -actionWidthPx
                            } else {
                                0f
                            }
                            swipeOffset.animateTo(
                                targetValue = targetOffset,
                                animationSpec = tween(durationMillis = 300)
                            )
                            isRevealed = targetOffset == -actionWidthPx
                        }
                    }
                )
        ) {
            content()
        }
    }
}

@Composable
fun SwipeButton(
    content: @Composable BoxScope.() -> Unit
) {
    Box(
        modifier = Modifier
            .fillMaxSize()
    ) {
        content()
    }
}
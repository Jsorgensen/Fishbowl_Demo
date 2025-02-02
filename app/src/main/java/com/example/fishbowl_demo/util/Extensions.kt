package com.example.fishbowl_demo.util

import kotlinx.coroutines.CoroutineScope
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.launch
import kotlinx.coroutines.withContext


val coroutineScope = CoroutineScope(Dispatchers.Default)

fun CoroutineScope.launchIO(block: suspend CoroutineScope.() -> Unit) {
    launch(Dispatchers.IO) {
        block()
    }
}

fun CoroutineScope.launchDefault(block: suspend CoroutineScope.() -> Unit) {
    launch(Dispatchers.Default) {
        block()
    }
}
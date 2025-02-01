package com.example.fishbowl_demo.util

import kotlinx.coroutines.CoroutineScope
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.launch


val coroutineScope: CoroutineScope = CoroutineScope(Dispatchers.Default)

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
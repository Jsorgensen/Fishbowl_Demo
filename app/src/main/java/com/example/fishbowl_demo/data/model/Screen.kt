package com.example.fishbowl_demo.data.model

enum class Screen {
    Jokes,
    Joke,
    ;

    val route: String
        get() = when(this) {
            Jokes -> "jokes"
            Joke -> "joke"
        }
}
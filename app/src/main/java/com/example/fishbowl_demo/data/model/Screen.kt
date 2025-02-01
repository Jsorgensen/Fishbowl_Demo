package com.example.fishbowl_demo.data.model

enum class Screen {
    Jokes,
    Joke,
    Favorites,
    ;

    val route: String
        get() = when(this) {
            Jokes -> "jokes"
            Joke -> "joke"
            Favorites -> "favorites"

        }
}
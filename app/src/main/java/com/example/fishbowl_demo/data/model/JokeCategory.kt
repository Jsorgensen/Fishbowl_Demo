package com.example.fishbowl_demo.data.model

import com.example.fishbowl_demo.R

enum class JokeCategory {
    Pun,
    Spooky,
    Christmas,
    Programming,
    Misc,
    Any,
    Dark,
    Undefined,
    ;

    val categoryIconId: Int
        get() = when (this) {
            Pun -> R.drawable.emoticon_happy_outline
            Spooky -> R.drawable.ghost_outline
            Christmas -> R.drawable.gift_outline
            Programming -> R.drawable.apple_keyboard_command
            Misc -> R.drawable.cards_diamond_outline
            Any -> R.drawable.view_grid_outline
            Dark -> R.drawable.weather_night
            Undefined -> R.drawable.view_grid_outline
        }

    val categoryColorId: Int
        get() = when (this) {
            Pun -> R.color.pink_500
            Spooky -> R.color.orange_500
            Christmas -> R.color.red_500
            Programming -> R.color.green_500
            Misc -> R.color.blue_500
            Any -> R.color.indigo_500
            Dark -> R.color.purple_500
            Undefined -> R.color.black
        }
}
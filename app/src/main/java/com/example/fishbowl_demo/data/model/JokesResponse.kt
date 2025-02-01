package com.example.fishbowl_demo.data.model

data class JokesResponse(
    val error: Boolean,
    val amount: Int,
    val jokes: List<Joke>,
)

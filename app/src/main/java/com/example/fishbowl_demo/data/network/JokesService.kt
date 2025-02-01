package com.example.fishbowl_demo.data.network

import com.example.fishbowl_demo.data.model.Joke
import javax.inject.Inject

class JokesService @Inject constructor(
    private val jokesApi: JokesApi,
) {

    suspend fun getJokes(
        amount: Int = jokeAmountRequestLimit
    ): List<Joke>? {
        val validAmount = if (amount > jokeAmountRequestLimit) jokeAmountRequestLimit else amount
        return jokesApi.getJokes(validAmount)
            .execute()
            .body()?.jokes
    }

    companion object {
        private const val jokeAmountRequestLimit = 10
    }

}
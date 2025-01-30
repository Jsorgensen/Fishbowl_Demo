package com.example.fishbowl_demo.data.network

import com.example.fishbowl_demo.data.model.Joke
import javax.inject.Inject

class JokesService @Inject constructor(
    private val jokesApi: JokesApi,
) {

    suspend fun getJoke(): Joke? {
        val response = jokesApi.getJoke().execute()
        return response.body()
    }

}
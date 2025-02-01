package com.example.fishbowl_demo.data.network

import com.example.fishbowl_demo.data.model.Joke
import retrofit2.Call
import retrofit2.http.GET

interface JokesApi {

    @GET("/joke/Any?format=json")
    fun getJoke(): Call<Joke>

}
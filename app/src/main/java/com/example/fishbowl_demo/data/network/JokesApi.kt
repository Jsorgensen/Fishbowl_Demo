package com.example.fishbowl_demo.data.network

import com.example.fishbowl_demo.data.model.JokesResponse
import retrofit2.Call
import retrofit2.http.GET
import retrofit2.http.Query

interface JokesApi {

    @GET("/joke/Any?safe-mode&")
    fun getJokes(
        @Query("amount") amount: Int,
        @Query("format") format: String = "json",
    ): Call<JokesResponse>

}
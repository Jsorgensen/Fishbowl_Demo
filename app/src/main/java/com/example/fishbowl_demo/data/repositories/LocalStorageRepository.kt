package com.example.fishbowl_demo.data.repositories

import com.example.fishbowl_demo.data.database.JokeEntity
import com.example.fishbowl_demo.data.database.JokesDatabase
import com.example.fishbowl_demo.data.model.Joke
import com.google.gson.Gson
import kotlinx.coroutines.flow.Flow
import kotlinx.coroutines.flow.first
import kotlinx.coroutines.flow.map
import javax.inject.Inject

class LocalStorageRepository @Inject constructor(
    private val database: JokesDatabase,
    private val gson: Gson,
) {

    val jokesFlow: Flow<List<Joke>> = database.jokeDao().getAllJokes()
        .map { entities ->
            entities.map { entity ->
                val json = entity.json
                gson.fromJson(json, Joke::class.java)
                    .apply { isFavorite = true }
            }
        }


    suspend fun removeJoke(joke: Joke) {
        val jokeEntity = getEntityByExternalId(joke.id)
        if (jokeEntity != null) database.jokeDao().delete(jokeEntity)
    }

    suspend fun addJoke(joke: Joke) {
        insertOrUpdate(joke)
    }

    private suspend fun getEntityByExternalId(externalId: Int?): JokeEntity? {
        externalId ?: return null
        return database.jokeDao().getJoke(externalId).first()
    }

    private suspend fun insertOrUpdate(joke: Joke) {
        val jokeEntity = getEntityByExternalId(joke.id)
        if (jokeEntity == null) insertJoke(joke) else updateJoke(jokeEntity, joke)
    }

    private suspend fun insertJoke(joke: Joke) {
        val jokeEntity = convertJokeToJokeEntity(joke)
        database.jokeDao().insert(jokeEntity)
    }

    private suspend fun updateJoke(
        jokeEntity: JokeEntity,
        joke: Joke,
    ) {
        val updateEntity = convertJokeToJokeEntity(joke, jokeEntity.id)
        database.jokeDao().update(updateEntity)
    }

    private fun convertJokeToJokeEntity(
        joke: Joke,
        id: Int = 0,
    ): JokeEntity {
        val externalId = joke.id ?: -1
        val json = gson.toJson(joke)

        return JokeEntity(
            id = id,
            json = json,
            externalId = externalId,
        )
    }

}
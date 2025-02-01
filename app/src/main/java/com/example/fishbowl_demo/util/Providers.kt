package com.example.fishbowl_demo.util

import android.content.Context
import com.example.fishbowl_demo.data.network.JokesService
import com.example.fishbowl_demo.repositories.JokesRepository
import com.example.fishbowl_demo.repositories.LocalStorageRepository
import com.example.fishbowl_demo.repositories.dataStore
import com.google.gson.Gson
import dagger.Module
import dagger.Provides
import dagger.hilt.InstallIn
import dagger.hilt.android.qualifiers.ApplicationContext
import dagger.hilt.components.SingletonComponent
import retrofit2.Retrofit

@Module
@InstallIn(SingletonComponent::class)
class ProvidersModule {

    @Provides
    fun providesJokesService(): JokesService {
        return Retrofit.Builder()
            .baseUrl("https://v2.jokeapi.dev")
            .build()
            .create(JokesService::class.java)
    }

    @Provides
    fun providesGson(): Gson {
        return Gson()
    }

    @Provides
    fun providesLocalStorageRepository(
        @ApplicationContext context: Context,
    ): LocalStorageRepository {
        return LocalStorageRepository(
            dataStore = context.dataStore,
            gson = providesGson(),
        )
    }

    @Provides
    fun providesJokesRepository(
        @ApplicationContext context: Context,
    ): JokesRepository {
        val jokesService = providesJokesService()
        val localStorageRepository = providesLocalStorageRepository(context)

        return JokesRepository(
            jokesService = jokesService,
            localStorageRepository = localStorageRepository,
        )
    }

}
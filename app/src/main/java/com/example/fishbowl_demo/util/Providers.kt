package com.example.fishbowl_demo.util

import android.content.Context
import androidx.room.Room
import com.example.fishbowl_demo.data.database.JokesDatabase
import com.example.fishbowl_demo.data.network.JokesApi
import com.example.fishbowl_demo.data.network.JokesService
import com.example.fishbowl_demo.data.repositories.JokesRepository
import com.example.fishbowl_demo.data.repositories.LocalStorageRepository
import com.google.gson.Gson
import dagger.Module
import dagger.Provides
import dagger.hilt.InstallIn
import dagger.hilt.android.qualifiers.ApplicationContext
import dagger.hilt.components.SingletonComponent
import retrofit2.Retrofit
import retrofit2.converter.gson.GsonConverterFactory
import javax.inject.Singleton

@Module
@InstallIn(SingletonComponent::class)
class ProvidersModule {

    @Provides
    @Singleton
    fun providesJokesService(): JokesService {
        val gson = providesGson()
        val jokesApi = Retrofit.Builder()
            .baseUrl("https://v2.jokeapi.dev")
            .addConverterFactory(GsonConverterFactory.create(gson))
            .build()
            .create(JokesApi::class.java)
        return JokesService(jokesApi)
    }

    @Provides
    @Singleton
    fun providesGson(): Gson {
        return Gson()
    }

    @Provides
    @Singleton
    fun providesJokesDatabase(
        @ApplicationContext context: Context,
    ): JokesDatabase {
        return Room.databaseBuilder(
            context.applicationContext,
            JokesDatabase::class.java,
            "joke_database"
        )
            .fallbackToDestructiveMigration()
            .build()
    }

    @Provides
    @Singleton
    fun providesLocalStorageRepository(
        @ApplicationContext context: Context,
    ): LocalStorageRepository {
        return LocalStorageRepository(
            database = providesJokesDatabase(context),
            gson = providesGson(),
        )
    }

    @Provides
    @Singleton
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
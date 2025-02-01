package com.example.fishbowl_demo.data.database

import androidx.room.Database
import androidx.room.RoomDatabase

@Database(entities = [JokeEntity::class], version = 1,)
abstract class JokesDatabase: RoomDatabase() {

    abstract fun jokeDao(): JokeDao

}
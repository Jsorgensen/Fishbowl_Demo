package com.example.fishbowl_demo.data.database

import androidx.room.Dao
import androidx.room.Delete
import androidx.room.Insert
import androidx.room.OnConflictStrategy
import androidx.room.Query
import androidx.room.Update
import kotlinx.coroutines.flow.Flow

@Dao
interface JokeDao {

    @Insert(onConflict = OnConflictStrategy.IGNORE)
    suspend fun insert(entity: JokeEntity)

    @Update
    suspend fun update(entity: JokeEntity)

    @Delete
    suspend fun delete(entity: JokeEntity)

    @Query("Select * from jokes where external_id = :externalId")
    fun getJoke(externalId: Int): Flow<JokeEntity?>

    @Query("Select * from jokes")
    fun getAllJokes(): Flow<List<JokeEntity>>

}
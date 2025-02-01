package com.example.fishbowl_demo.data.database

import androidx.room.ColumnInfo
import androidx.room.Entity
import androidx.room.PrimaryKey

@Entity(tableName = "jokes")
data class JokeEntity(
    @PrimaryKey(autoGenerate = true) val id: Int = 0,
    @ColumnInfo(name = "json") val json: String,
    @ColumnInfo(name = "external_id") val externalId: Int = -1,
)

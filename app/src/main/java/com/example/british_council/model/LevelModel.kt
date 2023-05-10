package com.example.british_council.model

import androidx.room.Entity
import androidx.room.PrimaryKey

@Entity(tableName = "level_table")
data class LevelModel (
    val name:String,
    val desc:String,
    @PrimaryKey(autoGenerate = true)
    var id: Long = 0,
)
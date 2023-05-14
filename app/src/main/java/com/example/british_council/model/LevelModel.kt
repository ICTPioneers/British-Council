package com.example.british_council.model

import androidx.room.Entity
import androidx.room.PrimaryKey

@Entity
data class LevelModel (
    @PrimaryKey(autoGenerate = true)
    var id: Long = 0,
    val name:String,
    val desc:String,
)
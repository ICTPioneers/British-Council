package com.example.british_council.model

import androidx.room.PrimaryKey

class Level {
    var id: Long = 0
    val name : String? = null
    val desc : String? = null
    val bg_image : String? = null
    val audio : String? = null
    val text : ArrayList<Text>? = null
}
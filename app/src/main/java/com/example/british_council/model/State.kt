package com.example.british_council.model

import androidx.room.Entity
import androidx.room.Ignore
import androidx.room.PrimaryKey

@Entity(tableName = "state")
class State {
    @PrimaryKey(autoGenerate = true)
    var id_main :Int? = null
    var id_level: Int? = null
    var state : Int? = null


    @Ignore
    constructor(id_main: Int?, id_level: Int?, state: Int?) {
        this.id_main = id_main
        this.id_level = id_level
        this.state = state
    }

    constructor(id_level: Int?, state: Int?) {
        this.id_level = id_level
        this.state = state
    }


}

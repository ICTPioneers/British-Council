package com.example.british_council.model

import androidx.room.Entity
import androidx.room.Ignore
import androidx.room.PrimaryKey

@Entity(tableName = "state")
class State {
    @PrimaryKey(autoGenerate = true)
    var id :Int? = null
    var state : Int? = null


    @Ignore
    constructor(id_main: Int?, state: Int?) {
        this.id = id_main
        this.state = state
    }

    constructor( state: Int?) {
        this.state = state
    }


}

package com.example.british_council.model

import androidx.room.Entity
import androidx.room.Ignore
import androidx.room.PrimaryKey

@Entity
class Text {
    @PrimaryKey(autoGenerate = true)
    var id_main :Int? = null
    var text : String? = null
    var start_time: Int? = null
    var end_time: Int? = null


    constructor(text: String?, start_time: Int?, end_time: Int? ) {
        this.text = text
        this.start_time = start_time
        this.end_time = end_time
    }

    @Ignore
    constructor(id_main: Int?, text: String?, start_time: Int?, end_time: Int?) {
        this.id_main = id_main
        this.text = text
        this.start_time = start_time
        this.end_time = end_time
    }


}
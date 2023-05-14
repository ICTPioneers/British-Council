package com.example.british_council.model

import androidx.room.Entity
import androidx.room.Ignore
import androidx.room.PrimaryKey

@Entity
class Level {
    @PrimaryKey(autoGenerate = true)
    var id_main :Int? = null
    var id: Int? = null
    var name : String? = null
    var desc : String? = null
    var bg_image : String? = null
    var audio : String? = null
    var text : ArrayList<Text>? = null


    constructor(id: Int?, name: String?, desc: String?, image: String?, audio: String? , text : ArrayList<Text>? ) {
        this.id = id
        this.name = name
        this.desc = desc
        this.bg_image = image
        this.audio = audio
        this.text = text
    }
    @Ignore
    constructor(id_main : Int? ,id: Int?,name: String?, desc: String?, image: String?, audio: String? , text : ArrayList<Text>? ) {
        this.id_main = id_main
        this.id = id
        this.name = name
        this.desc = desc
        this.bg_image = image
        this.audio = audio
        this.text = text
    }

    constructor()


}
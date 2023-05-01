package com.example.british_council.activity

import android.content.Intent
import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import android.widget.TextView
import com.example.british_council.R
import java.util.*
import kotlin.concurrent.timerTask

class Splash : AppCompatActivity() {
    var txt : TextView? = null

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_splash)

//        txt_british
        txt = findViewById(R.id.txt_british)
        txt?.text = "BRITISH \n COUNCIL"
        Timer().schedule(timerTask {
            startActivity(Intent(this@Splash, MainActivity::class.java))
            finish()
        }, 3000)



    }



}
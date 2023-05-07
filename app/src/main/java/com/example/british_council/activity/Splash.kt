package com.example.british_council.activity

import android.content.Intent
import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import android.view.View
import android.widget.TextView
import com.example.british_council.R
import java.util.*
import kotlin.concurrent.timerTask

class Splash : AppCompatActivity() {
    var txt : TextView? = null

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_splash)
        window.decorView.systemUiVisibility = View.SYSTEM_UI_FLAG_FULLSCREEN

//        txt_british
        Timer().schedule(timerTask {
            startActivity(Intent(this@Splash, MainActivity::class.java))
            finish()
        }, 2000)

    }



}
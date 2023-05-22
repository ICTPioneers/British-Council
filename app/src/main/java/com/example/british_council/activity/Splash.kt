package com.example.british_council.activity

import android.content.Intent
import android.os.Bundle
import android.view.View
import androidx.appcompat.app.AppCompatActivity
import com.example.british_council.R
import java.util.*
import kotlin.concurrent.timerTask

class Splash : AppCompatActivity() {

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_splash)
        window.decorView.systemUiVisibility = View.SYSTEM_UI_FLAG_FULLSCREEN

        Timer().schedule(timerTask {
            startActivity(Intent(this@Splash, MainActivity::class.java))
            finish()
        }, 2000)
    }
}
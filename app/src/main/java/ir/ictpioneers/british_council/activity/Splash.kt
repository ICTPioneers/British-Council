package ir.ictpioneers.british_council.activity

import android.content.Intent
import android.os.Bundle
import android.view.View
import androidx.appcompat.app.AppCompatActivity
import ir.ictpioneers.british_council.BuildConfig
import ir.ictpioneers.british_council.R
import ir.ictpioneers.british_council.databinding.ActivitySplashBinding
import java.util.*
import kotlin.concurrent.timerTask

class Splash : AppCompatActivity() {
    private var binding: ActivitySplashBinding? = null


    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_splash)
        window.decorView.systemUiVisibility = View.SYSTEM_UI_FLAG_FULLSCREEN

        initBinding()
        binding!!.version.text = BuildConfig.VERSION_NAME

        Timer().schedule(timerTask {
            startActivity(Intent(this@Splash, MainActivity::class.java))
            finish()
        }, 2000)
    }


    private fun initBinding() {
        binding = ActivitySplashBinding.inflate(layoutInflater)
        val view = binding!!.root
        setContentView(view)
    }
}
package com.example.british_council.activity

import android.content.Intent
import android.media.AudioManager
import android.media.MediaPlayer
import android.net.Uri
import android.os.Bundle
import android.os.Handler
import android.os.Looper
import android.text.TextUtils
import android.util.Log
import android.view.View
import android.widget.SeekBar
import android.widget.SeekBar.OnSeekBarChangeListener
import androidx.appcompat.app.AppCompatActivity
import com.example.british_council.R
import com.example.british_council.adapter.TextAdapter
import com.example.british_council.databinding.ActivityLevelBinding
import com.example.british_council.helper.App
import com.example.british_council.helper.Session
import com.example.british_council.model.Level
import com.example.british_council.model.Text
import com.google.gson.Gson


class LevelActivity : AppCompatActivity() {

    private var binding: ActivityLevelBinding? = null
    private val mHandler = Handler()
    private var mRunnable: Runnable? = null

    private var level: Level? = null
    private var customArrayAdapter: TextAdapter? = null
    private var mediaPlayer: MediaPlayer? = null
    private var mCurrentPosition: Int? = null

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_level)

        initBinding()
        checkIntent()
        saveSoundToStorage()
        setAudio()
        onClicked()
        setSeekBar()
        setRecycler()
        initHandler()
        setProgress()
        fixActive()
    }

    private fun initBinding() {
        binding = ActivityLevelBinding.inflate(layoutInflater)
        val view = binding!!.root
        setContentView(view)
    }


    private fun setAudio() {
        try {
            mediaPlayer = MediaPlayer()
            mediaPlayer!!.setAudioStreamType(AudioManager.STREAM_MUSIC)
            mediaPlayer!!.setDataSource(applicationContext, Uri.parse(level?.audio))
            mediaPlayer!!.prepare()
        } catch (ex: Exception) {
        }
    }

    private fun checkIntent() {
        if (intent.getStringExtra("level") != null) {
            var m = intent.getStringExtra("level")
            level = Gson().fromJson<Level>(m, Level::class.java)
        }
        customArrayAdapter = TextAdapter(level?.text as ArrayList<Text>? ?: ArrayList())
    }

    private fun saveSoundToStorage() {
//        App.saveFile(App.getByte(Uri.parse(level?.audio)))
        App.saveFile(App.getByte(Uri.parse("")))
    }

    private fun onClicked() {
        binding?.back?.setOnClickListener { onBackPressed() }


        binding?.start?.setOnClickListener {
            startPlayingTest()
            arrayOf(binding?.start, binding?.lottie).forEach { it!!.visibility = View.GONE }
            arrayOf(binding?.lottieSound, binding?.linearShow).forEach { it!!.visibility = View.VISIBLE }
        }

        binding?.linearShow?.setOnClickListener {
            arrayOf(binding?.recycler, binding?.lottieSound).forEach {
                it!!.visibility =
                    if (binding?.recycler?.visibility == View.VISIBLE) View.GONE else View.VISIBLE
            }
        }

        binding?.play?.setOnClickListener {
            startPlayingTest()
        }

        binding?.next?.setOnClickListener {
            startActivity(Intent(this, TaskActivity::class.java))
        }

        binding?.forward!!.setOnClickListener {
            if (mediaPlayer != null) {
                var m = mediaPlayer!!.currentPosition / 1000 + 10
                mediaPlayer?.seekTo(m * 1000)
                binding?.seekbar?.progress = m
            }
        }

        binding?.replay!!.setOnClickListener {
            if (mediaPlayer != null) {
                var f = mediaPlayer!!.currentPosition / 1000 - 10
                mediaPlayer?.seekTo(f * 1000)
                binding?.seekbar?.progress = f
            }
        }

    }

    private fun setSeekBar() {
        binding?.seekbar?.max = mediaPlayer!!.duration / 1000
        binding?.seekbar!!.setOnSeekBarChangeListener(object : OnSeekBarChangeListener {
            override fun onStopTrackingTouch(seekBar: SeekBar) {
            }

            override fun onStartTrackingTouch(seekBar: SeekBar) {
            }

            override fun onProgressChanged(seekBar: SeekBar, progress: Int, fromUser: Boolean) {
                if (mediaPlayer != null && fromUser) {
                    mediaPlayer?.seekTo(progress * 1000)
                }
            }
        })
    }


    private fun initHandler() {
        mRunnable = object : Runnable {
            override fun run() {
                if (mediaPlayer != null) {
                    customArrayAdapter?.getCurrentPos(mediaPlayer?.currentPosition!! / 1000)
                    Log.e("handler  ", "run: ${mediaPlayer?.currentPosition!! / 1000}")
                    binding?.seekbar?.progress = mediaPlayer!!.currentPosition / 1000
                    setStartAndEndTime()
                    check()
                }
                mHandler.postDelayed(this, 500)
            }
        }
        this@LevelActivity.runOnUiThread(mRunnable)
    }

    private fun setStartAndEndTime() {
        val hours: Int = mediaPlayer?.currentPosition!! / (1000 * 60 * 60)
        val minutes: Int = mediaPlayer?.currentPosition!! % (1000 * 60 * 60) / (1000 * 60)
        val seconds: Int = mediaPlayer?.currentPosition!! % (1000 * 60 * 60) % (1000 * 60) / 1000
        binding?.startTime?.text = String.format("%02d:%02d", minutes, seconds)
        var m = mediaPlayer!!.duration / 1000 / 60
        var s = mediaPlayer!!.duration / 1000 % 60
        binding?.endTime?.text = String.format("%02d:%02d", m, s)
    }


    private fun check() {
        if (mCurrentPosition == mediaPlayer?.duration!! / 1000) {
            binding?.play?.background = resources.getDrawable(R.drawable.ic_play)
            binding?.start?.text = "play"
            binding?.lottieSound?.cancelAnimation()
        }
    }


    private fun setRecycler() {
        binding?.recycler!!.adapter = customArrayAdapter
    }


    private fun startPlayingTest() {
        if (mediaPlayer != null) {
            if (mediaPlayer!!.isPlaying) {
                mediaPlayer?.pause()
                binding?.lottieSound?.cancelAnimation()
                binding?.play?.background = resources.getDrawable(R.drawable.ic_play)
                binding?.start?.text = "play"

            } else if (mediaPlayer!! != null) {
                mediaPlayer!!.start()
                binding?.lottieSound?.loop(true)
                binding?.lottieSound?.playAnimation()
                binding?.relative?.visibility = View.VISIBLE
                binding?.play?.background = resources.getDrawable(R.drawable.ic_pause)
                binding?.start?.text = "pause"
            }
        }
    }

    private fun setProgress() {
        binding?.progress?.max = Session.getInstance().getInt("length")
        binding?.progress?.setProgress(level?.id!!, true)
        binding?.progress?.animate()
        binding?.tvProgress?.text = "${level!!.id} / ${Session.getInstance().getInt("length")}"
    }


    private fun fixActive() {
//        var check = level?.active
        if (level?.active != 0 && TextUtils.isEmpty(level?.active.toString())) {
            Handler(Looper.getMainLooper()).postDelayed({
                level?.active = level?.id
                App.toast("10 second past ${level?.active}")
            }, 10000)
        } else return
    }


    override fun onStop() {
        super.onStop()
        mediaPlayer?.stop()
        if (mRunnable != null) mHandler.removeCallbacks(mRunnable!!)
    }
}

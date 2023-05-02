package com.example.british_council.activity

import android.media.MediaPlayer
import android.os.Bundle
import android.os.Handler
import android.util.Log
import android.view.View
import android.widget.*
import android.widget.SeekBar.OnSeekBarChangeListener
import androidx.appcompat.app.AppCompatActivity
import com.example.british_council.R
import java.io.IOException


class LevelActivity : AppCompatActivity() {

    private var tv_passage: TextView? = null
    private var tv_next: TextView? = null
    private var tv_start: TextView? = null
    private var tv_timeStart: TextView? = null
    private var tv_timeEnd: TextView? = null
    private var tv_back: TextView? = null
    private var seekBar: SeekBar? = null

    private var img_play: ImageView? = null
    private var img_forward: ImageView? = null
    private var img_replay: ImageView? = null

    private var relative: RelativeLayout? = null

    private var mediaPlayer: MediaPlayer? = null
    private var k = -1


    private val LOG_TAG = "AudioRecordTest"
    private val mFileName: String? = null
    private var seekBarr: SeekBar? = null
    private val startRecord: Button? = null
    private var startPlaying: Button? = null
    private var stopPlaying: Button? = null

    private var p: Int = 0


//    private fun startPlaying() {
//        if (mediaPlayer != null && mediaPlayer!!.isPlaying()) {
//            mediaPlayer?.pause()
//        } else if (mediaPlayer != null) {
//            mediaPlayer?.start()
//        } else {
//            mediaPlayer = MediaPlayer()
//            try {
//                mediaPlayer?.setDataSource(mFileName)
//                mediaPlayer?.prepare()
//                mediaPlayer?.start()
//            } catch (e: IOException) {
//                Log.e(LOG_TAG, "prepare() failed")
//            }
//        }
//    }


    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_level)
        mediaPlayer = MediaPlayer.create(this, R.raw.voice)
        initID()
        onClicked()
        var m = mediaPlayer!!.duration / 1000 / 60
        var s = mediaPlayer!!.duration / 1000 % 60
        Log.e("111", "setProgress:  $m : $s}")

        tv_timeEnd?.text = "$m:$s"


        val mHandler = Handler()
        this@LevelActivity.runOnUiThread(object : Runnable {
            override fun run() {
                if (mediaPlayer != null) {
                    val mCurrentPosition: Int = mediaPlayer?.currentPosition!! / 1000
                    seekBar?.progress = mCurrentPosition
                    Log.e("111", "setProgress:  $mCurrentPosition}")
                    tv_timeStart?.text = mCurrentPosition.toString()


                }
                mHandler.postDelayed(this, 1000)
            }
        })

        setProgress()
    }


    private fun initID() {
        tv_passage = findViewById(R.id.tv_passage)
        tv_next = findViewById(R.id.tv_next)
        tv_back = findViewById(R.id.toolbar)
        tv_start = findViewById(R.id.tv_start)
        tv_timeStart = findViewById(R.id.tv_time_start)
        tv_timeEnd = findViewById(R.id.tv_time_end)

        seekBar = findViewById(R.id.seekbar)
        img_play = findViewById(R.id.play)
        img_forward = findViewById(R.id.img_forward)
        img_replay = findViewById(R.id.img_replay)

        relative = findViewById(R.id.relative)
    }

    private fun setText() {
        tv_passage?.text =
            "Susanne: Hi, Mario. Can you help me prepare some things for the next month?\n" +
                    "\n" +
                    "Mario: OK, sure. What can I help you with?\n" +
                    "\n" +
                    "Susanne: I need to visit the customer in Germany. It’s important.\n" +
                    "\n" +
                    "Mario: What can I do to help?\n" +
                    "\n" +
                    "Susanne: Can you send an email to the customer? Ask them when I can visit them next week. Please do this first. It’s a priority and very urgent.\n" +
                    "\n" +
                    "Mario: Right. I’ll do it today.\n" +
                    "\n" +
                    "Susanne: Thanks. This next task is also important. Can you invite everyone to the next team meeting?\n" +
                    "\n" +
                    "Mario: Yes, I will.\n" +
                    "\n" +
                    "Susanne: But first you need to book a meeting room. After that, please send everyone an email about it.\n" +
                    "\n" +
                    "Mario: Yes, of course.\n" +
                    "\n" +
                    "Susanne: And fianlly, can you write a short report about our new project? I have to give a presentation to our managers next month. Please do it when you have time – sometime in the next two or three weeks. It’s not too urgent.\n" +
                    "\n" +
                    "Mario: Sure, no problem. I can do it this week.\n" +
                    "\n" +
                    "Susanne: There’s no hurry. Take your time."
    }

    private fun onClicked() {
        tv_back?.setOnClickListener { finish() }

        tv_start?.setOnClickListener {
            k++
            if (k % 2 == 0) {
                tv_start?.text = "توقف"
                tv_next?.setBackgroundDrawable(resources.getDrawable(R.drawable.item_back_next_selected))
                relative?.visibility = View.VISIBLE
                playSound()
            } else {
                tv_start?.text = "ادامه"
                mediaPlayer?.pause()
                img_play?.background = resources.getDrawable(R.drawable.ic_play)
            }
        }


//        tv_next?.setOnClickListener {
//            tv_start?.visibility = View.GONE
//            tv_passage?.visibility = View.VISIBLE
//            setText()
//            mediaPlayer?.stop()
//            playSound()
//        }

    }


    private fun setProgress() {
        seekBar?.max = mediaPlayer!!.duration / 1000
        Log.e("111", "setProgress: ${mediaPlayer!!.duration / 1000 / 60}")
        seekBar!!.setOnSeekBarChangeListener(object : OnSeekBarChangeListener {
            override fun onStopTrackingTouch(seekBar: SeekBar) {
            }

            override fun onStartTrackingTouch(seekBar: SeekBar) {
            }

            override fun onProgressChanged(seekBar: SeekBar, progress: Int, fromUser: Boolean) {
                if (mediaPlayer != null && fromUser) {
                    mediaPlayer?.seekTo(progress * 1000)
                    p = progress
                }
            }
        })
    }

    private fun playSound() {
        mediaPlayer = MediaPlayer.create(this, R.raw.voice)
        var m = mediaPlayer?.duration
        mediaPlayer?.start()
        img_play?.background = resources.getDrawable(R.drawable.ic_pause)
    }


    private fun stopPlaying() {
        mediaPlayer!!.release()
        mediaPlayer = null
        startPlaying!!.text = "Start playing"
    }

    private fun pausePlaying() {
        if (mediaPlayer!!.isPlaying) {
            mediaPlayer!!.pause()
        } else {
            mediaPlayer!!.start()
        }
    }

    override fun onResume() {
        super.onResume()
    }


}

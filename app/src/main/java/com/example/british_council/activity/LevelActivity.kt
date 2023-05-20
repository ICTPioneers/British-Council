package com.example.british_council.activity

import android.content.Intent
import android.media.AudioManager
import android.media.MediaPlayer
import android.net.Uri
import android.os.Bundle
import android.os.Handler
import android.os.Looper
import android.view.View
import android.widget.*
import android.widget.SeekBar.OnSeekBarChangeListener
import androidx.appcompat.app.AppCompatActivity
import com.airbnb.lottie.LottieAnimationView
import com.example.british_council.R
import com.example.british_council.adapter.CustomArrayAdapter
import com.example.british_council.helper.App
import com.example.british_council.helper.Session
import com.example.british_council.model.Level
import java.util.*


class LevelActivity : AppCompatActivity() {

    private var tv_Text: TextView? = null
    private var tv_next: TextView? = null
    private var tv_start: TextView? = null
    private var tv_timeStart: TextView? = null
    private var tv_timeEnd: TextView? = null
    private var tv_back: TextView? = null
    private var tv_showText: TextView? = null
    private var tv_progress: TextView? = null
    private var seekBar: SeekBar? = null

    private var img_play: ImageView? = null
    private var img_forward: ImageView? = null
    private var img_replay: ImageView? = null
    private var progress: ProgressBar? = null
    private var lottie: LottieAnimationView? = null
    private var lottie_sound: LottieAnimationView? = null
    private var relative: RelativeLayout? = null
    private var listView: ListView? = null
    private var linear_show: LinearLayout? = null

    private var mediaPlayer: MediaPlayer? = null
    private var arrayAdapter: CustomArrayAdapter? = null

    private var level: Level? = null
    private var pos: Int = Session.getInstance().getInt("pos")

    var mCurrentPosition : Int? = null
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_level)

        getPositionOfLevel()
        saveSoundToStorage()
        setAudio()
        initID()
        onClicked()
        setSeekBar()
        setListView(2)
        initHandler()
        setProgress()
        fixActive()
    }


    private fun initID() {
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
        listView = findViewById(R.id.listView)
        progress = findViewById(R.id.progress)
        tv_progress = findViewById(R.id.txt_progress)
        lottie = findViewById(R.id.lottie)
        tv_showText = findViewById(R.id.tv_Display_text)
        linear_show = findViewById(R.id.linear_show)
        lottie_sound = findViewById(R.id.lottie_sound)
    }

    private fun setAudio() {
        try {
            mediaPlayer = MediaPlayer()
            mediaPlayer!!.setAudioStreamType(AudioManager.STREAM_MUSIC)
            mediaPlayer!!.setDataSource(applicationContext, Uri.parse(level?.audio))
            mediaPlayer!!.prepare()
            App.toast("" + mediaPlayer?.duration!! / 1000 / 60 + " : " + mediaPlayer?.duration!! / 1000 % 60)
        } catch (ex: Exception) {
        }
    }

    private fun getPositionOfLevel() {
        level = App.database.dao.getLeve(pos + 1)
    }

    private fun saveSoundToStorage() {
//        App.saveFile(App.getByte(Uri.parse(level?.audio)))
        App.saveFile(App.getByte(Uri.parse("")))
    }

    private fun onClicked() {
        tv_back?.setOnClickListener { onBackPressed() }


        tv_start?.setOnClickListener {
            startPlayingTest()
            arrayOf( tv_start, lottie ).forEach { it!!.visibility =  View.GONE }
            arrayOf( lottie_sound ,linear_show ).forEach { it!!.visibility = View.VISIBLE }
        }

        linear_show?.setOnClickListener {
            arrayOf( listView , lottie_sound).forEach { it!!.visibility = if (listView?.visibility == View.VISIBLE) View.GONE else View.VISIBLE }
        }

        img_play?.setOnClickListener {
            startPlayingTest()
        }

        tv_next?.setOnClickListener {
            startActivity(Intent(this, TaskActivity::class.java))
        }

    }

    private fun setSeekBar() {
        seekBar?.max = mediaPlayer!!.duration / 1000
        seekBar!!.setOnSeekBarChangeListener(object : OnSeekBarChangeListener {
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
        val mHandler = Handler()
        this@LevelActivity.runOnUiThread(object : Runnable {
            override fun run() {
                if (mediaPlayer != null) {
                    setStartAndEndTime()
                    forwardSound()
                    replaySound()
                }
                mHandler.postDelayed(this, 1000)
            }
        })
    }


    private  fun setStartAndEndTime(){
        val hours: Int = mediaPlayer?.currentPosition!! / (1000 * 60 * 60)
        val minutes: Int = mediaPlayer?.currentPosition!! % (1000 * 60 * 60) / (1000 * 60)
        val seconds: Int = mediaPlayer?.currentPosition!! % (1000 * 60 * 60) % (1000 * 60) / 1000
        tv_timeStart?.text = "$minutes:$seconds"
        var m = mediaPlayer!!.duration / 1000 / 60
        var s = mediaPlayer!!.duration / 1000 % 60
        tv_timeEnd?.text = "$m:$s"
    }


    private fun replaySound(){
        img_replay!!.setOnClickListener {
            if (mediaPlayer != null) {
                mCurrentPosition = mediaPlayer!!.currentPosition / 1000 - 10
                mediaPlayer?.seekTo(mCurrentPosition!! * 1000)
                seekBar?.progress = mCurrentPosition!!
            }
        }
    }

   private fun forwardSound() {
       mCurrentPosition = mediaPlayer!!.currentPosition / 1000
       seekBar?.progress = mCurrentPosition!!

        img_forward!!.setOnClickListener {
            if (mediaPlayer != null) {
                mCurrentPosition = mediaPlayer!!.currentPosition / 1000 + 10
                mediaPlayer?.seekTo(mCurrentPosition!! * 1000)
                seekBar?.progress = mCurrentPosition!!
            }
        }

       if (mCurrentPosition == mediaPlayer?.duration!! / 1000 ) {
           img_play?.background = resources.getDrawable(R.drawable.ic_play)
           tv_start?.text = "play"
           lottie_sound?.cancelAnimation()
       }
    }


    private fun setListView(ps: Int) {
        arrayAdapter = CustomArrayAdapter(this, level!!.text!!, ps)
        listView!!.adapter = arrayAdapter
        listView!!.divider = null
    }


    private fun startPlayingTest() {
        if (mediaPlayer != null) {
            if (mediaPlayer!!.isPlaying) {
                mediaPlayer?.pause()
                lottie_sound?.cancelAnimation()
                img_play?.background = resources.getDrawable(R.drawable.ic_play)
                Toast.makeText(this, "pause", Toast.LENGTH_SHORT).show()
                tv_start?.text = "play"

            } else if (mediaPlayer!! != null) {
                mediaPlayer!!.start()
                lottie_sound?.loop(true)
                lottie_sound?.playAnimation()
                relative?.visibility = View.VISIBLE
                img_play?.background = resources.getDrawable(R.drawable.ic_pause)
                Toast.makeText(this, "play", Toast.LENGTH_SHORT).show()
                tv_start?.text = "pause"
//                tv_next?.setBackgroundDrawable(resources.getDrawable(R.drawable.item_back_next_selected))
            }
        }
    }


    private fun setProgress() {
        var status = Session.getInstance().getInt("pos")
        progress?.max = 12
        progress?.setProgress(status + 1, true)
        progress?.animate()
        tv_progress?.text = "${status + 1} / 12"
    }


    private fun fixActive() {
//        val mHandler = Handler()
//        this@LevelActivity.runOnUiThread(object : Runnable {
//            override fun run() {
//                App.toast("10 second past")
//                mHandler.postDelayed(this, 10000)
//            }
//        })

//        Timer().schedule(timerTask {
//                App.toast("10 second past")
//        }, 10000)


        Handler(Looper.getMainLooper()).postDelayed({
                         App.toast("3 second past")
        }, 3000)
    }



    override fun onStop() {
        super.onStop()
        mediaPlayer?.stop()
    }

}

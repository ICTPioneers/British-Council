package com.example.british_council.activity

import android.content.Intent
import android.media.MediaPlayer
import android.os.Bundle
import android.os.Handler
import android.util.Log
import android.view.View
import android.widget.*
import android.widget.SeekBar.OnSeekBarChangeListener
import androidx.appcompat.app.AppCompatActivity
import com.airbnb.lottie.LottieAnimationView
import com.example.british_council.PassageModel
import com.example.british_council.R
import com.example.british_council.adapter.CustomArrayAdapter
import kotlin.collections.ArrayList


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
    var arrayOfModel: ArrayList<PassageModel>? = null
    var arrayText: ArrayList<String>? = null
    private var arrayAdapter: CustomArrayAdapter? = null

    private var p = -1

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_level)

        mediaPlayer = MediaPlayer.create(this, R.raw.one_a_request_from_your_boss)
        initID()
        initArrayList()
        onClicked()
        setSeekBar()
        initHandler()
//        setListView(-9)
        setProgress()
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


    private fun onClicked() {
        tv_back?.setOnClickListener {
            mediaPlayer?.stop()
            finish()
        }


        tv_start?.setOnClickListener {
            startPlayingTest()
            tv_start!!.visibility = View.GONE
            lottie?.visibility = View.GONE
            lottie_sound?.visibility = View.VISIBLE
            linear_show?.visibility = View.VISIBLE
        }


        linear_show?.setOnClickListener {
            listView?.visibility = if (listView?.visibility == View.VISIBLE) View.GONE else View.VISIBLE
            lottie_sound?.visibility = if (lottie_sound?.visibility == View.VISIBLE) View.GONE else View.VISIBLE

        }


        img_play?.setOnClickListener {
            startPlayingTest()
        }


        tv_next?.setOnClickListener {
            startActivity(Intent(this,TaskActivity::class.java))


           /* tv_start?.visibility = View.GONE
            tv_passage?.visibility = View.VISIBLE
            setText()
            mediaPlayer?.stop()*/
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

        var m = mediaPlayer!!.duration / 1000 / 60
        var s = mediaPlayer!!.duration / 1000 % 60
        Log.e("111", "oncreate:  $m : $s}")
        tv_timeEnd?.text = "$m:$s"
    }


    private fun initHandler() {
        val mHandler = Handler()
        this@LevelActivity.runOnUiThread(object : Runnable {
            override fun run() {
                if (mediaPlayer != null) {
                    val hours: Int = mediaPlayer?.currentPosition!! / (1000 * 60 * 60)
                    val minutes: Int =
                        mediaPlayer?.currentPosition!! % (1000 * 60 * 60) / (1000 * 60)
                    val seconds: Int =
                        mediaPlayer?.currentPosition!! % (1000 * 60 * 60) % (1000 * 60) / 1000
                    val mCurrentPosition: Int = mediaPlayer?.currentPosition!! / 1000
                    seekBar?.progress = mCurrentPosition
                    Log.e("111", "handler:  $mCurrentPosition}")
                    tv_timeStart?.text = "$minutes:$seconds"

                    if (mCurrentPosition == mediaPlayer?.duration!! / 1000 - 1) {
                        img_play?.background = resources.getDrawable(R.drawable.ic_play)
                        tv_start?.text = "play"
                    }
                    fixTextColorOfListView(mCurrentPosition)
                }
                mHandler.postDelayed(this, 1000)
            }
        })
    }

    private fun fixTextColorOfListView(progress: Int) {
        for (x in arrayOfModel!!.indices) {
            Log.e("TAG", "fixTextColorOfListView: $progress")
            if (progress in arrayOfModel!![x].startTime!!..arrayOfModel!![x].endTime!!) {
//                CustomArrayAdapter(this, ArrayList(), x)
//        arrayAdapter = CustomArrayAdapter(this, arrayText!!, x)
                setListView(x)
//                    p = x
//                arrayAdapter = CustomArrayAdapter(this, ArrayList(), x)

            }
        }
    }


    private fun setListView(ps : Int) {
        arrayAdapter = CustomArrayAdapter(this, arrayText!!, ps)
//        arrayAdapter = CustomArrayAdapter(this, ArrayList(), p)
        listView!!.adapter = arrayAdapter
        listView!!.divider = null
    }

//    override fun onResume() {
//        super.onResume()
////        if(arrayAdapter != null){
//
////            arrayAdapter = CustomArrayAdapter(this, arrayText!!, p)
////        arrayAdapter!!.addList(arrayText!!)
//
////        listView!!.adapter = arrayAdapter
////        }
//    }
    private fun initArrayList() {
        arrayOfModel = ArrayList<PassageModel>()
        arrayOfModel?.add(PassageModel("Susanne: Hi, Mario. Can you help me prepare some things for the next month?", 5, 10))
        arrayOfModel?.add(PassageModel("Mario: OK, sure. What can I help you with?", 11, 13))
        arrayOfModel?.add(PassageModel("Susanne: I need to visit the customer in Germany. It’s important.", 14, 18))
        arrayOfModel?.add(PassageModel("Mario: What can I do to help?", 19, 20))
        arrayOfModel?.add(PassageModel("Susanne: Can you send an email to the customer? Ask them when I can visit them next week. Please do this first. It’s a priority and very urgent.", 21, 33))
        arrayOfModel?.add(PassageModel("Mario: Right. I’ll do it today.", 34, 35))
//        arrayOfModel?.add(PassageModel("Susanne: Thanks. This next task is also important. Can you invite everyone to the next team meeting?", 36, 44))
//        arrayOfModel?.add(PassageModel("Mario: Yes, I will.", 45, 46))
//        arrayOfModel?.add(PassageModel("Susanne: But first you need to book a meeting room. After that, please send everyone an email about it.", 47, 54))
//        arrayOfModel?.add(PassageModel("Mario: Yes, of course.", 55, 56))
//        arrayOfModel?.add(PassageModel("Susanne: And fianlly, can you write a short report about our new project? I have to give a presentation to our managers next month. Please do it when you have time – sometime in the next two or three weeks. It’s not too urgent.", 57, 74))
//        arrayOfModel?.add(PassageModel("Mario: Sure, no problem. I can do it this week.", 75, 78))
//        arrayOfModel?.add(PassageModel("Susanne: There’s no hurry. Take your time.", 79, 83))


        arrayText = ArrayList<String>()
        for (x in arrayOfModel!!.indices) {
            arrayText!!.add(arrayOfModel!![x].text.toString())
        }
    }

    private fun startPlayingTest() {
        if (mediaPlayer != null) {
            if (mediaPlayer!!.isPlaying) {
                mediaPlayer?.pause()
                img_play?.background = resources.getDrawable(R.drawable.ic_play)
                Toast.makeText(this, "pause", Toast.LENGTH_SHORT).show()
                tv_start?.text = "play"

            } else if (mediaPlayer!! != null) {
//                mediaPlayer?.release()
                mediaPlayer?.start()
                relative?.visibility = View.VISIBLE
                img_play?.background = resources.getDrawable(R.drawable.ic_pause)
                Toast.makeText(this, "play", Toast.LENGTH_SHORT).show()
                tv_start?.text = "pause"
//                tv_next?.setBackgroundDrawable(resources.getDrawable(R.drawable.item_back_next_selected))
            }
        }
    }


    private fun setProgress(){
        progress?.max = 12
        progress?.setProgress(3,true)
        progress?.animate()
        tv_progress?.text = "3 / 12"
     }

    override fun onBackPressed() {
        super.onBackPressed()
        mediaPlayer?.pause()
        mediaPlayer?.stop()
    }

    private fun setText() {
        tv_Text?.text =
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

}

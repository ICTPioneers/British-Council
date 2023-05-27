package com.example.british_council.activity

import android.annotation.SuppressLint
import android.content.ClipData
import android.content.ClipDescription
import android.graphics.Color
import android.graphics.PorterDuff
import android.os.Bundle
import android.util.Log
import android.view.DragEvent
import android.view.View
import android.view.View.DragShadowBuilder
import android.view.ViewGroup
import android.widget.LinearLayout
import android.widget.Toast
import androidx.appcompat.app.AppCompatActivity
import com.example.british_council.R
import com.example.british_council.databinding.ActivityTaskBinding

class TaskActivity : AppCompatActivity(), View.OnLongClickListener, View.OnDragListener {

    private var binding: ActivityTaskBinding? = null
    private var arrayTagColor = arrayOf("DRAGGABLE BUTTON 1", "DRAGGABLE BUTTON 2")
    private var arrayTagAnimal = arrayOf("DRAGGABLE BUTTON 3", "DRAGGABLE BUTTON 4")


    @SuppressLint("MissingInflatedId")
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_task)

        initBinding()
        setTag()
        setOnLongClickListener()
        setDragListener()
        clicked()
    }


    private fun initBinding() {
        binding = ActivityTaskBinding.inflate(layoutInflater)
        val view = binding!!.root
        setContentView(view)
    }

    private fun setTag(){
        binding!!.red.tag = "DRAGGABLE BUTTON 1"
        binding!!.blue.tag = "DRAGGABLE BUTTON 2"
        binding!!.cat.tag = "DRAGGABLE BUTTON 3"
        binding!!.dog.tag = "DRAGGABLE BUTTON 4"
    }

    private fun setOnLongClickListener(){
        binding!!.red.setOnLongClickListener(this)
        binding!!.blue.setOnLongClickListener(this)
        binding!!.cat.setOnLongClickListener(this)
        binding!!.dog.setOnLongClickListener(this)
    }


    private fun setDragListener(){
        binding!!.layoutMain.setOnDragListener(this)
        binding!!.colorLayout.setOnDragListener(this)
        binding!!.animalLayout.setOnDragListener(this)
    }

    override fun onDrag(v: View?, event: DragEvent?): Boolean {
        when (event!!.action) {
            DragEvent.ACTION_DRAG_STARTED -> {
                return event.clipDescription
                    .hasMimeType(ClipDescription.MIMETYPE_TEXT_PLAIN)
            }
            DragEvent.ACTION_DRAG_ENTERED -> {
                v?.background!!.setColorFilter(Color.GRAY, PorterDuff.Mode.SRC_IN)
                v.invalidate()
                return true
            }
            DragEvent.ACTION_DRAG_LOCATION -> return true
            DragEvent.ACTION_DRAG_EXITED -> {
                v!!.background.clearColorFilter()
                v.invalidate()
                return true
            }
            DragEvent.ACTION_DROP -> {
                val item: ClipData.Item = event.clipData.getItemAt(0)
                val dragData = item.text.toString()
//                  Toast.makeText(this, "Dragged data is $dragData", Toast.LENGTH_SHORT).show()

                v!!.background.clearColorFilter()
                v.invalidate()
                val vw = event.localState as View
                val owner = vw.parent as ViewGroup
                owner.removeView(vw)
                val container = v as LinearLayout
                container.addView(vw)
                vw.visibility = View.VISIBLE
                return true
            }

            DragEvent.ACTION_DRAG_ENDED -> {
                v!!.background.clearColorFilter()
                v.invalidate()
                if (event.result) Toast.makeText(
                    this,
                    "The drop was handled.",
                    Toast.LENGTH_SHORT
                ).show() else Toast.makeText(this, "The drop didn't work.", Toast.LENGTH_SHORT)
                    .show()
                return true
            }
            else -> Log.e("DragDrop Example", "Unknown action type received by OnDragListener.")
        }
        return false
    }

    override fun onLongClick(v: View?): Boolean {
        val item = ClipData.Item(v?.tag as CharSequence)
        val mimeTypes = arrayOf(ClipDescription.MIMETYPE_TEXT_PLAIN)
        val data = ClipData(v.tag.toString(), mimeTypes, item)
        val dragshadow = DragShadowBuilder(v)
        v.startDrag(data, dragshadow, v, 0)
        return true
    }

    private fun clicked() {
        binding!!.colorGroup?.setOnClickListener { selectFromLayout(binding!!.colorLayout!! , arrayTagColor) }
        binding!!.animallTitle?.setOnClickListener { selectFromLayout(binding!!.animalLayout!!, arrayTagAnimal) }
    }

    private fun selectFromLayout(ll: LinearLayout, array: Array<String>) {
        val childCount = ll.childCount
        for (i in 0 until childCount) {
            val view = ll.getChildAt(i)
            if (view.tag in array) {
                view.setBackgroundColor(resources.getColor(R.color.green_lite))
            } else view.setBackgroundColor(resources.getColor(R.color.red))
        }
    }



}


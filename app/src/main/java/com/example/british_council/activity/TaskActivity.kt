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
import android.widget.Button
import android.widget.LinearLayout
import android.widget.Toast
import androidx.appcompat.app.AppCompatActivity
import com.cleveroad.audiovisualization.*
import com.example.british_council.R

class TaskActivity : AppCompatActivity() , View.OnLongClickListener , View.OnDragListener{

    @SuppressLint("MissingInflatedId")
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_task)
        val btn1 = findViewById<View>(R.id.red) as Button
        btn1.tag = "DRAGGABLE BUTTON 1"
        btn1.setOnLongClickListener(this)
        val btn2 = findViewById<View>(R.id.blue) as Button
        btn2.tag = "DRAGGABLE BUTTON 2"
        val btn3 = findViewById<View>(R.id.cat) as Button
        btn3.tag = "DRAGGABLE BUTTON 3"

        val btn4 = findViewById<View>(R.id.dog) as Button
        btn4.tag = "DRAGGABLE BUTTON 4"

        btn2.setOnLongClickListener(this)
        btn3.setOnLongClickListener(this)
        btn4.setOnLongClickListener(this)

        findViewById<View>(R.id.layout1).setOnDragListener(this)
        findViewById<View>(R.id.color).setOnDragListener(this)
        findViewById<View>(R.id.animal).setOnDragListener(this)

    }

      override fun onDrag(v: View?, event: DragEvent?): Boolean {
          val action: Int = event!!.action
          when (action) {
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
                  Toast.makeText(this, "Dragged data is $dragData", Toast.LENGTH_SHORT).show()
                  v!!.background.clearColorFilter()
                  v.invalidate()
                  val vw = event.localState as View
                  val owner = vw.parent as ViewGroup
                  owner.removeView(vw)
                  val container = v as LinearLayout
  //                if (container.childCount == 0){
                  container.addView(vw)
  //                    vw.visibility = View.VISIBLE
  //                }
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
}
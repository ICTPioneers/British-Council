package ir.ictpioneers.british_council.activity

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
import android.widget.TextView
import android.widget.Toast
import androidx.appcompat.app.AppCompatActivity
import ir.ictpioneers.british_council.R
import ir.ictpioneers.british_council.databinding.ActivityTaskBinding

class TaskActivity : AppCompatActivity(), View.OnLongClickListener, View.OnDragListener {

    private var binding: ActivityTaskBinding? = null
    private var arrayTagUrgent = arrayOf("Do this first","This is a priority", "It’s important",)
    private var arrayTagNotUrgent = arrayOf("Do it when you have time",   "There’s no hurry", "Take your time")

    private var number = 0


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

    private fun setTag() {
        binding!!.nz1.tag = "Do it when you have time"
        binding!!.z1.tag = "Do this first"
        binding!!.z2.tag = "This is a priority"
        binding!!.nz2.tag = "There’s no hurry"
        binding!!.z3.tag = "It’s important"
        binding!!.nz3.tag = "Take your time"
    }

    private fun setOnLongClickListener() {
        binding!!.nz1.setOnLongClickListener(this)
        binding!!.z1.setOnLongClickListener(this)
        binding!!.z2.setOnLongClickListener(this)
        binding!!.nz2.setOnLongClickListener(this)
        binding!!.z3.setOnLongClickListener(this)
        binding!!.nz3.setOnLongClickListener(this)
    }


    private fun setDragListener() {
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
        binding!!.colorGroup?.setOnClickListener {
            selectFromLayout(
                binding!!.colorLayout!!,
                arrayTagUrgent
            )
        }
        binding!!.animallTitle?.setOnClickListener {
            selectFromLayout(
                binding!!.animalLayout!!,
                arrayTagNotUrgent
            )
        }

        binding!!.txtFirst.setOnClickListener {
            setNumber(binding!!.txtFirst)
        }

          binding!!.txtSecond.setOnClickListener {
              setNumber(binding!!.txtSecond)
          }

          binding!!.txtThird.setOnClickListener {
              setNumber(binding!!.txtThird)
        }


        binding!!.txtClear.setOnClickListener {
            arrayOf( binding!!.txtThird ,  binding!!.txtSecond ,binding!!.txtFirst).forEach { it.background = null }
            arrayOf( binding!!.txtThird ,  binding!!.txtSecond ,binding!!.txtFirst).forEach { it.text = "_____" }
            number = 0
        }
    }

    private fun setNumber(v :TextView){
        if (v.text == "_____"){
            number++
            v.background = resources.getDrawable(R.drawable.item_border_number)
            v.text = "" + number
        }
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


package com.example.british_council.adapter

import android.content.Context
import android.util.Log
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.ArrayAdapter
import android.widget.TextView
import androidx.room.Ignore
import com.example.british_council.R
import com.example.british_council.helper.App
import com.example.british_council.model.Text

class CustomArrayAdapter(
    context: Context,
    private val list: List<Text>,
) :
    ArrayAdapter<Text>(context, R.layout.custom_list_view, list) {

    private var currentPos: Int? = null

    override fun getView(position: Int, view: View?, parent: ViewGroup): View {
        val inflater = LayoutInflater.from(parent.context)
        val rowView = inflater.inflate(R.layout.custom_list_view, null, true)

        val titleText = rowView.findViewById(R.id.textView_in_listview) as TextView
        titleText.text = list!![position].text
        titleText.setTextColor(context.resources.getColor(R.color.black))


        Log.e("getView  ", "run: $currentPos" )


        for (x in list!!.indices) {
//            App.toast("index $x")
            if (currentPos in list!![x].start_time!!..list!![x].end_time!!) {
                if (x == position) {
                    App.toast("pos $position")
                    titleText.setTextColor(context.resources.getColor(R.color.green_main))
                    break
                }
            }
        }

//        if (pos == position) {
//            titleText.setTextColor(context.resources.getColor(R.color.green_main))
//        }

        return rowView
    }



    fun getCurrentPos(cPos: Int){
        Log.e("qqqq", "getCurrentPos: $cPos - ${this.currentPos}" )
        this.currentPos = cPos
        Log.e("qqqq", "getCurrentPos after set: $cPos - ${this.currentPos}" )
    }
}
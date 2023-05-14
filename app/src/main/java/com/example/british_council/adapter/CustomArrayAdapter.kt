package com.example.british_council.adapter

import android.content.Context
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.ArrayAdapter
import android.widget.TextView
import com.example.british_council.R

class CustomArrayAdapter(context: Context, private val list: ArrayList<String>, val pos: Int) :
    ArrayAdapter<String>(context, R.layout.custom_list_view, list) {


    override fun getView(position: Int, view: View?, parent: ViewGroup): View {
        val inflater = LayoutInflater.from(parent.context)
        val rowView = inflater.inflate(R.layout.custom_list_view, null, true)

        val titleText = rowView.findViewById(R.id.textView_in_listview) as TextView
        titleText.text = list[position]
        titleText.setTextColor(context.resources.getColor(R.color.black))

        if (pos == position) {
            titleText.setTextColor(context.resources.getColor(R.color.green_main))
        }

        return rowView
    }

    fun addList( listt: ArrayList<String>){
        list.addAll(listt)
    }
}
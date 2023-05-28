package com.example.british_council.adapter

import android.content.res.ColorStateList
import android.location.GnssAntennaInfo.Listener
import android.view.LayoutInflater
import android.view.ViewGroup
import android.widget.LinearLayout
import android.widget.TextView
import androidx.recyclerview.widget.RecyclerView
import com.example.british_council.R
import com.example.british_council.databinding.ListItemSectionBinding
import com.example.british_council.helper.App
import com.example.british_council.model.Level
import com.google.android.material.floatingactionbutton.FloatingActionButton

class SectionAdapter(private var list: List<Level>, listener: Listener) :
    RecyclerView.Adapter<SectionAdapter.MyViewHolder>() {
    var listener = listener


    interface Listener {
        fun onClickListener(model: Level)
    }

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): MyViewHolder {
        return MyViewHolder(
            ListItemSectionBinding.inflate(
                LayoutInflater.from(parent.context),
                parent,
                false
            )
        )
    }


    override fun onBindViewHolder(holder: MyViewHolder, position: Int) {

        var item = holder.itemView
        var model = list[position]

        item.findViewById<TextView>(R.id.level_name).text = list[position].name
        item.findViewById<TextView>(R.id.part_name).text = list[position].desc


        when (model.states) {
            1 -> {
                item.findViewById<FloatingActionButton>(R.id.fab).backgroundTintList =
                    ColorStateList.valueOf(App.context.resources.getColor(R.color.green_2))
                item.findViewById<LinearLayout>(R.id.linear)
                    .setBackgroundDrawable(App.context.getDrawable(R.drawable.item_border_level))
                item.findViewById<FloatingActionButton>(R.id.fab)
                    .setImageDrawable(App.context.resources.getDrawable(R.drawable.ic_check))
            }
            2 -> {
                item.findViewById<FloatingActionButton>(R.id.fab).backgroundTintList =
                    ColorStateList.valueOf(App.context.resources.getColor(R.color.orang))
                item.findViewById<LinearLayout>(R.id.linear)
                    .setBackgroundDrawable(App.context.getDrawable(R.drawable.item_border_level_orang))
                item.findViewById<FloatingActionButton>(R.id.fab)
                    .setImageDrawable(App.context.resources.getDrawable(R.drawable.ic_lock_on))
            }
            else -> {
                item.findViewById<FloatingActionButton>(R.id.fab).backgroundTintList =
                    ColorStateList.valueOf(App.context.resources.getColor(R.color.gray))
                item.findViewById<LinearLayout>(R.id.linear)
                    .setBackgroundDrawable(App.context.getDrawable(R.drawable.item_border_level_red))
                item.findViewById<FloatingActionButton>(R.id.fab)
                    .setImageDrawable(App.context.resources.getDrawable(R.drawable.ic_lock_off))
            }
        }


        item.setOnClickListener { listener.onClickListener(model) }
    }

    override fun getItemCount(): Int {
        return list.size
    }


    fun updateList(items: ArrayList<Level>) {
        this.list = items
        notifyDataSetChanged()
    }


    class MyViewHolder(itemBinding: ListItemSectionBinding) :
        RecyclerView.ViewHolder(itemBinding.root) {
    }

}
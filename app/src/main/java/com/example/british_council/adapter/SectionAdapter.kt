package com.example.british_council.adapter

import android.content.Context
import android.content.Intent
import android.content.res.ColorStateList
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.LinearLayout
import android.widget.TextView
import androidx.recyclerview.widget.RecyclerView
import com.example.british_council.R
import com.example.british_council.activity.LevelActivity
import com.example.british_council.model.Level
import com.google.android.material.floatingactionbutton.FloatingActionButton

class SectionAdapter(context: Context, val list: List<Level>) :
    RecyclerView.Adapter<SectionAdapter.MyViewHolder>() {
    var arrayColor: ArrayList<Int> = ArrayList()
    var arrayIcon: ArrayList<Int> = ArrayList()
    var arrayBack: ArrayList<Int> = ArrayList()
    public var con: Context = context


    class MyViewHolder(itemView: View) : RecyclerView.ViewHolder(itemView) {
//        init {
//            itemView.setOnClickListener { Toast.makeText(, "", Toast.LENGTH_SHORT).show() }
//        }
    }


    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): MyViewHolder {
        return MyViewHolder(
            LayoutInflater.from(parent.context).inflate(R.layout.list_item_section, parent, false)
        )
    }



    override fun onBindViewHolder(holder: SectionAdapter.MyViewHolder, position: Int) {
        arrayColor.add(R.color.green_2)
        arrayColor.add(R.color.orang)
        arrayColor.add(R.color.gray)
        arrayColor.add(R.color.gray)

        arrayIcon.add(R.drawable.ic_check)
        arrayIcon.add(R.drawable.ic_lock_on)
        arrayIcon.add(R.drawable.ic_lock_off)
        arrayIcon.add(R.drawable.ic_lock_off)

        arrayBack.add(R.drawable.item_border_level)
        arrayBack.add(R.drawable.item_border_level_orang)
        arrayBack.add(R.drawable.item_border_level_red)
        arrayBack.add(R.drawable.item_border_level_red)
//        arrayBack.add(R.drawable.item_border_level)
        var a = holder.itemView
        var post = list[position]

        holder.itemView.findViewById<TextView>(R.id.level_name).text =  list[position].name
        holder.itemView.findViewById<TextView>(R.id.part_name).text =  list[position].desc


//        holder.itemView.findViewById<TextView>(R.id.tv_part).text = post.title
//        holder.itemView.findViewById<FloatingActionButton>(R.id.fab).backgroundTintList = ColorStateList.valueOf(context.resources.getColor(R.color.black))
        holder.itemView.findViewById<FloatingActionButton>(R.id.fab).backgroundTintList =
            ColorStateList.valueOf(con.resources.getColor(arrayColor[position]))
        holder.itemView.findViewById<FloatingActionButton>(R.id.fab)
            .setImageDrawable(con.resources.getDrawable(arrayIcon[position]))

        holder.itemView.findViewById<LinearLayout>(R.id.linear)
            .setBackgroundDrawable(con.getDrawable(arrayBack[position]))



        holder.itemView.setOnClickListener {
            con.startActivity(Intent(con,LevelActivity::class.java) )
        }


    }

    override fun getItemCount(): Int {
        return list.size
    }
}
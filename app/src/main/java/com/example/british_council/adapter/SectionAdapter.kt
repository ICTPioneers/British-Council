package com.example.british_council.adapter

import android.content.Context
import android.content.Intent
import android.content.res.ColorStateList
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.LinearLayout
import android.widget.TextView
import android.widget.Toast
import androidx.recyclerview.widget.RecyclerView
import com.example.british_council.R
import com.example.british_council.activity.LevelActivity
import com.example.british_council.helper.App
import com.example.british_council.helper.Session
import com.example.british_council.model.Level
import com.facebook.shimmer.ShimmerFrameLayout
import com.google.android.material.floatingactionbutton.FloatingActionButton

class SectionAdapter(context: Context, val list: List<Level>) :
    RecyclerView.Adapter<SectionAdapter.MyViewHolder>() {
    var arrayColor: ArrayList<Int> = ArrayList()
    var arrayIcon: ArrayList<Int> = ArrayList()
    var arrayBack: ArrayList<Int> = ArrayList()
    var context: Context = context


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
            ColorStateList.valueOf(context.resources.getColor(arrayColor[position]))
        holder.itemView.findViewById<FloatingActionButton>(R.id.fab)
            .setImageDrawable(context.resources.getDrawable(arrayIcon[position]))

        holder.itemView.findViewById<LinearLayout>(R.id.linear)
            .setBackgroundDrawable(context.getDrawable(arrayBack[position]))



        holder.itemView.setOnClickListener {
            Session.getInstance().putExtra("part",list[position].id)
            App.toast("hello ${list[position].id}")
            context.startActivity(Intent(context,LevelActivity::class.java) )
        }


    }

    override fun getItemCount(): Int {
        return list.size
    }
}
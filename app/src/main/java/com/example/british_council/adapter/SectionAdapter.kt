package com.example.british_council.adapter

import android.content.Intent
import android.content.res.ColorStateList
import android.text.TextUtils
import android.view.LayoutInflater
import android.view.ViewGroup
import android.widget.LinearLayout
import android.widget.TextView
import androidx.recyclerview.widget.RecyclerView
import com.example.british_council.R
import com.example.british_council.activity.LevelActivity
import com.example.british_council.databinding.ListItemSectionBinding
import com.example.british_council.helper.App
import com.example.british_council.model.Level
import com.google.android.material.floatingactionbutton.FloatingActionButton
import com.google.gson.Gson

class SectionAdapter( private val list: List<Level>) :
    RecyclerView.Adapter<SectionAdapter.MyViewHolder>() {
    var arrayColor: ArrayList<Int> = ArrayList()
    var arrayIcon: ArrayList<Int> = ArrayList()
    private var arrayBack: ArrayList<Int> = ArrayList()
    var ll : Level? = null


    init {
        App.toast(App.database.dao.getIdLevel().toString())
//        if(TextUtils.isEmpty(App.database.dao.getIdLevel(0).toString())) App.toast("level is active null")
//        else App.toast("level is active " + App.database.dao.getIdLevel(0))
    }
    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): MyViewHolder {
        return MyViewHolder(ListItemSectionBinding.inflate(LayoutInflater.from(parent.context), parent, false))
    }



    override fun onBindViewHolder(holder: MyViewHolder, position: Int) {
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
            ColorStateList.valueOf(App.context.resources.getColor(arrayColor[position]))
        holder.itemView.findViewById<FloatingActionButton>(R.id.fab)
            .setImageDrawable(App.context.resources.getDrawable(arrayIcon[position]))

        holder.itemView.findViewById<LinearLayout>(R.id.linear)
            .setBackgroundDrawable(App.context.getDrawable(arrayBack[position]))



        holder.itemView.setOnClickListener {
//            App.toast("hello ${list[position].id}")
            var i = Intent(App.context,LevelActivity::class.java)
//            i.addFlags(Intent.FLAG_ACTIVITY_NEW_TASK)
            i.flags = Intent.FLAG_ACTIVITY_NEW_TASK
            i.putExtra("level",Gson().toJson(list[position]))
            App.context.startActivity(i)
        }
    }

    override fun getItemCount(): Int {
        return list.size
    }





    class MyViewHolder(private val itemBinding: ListItemSectionBinding) : RecyclerView.ViewHolder(itemBinding.root) {
//        var title = view.title
        init {

            }
    }

}
package com.example.british_council.activity

import android.annotation.SuppressLint
import android.os.Build
import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import android.widget.TextView
import androidx.annotation.RequiresApi
import androidx.recyclerview.widget.RecyclerView
import com.example.british_council.R
import com.example.british_council.adapter.SectionAdapter

class MainActivity : AppCompatActivity() {
    private var txt_hello: TextView? = null
    private var adapter: SectionAdapter? = null
    private var recyclerView: RecyclerView? = null


    @RequiresApi(Build.VERSION_CODES.O)
    @SuppressLint("MissingInflatedId")
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_main)

        initID()
        initRecyclerview()
    }

    private fun initID() {
        txt_hello = findViewById(R.id.txt_hello)
        recyclerView = findViewById(R.id.rv_main)
    }


    private fun initRecyclerview() {
        var arrayList = ArrayList<String>()
        arrayList.add("part 1")
        arrayList.add("part 2")
        arrayList.add("part 3")
        arrayList.add("part 4")

        adapter = SectionAdapter(this, arrayList)
        recyclerView?.adapter = adapter
    }

}

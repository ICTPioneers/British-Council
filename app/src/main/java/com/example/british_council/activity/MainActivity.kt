package com.example.british_council.activity

import android.R
import android.content.Intent
import android.net.ConnectivityManager
import android.os.Build
import android.os.Bundle
import android.os.Handler
import android.os.Looper
import android.util.Log
import android.view.Gravity
import android.view.View
import androidx.annotation.RequiresApi
import androidx.appcompat.app.AppCompatActivity
import com.example.british_council.adapter.SectionAdapter
import com.example.british_council.database.dao.api.ApiClient
import com.example.british_council.databinding.ActivityMainBinding
import com.example.british_council.helper.App
import com.example.british_council.helper.Session
import com.example.british_council.model.Data
import com.example.british_council.model.Level
import com.google.android.material.snackbar.Snackbar
import com.google.gson.Gson
import retrofit2.Call
import retrofit2.Callback
import retrofit2.Response


class MainActivity : AppCompatActivity(), SectionAdapter.Listener {
    private var adapter: SectionAdapter? = null
    private var binding: ActivityMainBinding? = null
    private var listLevel: ArrayList<Level> = ArrayList()

    @RequiresApi(Build.VERSION_CODES.O)
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)

        initBinding()
        getDataFromSever()
        setSwipeRefreshLayout()
        initDrawer()
    }

    override fun onResume() {
        super.onResume()
        adapter?.updateList(initLevelByDatabase())
    }


    private fun initBinding() {
        binding = ActivityMainBinding.inflate(layoutInflater)
        val view = binding!!.root
        setContentView(view)
    }

    private fun initDrawer() {
        binding!!.menu.setOnClickListener { binding?.drawer?.openDrawer(Gravity.LEFT) }
        binding!!.drawerMenu.txtAboutUs.setOnClickListener {
            App.showDialog(this)
            binding!!.drawer.close()
        }
    }


//    private fun showDialog() {
//        val dialog = Dialog(this)
//        dialog.requestWindowFeature(Window.FEATURE_NO_TITLE)
//        dialog.setCancelable(false)
//        dialog.setContentView(R.layout.dialog_about_us)
//        dialog.show()
//    }

    private fun setSwipeRefreshLayout() {
        binding?.swipe?.setOnRefreshListener {
            checkConnection()
            Handler().postDelayed(Runnable {
                binding?.swipe?.isRefreshing = false
            }, 4000)
        }
    }

    private fun getDataFromSever() {
        ApiClient.getClient()?.getPost()?.enqueue(object : Callback<Data> {
            override fun onResponse(call: Call<Data>, response: Response<Data>) {
                Session.getInstance().putExtra("length", response.body()!!.level?.size)
                listLevel = response.body()?.level!!
                adapter = SectionAdapter(initLevelByDatabase(), this@MainActivity)

                Handler(Looper.getMainLooper()).postDelayed({
                    binding?.shimmer?.stopShimmer()
                    binding?.shimmer?.visibility = View.GONE
                    binding?.recycler?.adapter = adapter
                }, 3000)
            }

            override fun onFailure(call: Call<Data>, t: Throwable) {
                snack()
                Log.e("qqqq", "onFailure: ", t)
            }
        })
    }


    private fun initLevelByDatabase(): ArrayList<Level> {
        for (i in 0 until listLevel.size) {
            val levelDatabase = App.database.levelDao.getLeve(listLevel[i].id ?: 0)
            if (levelDatabase != null) {
                Log.e("qqq", "levelDatabase: ${levelDatabase.id}")
            } else Log.e("qqq", "listLevel: ${listLevel[i].id}")
            if (i == 0 && levelDatabase == null) {
                listLevel[i].states = 2
            } else if (levelDatabase != null) {
                listLevel[i].states = 1
                Log.e("qqq", "initLevelByDatabase: $i -${listLevel.size}")
                if (i + 1 <= listLevel.size - 1) {
                    listLevel[i + 1].states = 2
                }
            }
        }
        return listLevel
    }

    private fun checkConnection() {
        val connectivityManager = this.getSystemService(CONNECTIVITY_SERVICE) as ConnectivityManager
        val wifi = connectivityManager.getNetworkInfo(ConnectivityManager.TYPE_WIFI)
        val mobile = connectivityManager.getNetworkInfo(ConnectivityManager.TYPE_MOBILE)
        if (wifi!!.isConnected) getDataFromSever()
        else if (mobile!!.isConnected) getDataFromSever()
        else snack()
    }


    private fun snack() {
        val parentLayout = findViewById<View>(R.id.content)
        Snackbar.make(parentLayout, "your connection is failed", Snackbar.LENGTH_LONG)
            .setBackgroundTint(resources.getColor(R.color.darker_gray))
            .setTextColor(resources.getColor(R.color.black))
            .show()
    }

    override fun onClickListener(model: Level) {
        if (model.states != 0) {
            var i = Intent(App.context, LevelActivity::class.java)
            i.flags = Intent.FLAG_ACTIVITY_NEW_TASK
            i.putExtra("level", Gson().toJson(model))
            startActivity(i)
        }
    }


}

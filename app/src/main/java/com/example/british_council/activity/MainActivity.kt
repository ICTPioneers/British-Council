package com.example.british_council.activity

import android.R
import android.annotation.SuppressLint
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
import com.example.british_council.api.ApiClient
import com.example.british_council.databinding.ActivityMainBinding
import com.example.british_council.helper.App
import com.example.british_council.helper.Session
import com.example.british_council.model.Data
import com.google.android.material.snackbar.Snackbar
import retrofit2.Call
import retrofit2.Callback
import retrofit2.Response


class MainActivity : AppCompatActivity() {
    private var adapter: SectionAdapter? = null
    private var binding: ActivityMainBinding? = null

    @RequiresApi(Build.VERSION_CODES.O)
    @SuppressLint("MissingInflatedId")
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)

        initBinding()
        getDataFromSever()
        setSwipeRefreshLayout()
        initMenu()
    }

    private fun initBinding() {
        binding = ActivityMainBinding.inflate(layoutInflater)
        val view = binding!!.root
        setContentView(view)
    }

    private fun initMenu() {
        binding!!.menu.setOnClickListener { binding?.drawer?.openDrawer(Gravity.LEFT) }
    }

    private fun setSwipeRefreshLayout() {
        binding?.swipe?.setOnRefreshListener {
            checkConnection()
            Handler().postDelayed(Runnable {
                binding?.swipe?.isRefreshing = false
//                snack?.dismiss()
            }, 4000)
        }
    }

    private fun getDataFromSever() {
        ApiClient.getClient()?.getPost()?.enqueue(object : Callback<Data> {
            override fun onResponse(call: Call<Data>, response: Response<Data>) {
                Session.getInstance().putExtra("length", response.body()!!.level?.size)
                var lv = response.body()?.level!!
                adapter = SectionAdapter(lv)
                App.database.levelDao.insert(lv)

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


}

package com.example.british_council.activity

import android.annotation.SuppressLint
import android.content.DialogInterface
import android.net.ConnectivityManager
import android.os.Build
import android.os.Bundle
import android.os.Handler
import android.os.Looper
import android.util.Log
import android.view.View
import android.widget.TextView
import androidx.annotation.RequiresApi
import androidx.appcompat.app.AlertDialog
import androidx.appcompat.app.AppCompatActivity
import androidx.recyclerview.widget.RecyclerView
import androidx.swiperefreshlayout.widget.SwipeRefreshLayout
import com.example.british_council.R
import com.example.british_council.adapter.SectionAdapter
import com.example.british_council.api.ApiClient
import com.example.british_council.database.DatabaseHelper
import com.example.british_council.databinding.ActivityLevelBinding
import com.example.british_council.databinding.ActivityMainBinding
import com.example.british_council.helper.App
import com.example.british_council.helper.Session
import com.example.british_council.model.Data
import com.example.british_council.model.LevelModel
import com.facebook.shimmer.ShimmerFrameLayout
import retrofit2.Call
import retrofit2.Callback
import retrofit2.Response


class MainActivity : AppCompatActivity() {
    private var adapter: SectionAdapter? = null
    private var level: List<LevelModel>? = null
    private var db: DatabaseHelper? = null
    private var binding: ActivityMainBinding? = null
    var dialog :AlertDialog? = null


    @RequiresApi(Build.VERSION_CODES.O)
    @SuppressLint("MissingInflatedId")
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)

        initBinding()
        getDataFromSever()
        setSwipeRefreshLayout()
    }

    private fun initBinding() {
        binding = ActivityMainBinding.inflate(layoutInflater)
        val view = binding!!.root
        setContentView(view)
    }

//    private fun initID() {
//        txt_hello = findViewById(R.id.txt_hello)
//        recyclerView = findViewById(R.id.rv_main)
//        swipeRefresh = findViewById(R.id.swipe)
//        shimmer = findViewById(R.id.shimmer)
//    }


    private fun setSwipeRefreshLayout() {
        binding?.swipe?.setOnRefreshListener {
            checkConnection()
            Handler().postDelayed(Runnable {
                binding?.swipe?.isRefreshing = false
                dialog?.dismiss()
            }, 4000)
        }
    }

    private fun getDataFromSever() {
        ApiClient.getClient()?.getPost()?.enqueue(object : Callback<Data> {
            override fun onResponse(call: Call<Data>, response: Response<Data>) {
                Session.getInstance().putExtra("length", response.body()!!.level?.size)
                var lv = response.body()?.level!!
                adapter = SectionAdapter(applicationContext, lv)
                App.database.dao.insert(lv)

                Handler(Looper.getMainLooper()).postDelayed({
                    binding?.shimmer?.stopShimmer()
                    binding?.shimmer?.visibility = View.GONE
                    binding?.recycler?.adapter = adapter
                }, 3000)
            }

            override fun onFailure(call: Call<Data>, t: Throwable) {
                dialog()
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
        else dialog()
    }

    private fun dialog() {
        dialog = AlertDialog.Builder(this)
            .setMessage("your connection is failed")
            .setCancelable(true)
//            .setPositiveButton("yes", DialogInterface.OnClickListener { dialog, _ ->  dialog.dismiss() })
//            .setNegativeButton("no", DialogInterface.OnClickListener { dialog , _ ->  dialog.dismiss() })
            .show()
    }

}

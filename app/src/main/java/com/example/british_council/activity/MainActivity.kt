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
import com.example.british_council.helper.App
import com.example.british_council.model.Data
import com.example.british_council.model.LevelModel
import com.facebook.shimmer.ShimmerFrameLayout
import retrofit2.Call
import retrofit2.Callback
import retrofit2.Response


class MainActivity : AppCompatActivity() {
    private var txt_hello: TextView? = null
    private var adapter: SectionAdapter? = null
    private var swipeRefresh: SwipeRefreshLayout? = null
    private var recyclerView: RecyclerView? = null
    private var shimmer: ShimmerFrameLayout? = null
    private var level: List<LevelModel>? = null
    private var db: DatabaseHelper? = null



    @RequiresApi(Build.VERSION_CODES.O)
    @SuppressLint("MissingInflatedId")
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_main)

        initID()
//        shimmer?.startShimmer()
//        shimmer?.setBackgroundColor(resources.getColor(R.color.black))
        getDataFromSever()
        setSwipeRefreshLayout()
    }

    private fun initID() {
        txt_hello = findViewById(R.id.txt_hello)
        recyclerView = findViewById(R.id.rv_main)
        swipeRefresh = findViewById(R.id.swipe)
        shimmer = findViewById(R.id.shimmer)
    }


    private fun setSwipeRefreshLayout(){
        swipeRefresh?.setOnRefreshListener {
            checkConnection()
            Handler().postDelayed(Runnable {
                swipeRefresh?.isRefreshing = false
            }, 4000)
        }
    }

     private fun getDataFromSever(){
         ApiClient.getClient()?.getPost()?.enqueue(object : Callback<Data> {
             override fun onResponse(call: Call<Data>, response: Response<Data>) {
                 var lv =  response.body()?.level!!
                 adapter = SectionAdapter(applicationContext, response.body()?.level!!)
                 App.database.dao.insert(lv)

                 Handler(Looper.getMainLooper()).postDelayed({
                     shimmer?.stopShimmer()
                     shimmer?.visibility = View.GONE
                     recyclerView?.adapter = adapter
                 }, 4000)
             }

             override fun onFailure(call: Call<Data>, t: Throwable) {
                 App.toast("your connection is faild")
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
       else {
           AlertDialog.Builder(this)
               .setMessage("connection is canceled")
               .setCancelable(true)
               .setPositiveButton("yes", DialogInterface.OnClickListener { dialog, _ ->  dialog.dismiss() })
               .setNegativeButton("no", DialogInterface.OnClickListener { dialog , _ ->  dialog.dismiss() })
               .show()
//            progressBar.setVisibility(View.INVISIBLE)
        }
    }



//    Log.e("qqq", "onResponse: "+ lv[0].name)
//    Log.e("qqq", "onResponse: "+ lv[0].text!![0].text)
//    Log.e("qqq", "onResponse: "+ App.database.dao.getLeve(2))

}

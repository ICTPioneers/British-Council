package com.example.british_council.activity

import android.annotation.SuppressLint
import android.content.Context
import android.os.Build
import android.os.Bundle
import android.util.Log
import android.widget.TextView
import androidx.annotation.RequiresApi
import androidx.appcompat.app.AppCompatActivity
import androidx.recyclerview.widget.RecyclerView
import com.example.british_council.R
import com.example.british_council.adapter.SectionAdapter
import com.example.british_council.api.ApiClient
import com.example.british_council.api.ApiService
import com.example.british_council.model.Data
import com.example.british_council.model.LevelModel
import com.example.british_council.model.NullHostNameVerifier
import com.google.gson.Gson
import com.google.gson.reflect.TypeToken
import retrofit2.Call
import retrofit2.Callback
import retrofit2.Response
import retrofit2.create
import java.io.IOException
import java.security.SecureRandom
import javax.net.ssl.HttpsURLConnection
import javax.net.ssl.SSLContext
import javax.net.ssl.X509TrustManager

class MainActivity : AppCompatActivity() {
    private var txt_hello: TextView? = null
    private var adapter: SectionAdapter? = null
    private var recyclerView: RecyclerView? = null

    private var level: List<LevelModel>? = null


//    private var db: DatabaseHelper? = null



    @RequiresApi(Build.VERSION_CODES.O)
    @SuppressLint("MissingInflatedId")
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_main)

        initID()
        initRecyclerview()
//        testSever()

        val jsonFileString = getJsonDataFromAsset(applicationContext, "example_leaen_en.json")
////        Log.i("data", jsonFileString)
//
        val gson = Gson()
        val listPersonType = object : TypeToken<Data>() {}.type

        var persons: Data = gson.fromJson(jsonFileString, listPersonType)
//        persons.forEachIndexed { idx, person -> Log.i("data", "> Item $idx:\n$person") }



    }




    private fun initID() {
        txt_hello = findViewById(R.id.txt_hello)
        recyclerView = findViewById(R.id.rv_main)
    }


    fun initDatabase(){
//        val db = DatabaseHelper.getInstance(this)
//        level =  db!!.dao.getAllNotes()

    }

    private fun initRecyclerview() {
        var arrayList = ArrayList<String>()
        arrayList.add("part 1")
        arrayList.add("part 2")
        arrayList.add("part 3")
        arrayList.add("part 4")

        adapter = SectionAdapter(this, ArrayList())
        recyclerView?.adapter = adapter
        testSever()
    }


     private fun testSever(){
         var apiService = ApiClient.getClient()?.create(ApiService::class.java)
         val getApiPost = apiService?.getPost()
//       var apiService = ApiClient.getClient()?.getPost()

       getApiPost?.enqueue(object : Callback<Data> {
           override fun onResponse(call: Call<Data>, response: Response<Data>) {
                Log.e("qqq", "onResponse: "+ response.body());
               adapter = SectionAdapter(applicationContext, response.body()?.level!!)
               recyclerView?.adapter = adapter
           }

           override fun onFailure(call: Call<Data>, t: Throwable) {
               Log.e("qqqq", "onFailure: ", t)
           }
       })
   }





    private fun getJsonDataFromAsset(context: Context, fileName: String): String? {
        val jsonString: String
        try {
            jsonString = context.assets.open(fileName).bufferedReader().use { it.readText() }
        } catch (ioException: IOException) {
            ioException.printStackTrace()
            return null
        }
        return jsonString
    }


}

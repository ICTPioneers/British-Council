package com.example.british_council.api

import com.example.british_council.helper.Config
import okhttp3.OkHttpClient
import okhttp3.logging.HttpLoggingInterceptor
import retrofit2.Retrofit
import retrofit2.converter.gson.GsonConverterFactory
import javax.net.ssl.HostnameVerifier
import javax.net.ssl.SSLSession


object ApiClient {
    private var retrofit: Retrofit? = null

    fun getClient(): Retrofit? {
        val interceptor = HttpLoggingInterceptor()
        interceptor.level = HttpLoggingInterceptor.Level.BODY
        val client = OkHttpClient.Builder().addInterceptor(interceptor).build()
        val httpLoggingInterceptor = HttpLoggingInterceptor()
        httpLoggingInterceptor.level = HttpLoggingInterceptor.Level.BODY
        // Add the interceptor to OkHttpClient
        OkHttpClient.Builder().addInterceptor(httpLoggingInterceptor).build()
        OkHttpClient.Builder().hostnameVerifier(object : HostnameVerifier {
            override fun verify(hostname: String?, session: SSLSession?): Boolean {
                return true
            }
        }).build()



//        OkHttpClient.Builder().hostnameVerifier(object : HostnameVerifier {
//            override fun verify(p0: String?, p1: SSLSession?): Boolean {
//                TODO("Not yet implemented")
//            }
//
//        }).build()
//        val builder = OkHttpClient.Builder().addInterceptor(httpLoggingInterceptor).build()


//        val okHttpClient: OkHttpClient = Builder().hostnameVerifier(object : HostnameVerifier() {
//            fun verify(hostname: String?, session: SSLSession?): Boolean {
//                return true
//            }
//        }).build()
//

        retrofit = Retrofit.Builder()
            .baseUrl(Config.BASE_URL)
            .addConverterFactory(GsonConverterFactory.create())
            .client(client)
            .build()
        return retrofit
    }




//
//    fun getClient(): ApiService {
//        val client = OkHttpClient.Builder()
//        client.readTimeout(20, TimeUnit.SECONDS)
//        client.addInterceptor { chain ->
//            val original = chain.request()
//            val builder = original.newBuilder()
////            builder.addHeader("Authorization", "Bearer "+ Session.getInstance().sessionKey)
////            builder.addHeader("Accept", "application/json")
//            builder.method(original.method, original.body)
//            val request = builder.build()
//            chain.proceed(request)
//        }
//        val retrofit = Retrofit.Builder()
//            .baseUrl(Config.BASE_URL)
//            .addConverterFactory(ScalarsConverterFactory.create())
//            .addConverterFactory(GsonConverterFactory.create(GsonBuilder().setLenient().create()))
//            .client(client.build())
//            .build()
//        return retrofit.create(ApiService::class.java)
//    }

}
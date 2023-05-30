package ir.ictpioneers.british_council.api

import ir.ictpioneers.british_council.helper.Config.BASE_URL
import com.google.gson.GsonBuilder
import okhttp3.OkHttpClient
import okhttp3.logging.HttpLoggingInterceptor
import retrofit2.Retrofit
import retrofit2.converter.gson.GsonConverterFactory
import retrofit2.converter.scalars.ScalarsConverterFactory
import java.util.concurrent.TimeUnit

class ApiClient {
    companion object {
        fun getClient(): ApiService {
            val logging = HttpLoggingInterceptor()
            logging.level = HttpLoggingInterceptor.Level.BODY

            val client = OkHttpClient.Builder()
            client.addInterceptor(logging)
            client.readTimeout(20, TimeUnit.SECONDS)
            client.addInterceptor { chain ->
                val original = chain.request()
                val builder = original.newBuilder()
                builder.addHeader("Accept", "application/json")
                builder.method(original.method, original.body)
                val request = builder.build()
                chain.proceed(request)
            }
            val retrofit = Retrofit.Builder()
                .baseUrl("$BASE_URL/")
                .addConverterFactory(ScalarsConverterFactory.create())
                .addConverterFactory(GsonConverterFactory.create(GsonBuilder().setLenient().create()))
                .client(client.build())
                .build()
            return retrofit.create(ApiService::class.java)
        }
    }
}
package ir.ictpioneers.british_council.api

import ir.ictpioneers.british_council.model.Data
import retrofit2.Call
import retrofit2.http.GET

interface ApiService {
    @GET("british-council")
    fun getPost(): Call<Data>
}
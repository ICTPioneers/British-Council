package com.example.british_council.api

import com.example.british_council.model.Data
import retrofit2.Call
import retrofit2.http.GET

interface ApiService {
    @GET("british-council")
    fun getPost(): Call<Data>

}
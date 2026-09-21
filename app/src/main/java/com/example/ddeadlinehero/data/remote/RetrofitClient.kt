package com.example.ddeadlinehero.data.remote

import retrofit2.Retrofit
import retrofit2.converter.gson.GsonConverterFactory

object RetrofitClient {

    val api: AssignmentApi by lazy {
        Retrofit.Builder()
            .baseUrl("https://example.com/") // kjo osht fake backend
            .addConverterFactory(GsonConverterFactory.create())
            .build()
            .create(AssignmentApi::class.java)
    }
}

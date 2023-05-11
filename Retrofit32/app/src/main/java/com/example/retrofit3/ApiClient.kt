package com.example.retrofit3

import com.google.gson.GsonBuilder
import retrofit2.Retrofit
import retrofit2.converter.gson.GsonConverterFactory

object ApiClient {

    private val BASE_URL = "https://www.slmm.com.br"
    private var mRetrofit: Retrofit? = null

    val client: Retrofit
        get() {
            if(mRetrofit == null){


                mRetrofit = Retrofit.Builder()
                    .baseUrl(BASE_URL)
                    .addConverterFactory(GsonConverterFactory.create())
                    .build()
            }
            return this.mRetrofit!!
        }


}
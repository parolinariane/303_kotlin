package com.example.testetelas

import com.google.gson.GsonBuilder
import retrofit2.Retrofit
import retrofit2.converter.gson.GsonConverterFactory

object ApiClient {
    /*"https://www.slmm.com.br"*/
    //"""https://glitch.com/edit/#!/glitch-hello-node" //

    private val BASE_URL = "https://treasure-hunt-cotuca.glitch.me/"
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
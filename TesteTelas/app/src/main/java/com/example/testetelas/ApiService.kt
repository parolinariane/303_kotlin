package com.example.testetelas

import com.example.retrofit3.com.example.testetelas.EstruturaApi
import retrofit2.Call
import retrofit2.http.GET
import retrofit2.http.Query
import retrofit2.http.Body
import retrofit2.http.POST

interface ApiService {
    @GET("/alunos")
    fun fetchDados(): Call<ArrayList<DadosI>>
}
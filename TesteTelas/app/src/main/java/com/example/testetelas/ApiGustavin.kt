package com.example.testetelas;

import retrofit2.Call;
import retrofit2.http.*;

interface ApiGustavin {
    @POST("/alunos")
    fun inclui(@Body dados_i: DadosI): Call<Object>?

    @GET("/alunos/{ra}")
    fun recuperaUm(@Path("RA") RA : Number): Call<DadosI?>?

    @GET("/alunos")
    fun recupereTodos() : Call<ArrayList<DadosI>>
}

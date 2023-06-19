package com.example.testetelas

import android.graphics.Bitmap
import android.graphics.BitmapFactory
import android.os.Build
import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import android.util.Base64
import android.util.Log
import androidx.annotation.RequiresApi
import com.example.retrofit3.com.example.testetelas.EstruturaApi
import com.example.testetelas.databinding.ActivityTelaInfosBinding
import com.google.gson.Gson
import okhttp3.ResponseBody
import retrofit2.Call
import retrofit2.Callback
import retrofit2.Response
import retrofit2.Retrofit
import retrofit2.converter.gson.GsonConverterFactory

class TelaInfos : AppCompatActivity() {

    private lateinit var binding: ActivityTelaInfosBinding

    private var mApiService: ApiService? = null

    lateinit var EstruturaList: ArrayList<DadosI>

    private var mApiServicePost: ApiServicePost? = null

    var dadosI: DadosI? = null


    @RequiresApi(Build.VERSION_CODES.TIRAMISU)
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        binding = ActivityTelaInfosBinding.inflate(layoutInflater)
        setContentView(binding.root)

        binding.button.setOnClickListener {
            mApiService = ApiClient.client.create(ApiService::class.java)

            val call = mApiService!!.fetchDados()
            call!!.enqueue(object : Callback<ArrayList<DadosI>?> {

                override fun onResponse(
                    call: Call<ArrayList<DadosI>?>,
                    response: Response<ArrayList<DadosI>?>
                ) {
                    Log.d("OK", " dados: " + response.body()!!)
                    EstruturaList = response.body()!!
                    var msg: String = ""
                    msg = msg + "RA: "        + EstruturaList[2].ra!!   + "\n"
                    msg = msg + "Data: "      + EstruturaList[2].data_hora!! + "\n"
                    msg = msg + "Latitude: "  + EstruturaList[2].lat!!  + "\n"
                    msg = msg + "Longitude: " + EstruturaList[2].lon!!  + "\n"
                    msg = msg + "Foto: " + EstruturaList[2].foto!! + "\n"
                    binding.resultado.text = msg

                    Log.d("foto", EstruturaList[2].foto!!)
                    val imageBytes = Base64.decode(EstruturaList[2].foto!!, Base64.DEFAULT)
                    var bitmap: Bitmap? = null
                    bitmap = BitmapFactory.decodeByteArray(imageBytes,
                        0,
                        imageBytes.size)
                    binding.imgV.setImageBitmap(bitmap)
                }

                override fun onFailure(call: Call<ArrayList<DadosI>?>, t : Throwable){
                    Log.e("Erro", "Got Error: " + t.localizedMessage)
                }
            })
        }

        binding.btn2.setOnClickListener {
            val src: String = "https://icons8.com.br/icon/18907/usu%C3%A1rio-feminino"

            mApiServicePost = ApiClient.client.create(ApiServicePost::class.java)

            dadosI = intent.getSerializableExtra("Chave", DadosI::class.java)

            var gson = Gson().newBuilder().disableHtmlEscaping().create()
            var str = gson.toJson(dadosI)



            val call = mApiServicePost!!.sendDados(str)

            call!!.enqueue(object : Callback<ResponseBody> {
                override fun onResponse(call: Call<ResponseBody>, response: Response<ResponseBody>) {
                    Log.d("Resposta", "Resp: " + response.body().toString()!!)
                }

                override fun onFailure(call: Call<ResponseBody>, t: Throwable) {
                    Log.e("Erro", "Got Error: " + t.localizedMessage)

                }
            })

        }
    }
}
package com.example.retrofit3

import android.graphics.Bitmap
import android.graphics.BitmapFactory
import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import android.util.Base64
import android.util.Log
import com.example.retrofit3.databinding.ActivityMainBinding
import com.google.gson.Gson
import retrofit2.Call
import retrofit2.Callback
import retrofit2.Response

class MainActivity : AppCompatActivity() {

    private lateinit var binding: ActivityMainBinding

    private var mApiService: ApiService? = null
    lateinit var EstruturaList: ArrayList<EstruturaApi>

    private var mApiServicePost: ApiServicePost? = null

    var dadosI: DadosI? = null


    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        binding = ActivityMainBinding.inflate(layoutInflater)
        setContentView(binding.root)

        binding.button.setOnClickListener {
            mApiService = ApiClient.client.create(ApiService::class.java)
            // agora chamamos o call para enfilerar a chamada e esperar a resposta
            val call = mApiService!!.fetchDados("1234")
            call!!.enqueue(object : Callback<ArrayList<EstruturaApi>?>{


                override fun onResponse(
                    call: Call<ArrayList<EstruturaApi>?>,
                    response: Response<ArrayList<EstruturaApi>?>
                ) {
                    Log.d("OK", " dados: " + response.body()!!)
                    EstruturaList = response.body()!!
                    var msg: String = ""
                    msg = msg + "ID: "        + EstruturaList[2].id!!   + "\n"
                    msg = msg + "RA: "        + EstruturaList[2].ra!!   + "\n"
                    msg = msg + "Data: "      + EstruturaList[2].data!! + "\n"
                    msg = msg + "Latitude: "  + EstruturaList[2].lat!!  + "\n"
                    msg = msg + "Longitude: " + EstruturaList[2].log!!  + "\n"
                    binding.resultado.text = msg

                    Log.d("Img", EstruturaList[2].img!!)
                    val imageBytes = Base64.decode(EstruturaList[2].img!!, Base64.DEFAULT)
                    var bitmap: Bitmap? = null
                    bitmap = BitmapFactory.decodeByteArray(imageBytes,
                        0,
                        imageBytes.size)
                    binding.imgV.setImageBitmap(bitmap)
                }

                override fun onFailure(call: Call<ArrayList<EstruturaApi>?>, t : Throwable){
                    Log.e("Erro", "Got Error: " + t.localizedMessage)
                }
            })
        }

        binding.btn2.setOnClickListener {
            val src: String = "https://icons8.com.br/icon/18907/usu%C3%A1rio-feminino"

            mApiServicePost = ApiClient.client.create(ApiServicePost::class.java)

            dadosI = DadosI(ra = "22531" , lat = "1", lon = "1", img = src)
            var gson = Gson()
            var str = gson.toJson(dadosI)



            val call = mApiServicePost!!.sendDados(str)
            call!!.enqueue(object : Callback<Resposta>{
                override fun onResponse(call: Call<Resposta>, response: Response<Resposta>) {
                    Log.d("Resposta", "Resp: " + response.body()!!)
                }

                override fun onFailure(call: Call<Resposta>, t: Throwable) {
                    Log.e("Erro", "Got Error: " + t.localizedMessage)

                }
            })

        }
    }
}
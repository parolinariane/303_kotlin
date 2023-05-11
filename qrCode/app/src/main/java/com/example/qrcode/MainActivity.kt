package com.example.qrcode

import android.content.Intent
import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import android.widget.Toast
import com.example.qrcode.databinding.ActivityMainBinding
import com.google.zxing.integration.android.IntentIntegrator
import org.json.JSONException
import org.json.JSONObject

class MainActivity : AppCompatActivity() {

    private lateinit var binding: ActivityMainBinding

    private var qrScanIntegrator: IntentIntegrator? = null


    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        binding = ActivityMainBinding.inflate(layoutInflater)
        val view = binding.root
        setContentView(view)

        setOnClickListener()
        setupScanner()
    }

    private fun setupScanner(){
        qrScanIntegrator = IntentIntegrator(this)
        qrScanIntegrator?.setOrientationLocked(false)
    }

    private fun setOnClickListener(){

        binding.btnScan.setOnClickListener{ performAction() }
    }

    private fun performAction(){
        qrScanIntegrator?.initiateScan()
    }

    override fun onActivityResult(requestCode: Int, resultCode: Int, data: Intent?) {
        val result = IntentIntegrator.parseActivityResult(requestCode,resultCode, data)

        if(result != null){
            //qrCode has no data
            if(result.contents == null){
                Toast.makeText(this,
                getString(R.string.result_not_found),
                Toast.LENGTH_LONG
                ).show()
            }else
            {
                try{
                    val obj = JSONObject(result.contents)

                    binding.name.text = obj.getString("name")
                    binding.siteName.text = obj.getString("site_name")

                }
                catch (e: JSONException){
                    e.printStackTrace()
                    Toast.makeText(this,
                        result.contents,
                        Toast.LENGTH_LONG
                    ).show()
                }
            }
        }else{
            super.onActivityResult(requestCode, resultCode, data)
        }
    }
}
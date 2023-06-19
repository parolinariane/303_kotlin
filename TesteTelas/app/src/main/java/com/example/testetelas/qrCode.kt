package com.example.testetelas

import android.content.Intent
import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import android.util.Log
import android.widget.Button
import android.widget.Toast
import com.example.testetelas.databinding.ActivityMainBinding
import com.example.testetelas.databinding.ActivityQrCodeBinding
import com.google.zxing.integration.android.IntentIntegrator
import org.json.JSONException
import org.json.JSONObject
import java.io.BufferedReader
import java.io.File
import java.io.FileInputStream
import java.io.InputStreamReader

class qrCode : AppCompatActivity() {

    lateinit var btnGoToScan: Button

    lateinit var btnGoToCamera: Button

    private lateinit var binding: ActivityQrCodeBinding

    private var qrScanIntegrator: IntentIntegrator? = null


    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        binding = ActivityQrCodeBinding.inflate(layoutInflater)
        val view = binding.root
        setContentView(view)

        btnGoToCamera = findViewById(R.id.btnPgCamera)
        btnGoToCamera.setOnClickListener {
            val intent = Intent(this, TelaCamera::class.java)
            intent.flags = Intent.FLAG_ACTIVITY_SINGLE_TOP
            intent.putExtra("Chave", "Camera")
            startActivity(intent)
        }

        btnGoToScan = findViewById(R.id.btnScan1)
        btnGoToScan.setOnClickListener {
            val intent = Intent(this, qrCode::class.java)
            intent.flags = Intent.FLAG_ACTIVITY_SINGLE_TOP
            intent.putExtra("Chave", "Tela de login")
            startActivity(intent)
        }

        val string: String? = intent.getStringExtra("Chave")
        setOnClickListener()
        setupScanner()

    }

    private fun setupScanner(){
        qrScanIntegrator = IntentIntegrator(this)
        qrScanIntegrator?.setOrientationLocked(false)
    }

    private fun setOnClickListener(){
        binding.btnScan1.setOnClickListener { performAction() }
        binding.name.text.toString()
    }

    private fun performAction(){
        qrScanIntegrator?.initiateScan()
    }

    override fun onActivityResult(requestCode: Int, resultCode: Int, data: Intent?) {
        val result = IntentIntegrator.parseActivityResult(requestCode, resultCode, data)

        if (result != null) {
            // qrcode has no data
            if (result.contents == null) {
                Toast.makeText(
                    this,
                    getString(R.string.result_not_found),
                    Toast.LENGTH_LONG
                ).show()
            } else {
                try {
                    //val obj = JSONObject(result.contents)
                    binding.name.text = result.contents

                } catch (e: JSONException) {
                    e.printStackTrace()
                    Toast.makeText(
                        this,
                        result.contents,
                        Toast.LENGTH_LONG
                    ).show()
                }
            }

        } else {
            super.onActivityResult(requestCode, resultCode, data)
        }
    }

}
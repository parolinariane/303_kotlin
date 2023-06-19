package com.example.testetelas

import android.content.Intent
import android.os.Binder
import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import android.widget.Button
import com.example.testetelas.databinding.ActivityMainBinding
import com.google.zxing.integration.android.IntentIntegrator

class MainActivity : AppCompatActivity() {

    lateinit var btnGoToLogin: Button

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)

        setContentView(R.layout.activity_main)

        btnGoToLogin = findViewById(R.id.btnPgInicial)
        btnGoToLogin.setOnClickListener {
            val intent = Intent(this, qrCode::class.java)
            intent.flags = Intent.FLAG_ACTIVITY_SINGLE_TOP
            intent.putExtra("Chave", "Tela de login")
            startActivity(intent)
        }
    }
}
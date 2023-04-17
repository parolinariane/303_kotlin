package com.example.listabase

import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import android.widget.Button

class tela2 : AppCompatActivity() {
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_tela2)

        val btnVoltar = findViewById(R.id.btnBack) as Button
        btnVoltar.setOnClickListener{
            this.finish()
        }
    }
}
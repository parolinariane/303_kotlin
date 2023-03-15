package com.example.duastelas

import android.content.Intent
import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import android.widget.Button
import android.widget.TextView

class tela2 : AppCompatActivity() {

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_tela2)

        //recebendo valores da tela que chamou
        val string: String? = intent.getStringExtra("Extra")

        val texto = findViewById(R.id.textView2) as TextView
        texto.setText(string)


        val btnVoltar = findViewById(R.id.btnVoltar) as Button
        btnVoltar.setOnClickListener{
            this.finish()
        }

        val btnNext = findViewById(R.id.btnGoTo3) as Button
        btnNext.setOnClickListener{

            val intent = Intent(this, Tela3::class.java)
            startActivity(intent)
        }
    }
}
package com.example.duastelas

import android.content.Intent
import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import android.widget.Button

class MainActivity : AppCompatActivity() {

    lateinit var btnGoTotela2: Button

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_main) // diz qual a janela que vai começar

        btnGoTotela2 = findViewById(R.id.btnGoToTela1)
        btnGoTotela2.setOnClickListener{
            val intent = Intent(this, tela2::class.java)
            startActivity(intent)
        }
    }


}


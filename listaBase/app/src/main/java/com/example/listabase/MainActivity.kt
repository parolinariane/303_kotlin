package com.example.listabase

import android.content.Intent
import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import android.widget.ArrayAdapter
import android.widget.Button
import android.widget.ListView

class MainActivity : AppCompatActivity() {

    lateinit var btnGoTela2: Button

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_main)

        val arrayAdapter : ArrayAdapter<*>
        val usuarios = arrayOf("São Paulo", "Minas Gerais", "Rio de Janeiro")

        btnGoTela2 = findViewById(R.id.btnGoTela2)
        btnGoTela2.setOnClickListener{

            val intent = Intent(this, tela2::class.java)
            intent.flags = Intent.FLAG_ACTIVITY_SINGLE_TOP
            intent.putExtra("Chave", "Ola tela 2")
            startActivity(intent)
        }


        // acessa a lista a partir de um arquivo xml
        var mListView = findViewById<ListView>(R.id.userlist)

        //cria o adapter
        arrayAdapter = ArrayAdapter(
            this, android.R.layout.simple_list_item_1, usuarios)
            mListView.adapter = arrayAdapter




    }
}
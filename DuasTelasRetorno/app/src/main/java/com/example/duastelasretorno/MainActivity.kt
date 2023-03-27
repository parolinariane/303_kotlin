package com.example.duastelasretorno

import android.app.Activity
import android.content.Intent
import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import android.util.Log
import android.widget.Button
import android.widget.TextView
import androidx.activity.result.ActivityResult
import androidx.activity.result.contract.ActivityResultContracts

class MainActivity : AppCompatActivity() {
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_main)

        val launcher = registerForActivityResult(ActivityResultContracts.StartActivityForResult()){ result: ActivityResult ->
            Log.d("Teste", "Retorno")
            Log.d("Teste", result.resultCode.toString())
            var msg: String = ""

            if(result.resultCode == Activity.RESULT_OK){
               result.data?.let {
                   if(it.hasExtra("param1")){
                    msg = msg + it.getIntExtra("param1", 0).toString()

                   }

                   if(it.hasExtra("param2")){
                       msg = msg + it.getStringExtra("param2").toString()
                   }
               }

                //val bundle: Bundle? = result.data?.extras
                //var msg = bundle?.get("ActivityResult").toString()
                var txt = findViewById(R.id.textView) as TextView
                txt.setText(msg)
            }
        }


        val btnNext = findViewById(R.id.btnGetValor) as Button
        btnNext.setOnClickListener{
            val intent = Intent(this, TelaValores::class.java)
            launcher.launch(intent)
        }
    }


}
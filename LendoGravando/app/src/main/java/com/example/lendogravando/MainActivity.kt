package com.example.lendogravando

import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import android.os.Environment
import android.util.Log
import com.example.lendogravando.databinding.ActivityMainBinding
import java.io.BufferedReader
import java.io.File
import java.io.FileInputStream
import java.io.FileOutputStream
import java.io.IOException
import java.io.InputStreamReader

class MainActivity : AppCompatActivity() {

    private lateinit var binding: ActivityMainBinding

    var fileName = "DS304_${System.currentTimeMillis()}.txt"

    internal var extFile: File?=null

    private val filepath = "Minha_foto"

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        binding = ActivityMainBinding.inflate(layoutInflater)
        setContentView(binding.root)

        binding.btnGrava.setOnClickListener {
            extFile = File(getExternalFilesDir(filepath), fileName)
            Log.e("DEBUG", getExternalFilesDir(filepath).toString())
            try{
                val fileOutputStream = FileOutputStream(extFile)
                fileOutputStream.write(binding.edtMsg.text.toString().toByteArray())
                fileOutputStream.close()

            } catch (e: IOException){
                e.printStackTrace()
            }
            binding.txtMsg.text = "Dados gravados"

        }

        binding.btnLe.setOnClickListener {
            extFile = File(getExternalFilesDir(filepath), fileName)
            Log.e("DEBUG", getExternalFilesDir(filepath).toString())

            if(fileName!=null && fileName.trim() != ""){
                var fileInputStream = FileInputStream(extFile)
                var inputStreamReader: InputStreamReader = InputStreamReader(fileInputStream)
                val bufferedReader: BufferedReader = BufferedReader(inputStreamReader)
                val stringBuild: StringBuilder = StringBuilder()
                var text: String? = null
                while({text = bufferedReader.readLine(); text} () != null){
                    stringBuild.append(text)
                }
                fileInputStream.close()
                binding.txtMsg.text = stringBuild.toString()
            }

        }

        if(!isExxternalStorageAvaible || isExternalStorageReadOnly){
            binding.btnGrava.isEnabled = true
        }




    }

    private val isExxternalStorageAvaible: Boolean get(){
        val extStorageState = Environment.getExternalStorageState()

        return if (Environment.MEDIA_MOUNTED_READ_ONLY.equals(extStorageState)){
            true
        }
        else {
            false
        }

    }

    private val isExternalStorageReadOnly: Boolean get(){
        val extStorageState = Environment.getExternalStorageState()
        return if (Environment.MEDIA_MOUNTED.equals(extStorageState)){
            true
        }
        else {
            false
        }

    }

}
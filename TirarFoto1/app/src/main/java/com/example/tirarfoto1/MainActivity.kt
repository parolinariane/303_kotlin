package com.example.tirarfoto1


import android.Manifest.permission_group.CAMERA
import android.app.Activity
import android.content.ContentValues
import android.content.Intent
import android.content.pm.PackageManager
import android.net.Uri
import android.os.Build
import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import android.provider.MediaStore
import android.widget.Button
import android.widget.ImageView
import androidx.appcompat.app.AlertDialog

class MainActivity : AppCompatActivity() {

    val CAMERA_PERMISSION_CODE = 1000
    val CAMERA_CAPTURE_CODE    = 1001
    val MEMORY_READ_CODE       = 1002
    val MEMORY_WRITE_CODE      = 1003

    private var imageUri: Uri? = null
    private var imagem: ImageView? = null


    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_main)

        imagem = findViewById(R.id.imagem)

        findViewById<Button>(R.id.takepicture).setOnClickListener{
            var permissionGranted = requestCameraPermission()
            if(permissionGranted){
                openCameraInterface()
            }
        }

    }

    private fun requestCameraPermission(): Boolean{
        var permissionGranted = false

        if(Build.VERSION.SDK_INT >= Build.VERSION_CODES.M){

            var cameraPermissionNotGranted = checkSelfPermission(CAMERA)
            if(cameraPermissionNotGranted == PackageManager.PERMISSION_DENIED){
                var permission = arrayOf(CAMERA)
                requestPermissions(permission, CAMERA_PERMISSION_CODE)
            }
            else{
                permissionGranted = true
            }
        }
        else{
            permissionGranted = true
        }
        return permissionGranted
    }

    override fun onRequestPermissionsResult(
        requestCode: Int,
        permissions: Array<out String>,
        grantResults: IntArray
    ) {
        super.onRequestPermissionsResult(requestCode, permissions, grantResults)

        if(requestCode === CAMERA_PERMISSION_CODE){
            if (grantResults.size === 1 && grantResults[0] == PackageManager.PERMISSION_GRANTED){
                openCameraInterface()
            } else{
                showAlert("Permissao negada para a camera")
            }
        }
    }

    private fun showAlert(msg: String){
        val builder = AlertDialog.Builder(this)
        builder.setMessage(msg)
        builder.setPositiveButton("ok", null)
        val dialog = builder.create()
        dialog.show()
    }

    private fun openCameraInterface(){


        val values = ContentValues()
        values.put(MediaStore.Images.Media.TITLE,"Foto tirada")
        values.put(MediaStore.Images.Media.DESCRIPTION, "Aula 303")

        imageUri = this?.contentResolver?.insert(MediaStore.Images.Media.EXTERNAL_CONTENT_URI,values)

        //cria a intent para tirar a foto
        val intent = Intent(MediaStore.ACTION_IMAGE_CAPTURE)
        intent.putExtra(MediaStore.EXTRA_OUTPUT, imageUri)
        startActivityForResult(intent, CAMERA_CAPTURE_CODE)
    }

    //recebe a sinalizaçao que a intent terminou e voltou dados
    override fun onActivityResult(requestCode: Int, resultCode: Int, data: Intent?) {
        super.onActivityResult(requestCode, resultCode, data)

        if(resultCode == Activity.RESULT_OK) {
            if (requestCode === CAMERA_CAPTURE_CODE) {
                imagem?.setImageURI(imageUri)
            }
        }else{
            showAlert("Erro na foto")
        }
    }
}
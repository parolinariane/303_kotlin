package com.example.testetelas

import android.Manifest
import android.Manifest.permission.CAMERA
import android.app.Activity
import android.content.ContentValues
import android.content.Context
import android.content.Intent
import android.content.pm.PackageManager
import android.graphics.Bitmap
import android.graphics.drawable.BitmapDrawable
import android.location.Location
import android.location.LocationListener
import android.location.LocationManager
import android.net.Uri
import android.os.Build
import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import android.os.Environment
import android.provider.MediaStore
import android.util.Base64
import android.util.Log
import android.widget.Button
import android.widget.ImageView
import android.widget.Toast
import androidx.appcompat.app.AlertDialog
import androidx.core.app.ActivityCompat
import androidx.core.content.ContextCompat
import com.example.testetelas.databinding.ActivityTelaCameraBinding
import java.io.ByteArrayOutputStream
import java.io.File
import java.io.FileOutputStream
import java.io.IOException
import java.io.Serializable

class TelaCamera : AppCompatActivity(), LocationListener {

    val CAMERA_PERMISSION_CODE = 1000
    val CAMERA_CAPTURE_CODE    = 1001
    val MEMORY_READ_CODE       = 1002
    val MEMORY_WRITE_CODE      = 1003

    private var imageUri: Uri? = null
    private var imagem: ImageView? = null

    private var locate: Location? = null
    private var imageBS64: String? = null

    lateinit var btnGoToTelaInfos: Button

    private lateinit var binding: ActivityTelaCameraBinding

    var fileName = "DS304_${System.currentTimeMillis()}.txt"

    internal var extFile: File?=null

    private val filepath = "Minha_foto"

    private lateinit var locationManager: LocationManager

    private val locationPermissionCode = 2

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        binding = ActivityTelaCameraBinding.inflate(layoutInflater)
        setContentView(binding.root)

        title = "KotlinApp"

        binding.btnLocation.setOnClickListener (){
            getLocation()
        }

        btnGoToTelaInfos = findViewById(R.id.btnGoToTelaInf)
        btnGoToTelaInfos.setOnClickListener{

            val intent = Intent(this, TelaInfos::class.java)
            intent.putExtra("Chave", DadosI(binding.edtMsg.text.toString(), locate?.latitude.toString(), locate?.longitude.toString(), imageBS64) as Serializable)
            startActivity(intent)
        }

        binding.btnGravar.setOnClickListener {
            extFile = File(getExternalFilesDir(filepath), fileName)
            try{
                val fileOutputStream = FileOutputStream(extFile)
                fileOutputStream.write(binding.edtMsg.text.toString().toByteArray())
                fileOutputStream.close()

            } catch (e: IOException){
                e.printStackTrace()
            }

            binding.txtMsg.text = "Dados gravados"
        }


        val string: String? = intent.getStringExtra("Chave")

        imagem = findViewById(R.id.imgPhoto)

        findViewById<Button>(R.id.btnTakePicture).setOnClickListener {
            var permissionGranted = requestCameraPermission()
            if(permissionGranted){
                openCameraInterface()
            }
        }

        if(!isExxternalStorageAvaible || isExternalStorageReadOnly){
            binding.btnGravar.isEnabled = true
        }


    }

    private fun getLocation(){
        locationManager = getSystemService(Context.LOCATION_SERVICE) as LocationManager

        if(ContextCompat.checkSelfPermission(this, Manifest.permission.ACCESS_FINE_LOCATION) != PackageManager.PERMISSION_GRANTED){
            ActivityCompat.requestPermissions(this, arrayOf(Manifest.permission.ACCESS_FINE_LOCATION), locationPermissionCode)
        }
        locationManager.requestLocationUpdates(LocationManager.GPS_PROVIDER, 5000, 5f, this)
    }


    private val isExxternalStorageAvaible: Boolean get(){
        val extStorageState = Environment.getExternalStorageState()

        return Environment.MEDIA_MOUNTED_READ_ONLY == extStorageState

    }

    private val isExternalStorageReadOnly: Boolean get(){
        val extStorageState = Environment.getExternalStorageState()
        return Environment.MEDIA_MOUNTED == extStorageState

    }


    private fun requestCameraPermission():Boolean{
        var permissionGranted = false
        if(Build.VERSION.SDK_INT >= Build.VERSION_CODES.M){
            var cameraPermissonNotGranted = checkSelfPermission(CAMERA)
            if(cameraPermissonNotGranted == PackageManager.PERMISSION_DENIED){
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
            if(grantResults.size === 1 && grantResults[0] == PackageManager.PERMISSION_GRANTED){
                openCameraInterface()
            }
            else{
                showAlert("Permissao negada para a camera")
            }
        }

        if(requestCode == locationPermissionCode){
            if(grantResults.isNotEmpty() && grantResults[0] == PackageManager.PERMISSION_GRANTED){
                Toast.makeText(this, "Permissao ok\n clique novamente" , Toast.LENGTH_SHORT).show()
            }
            else{
                Toast.makeText(this, "Permissao negada", Toast.LENGTH_SHORT).show()
            }
        }
    }

    private fun showAlert(msg:String){
        val builder = AlertDialog.Builder(this)
        builder.setMessage(msg)
        builder.setPositiveButton("ok", null)
        val dialog = builder.create()
        dialog.show()
    }

    private fun openCameraInterface(){

        val values = ContentValues()
        values.put(MediaStore.Images.Media.TITLE,"Foto tirada")
        values.put(MediaStore.Images.Media.DESCRIPTION,"Trabalho interdisciplinar")

        imageUri = this?.contentResolver?.insert(MediaStore.Images.Media.EXTERNAL_CONTENT_URI,values)

        val intent = Intent(MediaStore.ACTION_IMAGE_CAPTURE)
        intent.putExtra(MediaStore.EXTRA_OUTPUT,imageUri)
        startActivityForResult(intent, CAMERA_CAPTURE_CODE)
    }

    override fun onActivityResult(requestCode: Int, resultCode: Int, data: Intent?) {
        super.onActivityResult(requestCode, resultCode, data)
        if (resultCode == Activity.RESULT_OK) {
            if(requestCode === CAMERA_CAPTURE_CODE) {
                imagem?.setImageURI(imageUri)
                val bmp = (imagem?.getDrawable() as BitmapDrawable).getBitmap()
                val stream = ByteArrayOutputStream()
            bmp.compress(Bitmap.CompressFormat.JPEG,90,stream)
            val imgArr = stream.toByteArray()
            val imgStr: String = Base64.encodeToString(imgArr, Base64.DEFAULT)
                imageBS64 = imgStr
            }
        }else{
            showAlert("Erro na foto")
        }
    }

    override fun onLocationChanged(p0: Location) {
        locate = p0
        binding.txtCoord.text = "Latitude: " + p0.latitude
        binding.longit.text = "Longitude: " + p0.longitude
    }
}
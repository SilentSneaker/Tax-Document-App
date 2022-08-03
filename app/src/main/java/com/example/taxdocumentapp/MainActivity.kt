package com.example.taxdocumentapp

import android.content.Intent
import android.content.pm.PackageManager
import android.graphics.Camera
import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import android.provider.MediaStore
import android.widget.ImageButton
import android.widget.Toast
import androidx.core.app.ActivityCompat
import androidx.core.content.ContextCompat
import java.util.jar.Manifest



 class MainActivity : AppCompatActivity() {

     var CameraClicked:ImageButton? = null

     companion object{
         private const val CAMERA_PERMISSION_CODE = 1
         private const val CAMERA_Request = 2
     }


     override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_main)

         CameraClicked?.setOnClickListener(){
            if(ContextCompat.checkSelfPermission(this,
                android.Manifest.permission.CAMERA) ==
                PackageManager.PERMISSION_GRANTED)

            {
                val intent = Intent(MediaStore.ACTION_IMAGE_CAPTURE)
                startActivityForResult(intent, CAMERA_Request)
            }
             else
            {
                ActivityCompat.requestPermissions(this,
                arrayOf(android.Manifest.permission.CAMERA), CAMERA_PERMISSION_CODE)

            }
         }


    }


}



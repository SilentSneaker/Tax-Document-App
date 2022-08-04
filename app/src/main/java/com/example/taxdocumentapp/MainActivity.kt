package com.example.taxdocumentapp

import android.app.Activity
import android.content.Intent
import android.content.pm.PackageManager
import android.graphics.Bitmap
import android.media.Image
import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import android.provider.MediaStore
import android.widget.ImageButton
import android.widget.Toast
import androidx.core.app.ActivityCompat
import androidx.core.content.ContextCompat

class MainActivity : AppCompatActivity() {
//object to store values
     companion object{
         private const val CAMERA_PERMISSION_CODE = 1
         private const val CAMERA_Request = 2
     }


     override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_main)
//image button commands
         findViewById<ImageButton>(R.id.TakePict).setOnClickListener{
             if(ContextCompat.checkSelfPermission(this,
                     android.Manifest.permission.CAMERA) ==
                 PackageManager.PERMISSION_GRANTED) {
                 val intent = Intent(MediaStore.ACTION_IMAGE_CAPTURE)
                 startActivityForResult(intent, CAMERA_Request)
             } else {
                 ActivityCompat.requestPermissions(this,
                     arrayOf(android.Manifest.permission.CAMERA), CAMERA_PERMISSION_CODE)
             }
         }


    }
// Get permissions for camera if the permissions haven't already been granted
     override fun onRequestPermissionsResult(
         requestCode: Int,
         permissions: Array<out String>,
         grantResults: IntArray
     ) {
         super.onRequestPermissionsResult(requestCode, permissions, grantResults)
         if(requestCode == CAMERA_PERMISSION_CODE)
         {
             if(grantResults.isNotEmpty() && grantResults[0] == PackageManager.PERMISSION_GRANTED)
             {
                 val intent = Intent(MediaStore.ACTION_IMAGE_CAPTURE)
                 startActivityForResult(intent, CAMERA_PERMISSION_CODE)
             }
             else
             {
                 Toast.makeText(this@MainActivity,
                     "Permission for camera was denied.", Toast.LENGTH_LONG).show()
             }
         }
     }
// if activity happens store image thumbnail
     override fun onActivityResult(requestCode: Int, resultCode: Int, data: Intent?) {
         super.onActivityResult(requestCode, resultCode, data)
         if(resultCode == Activity.RESULT_OK)
         {
             if(requestCode == CAMERA_Request)
             {
                 val thumbNail: Bitmap = data!!.extras!!.get("data") as Bitmap
                 findViewById<androidx.appcompat.widget.AppCompatImageView>(R.id.Thumbnail).setImageBitmap(thumbNail)
             }
         }
     }
}



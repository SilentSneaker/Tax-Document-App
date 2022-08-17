package com.example.taxdocumentapp

import android.content.Intent
import android.content.pm.PackageManager

import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import android.os.Environment
import android.provider.MediaStore
import android.view.View
import android.widget.TextView
import android.widget.Toast
import androidx.core.app.ActivityCompat
import androidx.core.content.ContextCompat
import androidx.core.content.FileProvider
import androidx.recyclerview.widget.GridLayoutManager

import androidx.recyclerview.widget.RecyclerView
import java.io.File
import java.io.IOException
import java.text.SimpleDateFormat
import java.util.*




class MainActivity : AppCompatActivity() {




    // creating file name
    var CurrentImageFile: String? = null

    lateinit var tags: List<String>
    //object to store values
    companion object{
        private const val CameraPermissionCode = 1
    }


    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_main)


    }






    // creating unique image name
    private fun getImageFile(): File {
        var timeStamp: String = SimpleDateFormat("yyyyMMdd_HHmmss").format(Date())
        val imageName: String = "jpg_" +timeStamp+"_"
        var storageDir: File? = getExternalFilesDir(Environment.DIRECTORY_PICTURES)

        var imageFile: File = File.createTempFile(imageName, ".jpeg", storageDir)
        CurrentImageFile = imageFile.absolutePath
        return imageFile
    }

    fun snapPicture(view: View) {
        val cameraIntent = Intent(MediaStore.ACTION_IMAGE_CAPTURE)

            var imageFile: File? = null

            try {
                imageFile = getImageFile()
            }
            catch (e: IOException)
            {
                e.printStackTrace()
            }


            if (imageFile != null)
            {
                var imageUri = FileProvider.getUriForFile(this,
                    "com.example.taxdocumentapp",imageFile)
                cameraIntent.putExtra(MediaStore.EXTRA_OUTPUT,imageUri)
                startActivityForResult(cameraIntent, CameraPermissionCode)

            }
            else
            {
                Toast.makeText(this, "Error", Toast.LENGTH_SHORT).show()
            }

    }



}
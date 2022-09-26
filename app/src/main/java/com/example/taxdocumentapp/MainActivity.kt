package com.example.taxdocumentapp

import android.app.Activity
import android.content.Intent
import android.content.pm.PackageManager
import android.graphics.Bitmap
import android.media.Image
import android.net.Uri
import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import android.os.Environment
import android.provider.MediaStore
import android.view.View
import android.widget.ImageButton
import android.widget.ImageView
import android.widget.Toast
import androidx.core.app.ActivityCompat
import androidx.core.content.ContextCompat
import androidx.core.content.FileProvider
import java.io.File
import java.io.IOException
import java.net.URI
import java.text.SimpleDateFormat
import java.util.*
import kotlin.jvm.Throws

class MainActivity : AppCompatActivity() {
//object to store values
     companion object{
         private const val CAMERA_PERMISSION_CODE = 1
     }

    // creating file name
    var CurrentImageFile: String? = null

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_main)
//image button commands

    }

// Get permissions for camera if the permissions haven't already been granted

// if activity happens store image thumbnail

    // creating unique image name
    private fun getImageFile(): File {
        var timeStamp: String = SimpleDateFormat("yyyyMMdd_HHmmss").format(Date())
        var imageName: String = "jpg_" +timeStamp+"_"
        var storageDir: File? = getExternalFilesDir(Environment.DIRECTORY_PICTURES)

        var imageFile: File = File.createTempFile(imageName, ".jpeg", storageDir)
        CurrentImageFile = imageFile.absolutePath
        return imageFile
    }

    fun SnapPicture(view: View) {
        var cameraIntent: Intent = Intent(MediaStore.ACTION_IMAGE_CAPTURE)

        if(cameraIntent.resolveActivity(packageManager)!=null)
        {
            var imageFile: File? = null

            try {
                imageFile = getImageFile()
            }
            catch (e: IOException)
            {
                e.printStackTrace()
            }
            if (imageFile!=null)
            {
                var imageUri: Uri? = FileProvider.getUriForFile(this,
                    "com.example.android.fileprovider",imageFile)
                cameraIntent.putExtra(MediaStore.EXTRA_OUTPUT,imageUri)
                startActivityForResult(cameraIntent, CAMERA_PERMISSION_CODE)
            }
        }
    }
    fun thumbNail(view: View) {

    }
}



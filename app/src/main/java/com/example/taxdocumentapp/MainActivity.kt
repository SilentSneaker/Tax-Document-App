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
import android.provider.ContactsContract
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
        private const val Camera_Request_code = 2
     }

    // creating file name
    var CurrentImageFile: String? = null

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

    fun SnapPicture(view: View) {
        val cameraIntent = Intent(MediaStore.ACTION_IMAGE_CAPTURE)

        if(cameraIntent.resolveActivity(this.packageManager) != null)
        {
            var imageFile: File? = null

            if(imageFile == null)
            {
                imageFile = getImageFile()
            }

            if (imageFile != null)
            {
                var imageUri = FileProvider.getUriForFile(this,
                    "com.example.android.fileprovider",imageFile)
                cameraIntent.putExtra(MediaStore.EXTRA_OUTPUT,imageUri)
                startActivityForResult(cameraIntent, Camera_Request_code)
            }
            else
            {
                Toast.makeText(this, "Error", Toast.LENGTH_SHORT).show()
            }
        }
    }

    override fun onActivityResult(requestCode: Int, resultCode: Int, data: Intent?) {
        super.onActivityResult(requestCode, resultCode, data)
        if (requestCode == Camera_Request_code && requestCode == Activity.RESULT_OK)
        {
            thumbNail()
        }
    }
    fun thumbNail() {
        var intent: Intent? = Intent(this, findViewById(R.id.disImageView))
        intent?.putExtra("image_path", getImageFile() )
        startActivity(intent)
    }
}
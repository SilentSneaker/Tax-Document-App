package com.example.taxdocumentapp

import android.content.Intent
import android.graphics.Bitmap
import android.media.Image

import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import android.os.Environment
import android.provider.MediaStore
import android.view.View
import android.widget.TextView
import android.widget.Toast
import androidx.activity.result.ActivityResult
import androidx.core.content.FileProvider

import androidx.recyclerview.widget.RecyclerView
import java.io.File
import java.io.IOException
import java.text.SimpleDateFormat
import java.util.*




class MainActivity : AppCompatActivity() {



    // creating file name
    var CurrentImageFile: String? = null
    var imgfil: File? = null

    lateinit var tags: List<String>
    //object to store values

        private val cameraPermissionCode = 1



    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_main)




    }





    // creating unique image name
    private fun setImageFile(): File {
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
                imageFile = setImageFile()
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
                startActivityForResult(cameraIntent, cameraPermissionCode)
                imgfil = imageFile
            }
            else
            {
                Toast.makeText(this, "Error", Toast.LENGTH_SHORT).show()
            }
    }

    @Override
    override fun onActivityResult(requestCode: Int, resultCode: Int, data: Intent?) {
        super.onActivityResult(requestCode, resultCode, data)

        if (requestCode == cameraPermissionCode && resultCode == RESULT_OK){

            var intent = Intent(this, SetDocInfo::class.java)
            startActivity(intent)
        }
    }



    fun goToGallery(view: View) {
        var intent = Intent(this, Gallery::class.java)
        startActivity(intent)
    }

    fun goToContactUs(view: View) {
        var int = Intent(this, contactUsScreen::class.java)
        startActivity(int)

    }
    fun goToTotalsScreen(view: View) {


    }

}
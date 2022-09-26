package com.example.taxdocumentapp

import android.content.Intent

import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import android.os.Environment
import android.provider.MediaStore
import android.view.View
import android.widget.Toast
import androidx.core.content.FileProvider
import java.io.*
import java.lang.Exception

import java.net.URL
import java.text.SimpleDateFormat
import java.util.*


class MainActivity : AppCompatActivity() {



    // creating file name
    var CurrentImageFile: String? = null
    companion object{
        lateinit var imFile: URL
        var read = false

    }


    lateinit var tags: List<String>
    //object to store values

        private val cameraPermissionCode = 1



    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_main)

        if (!read) {


            readSavedDocument()
            read = true
        }

    }


    fun readSavedDocument(){

        var fileInputStream: FileInputStream? = null
        try {
            fileInputStream = openFileInput("SavedDocuments.txt")

        var inputStreamReader = InputStreamReader(fileInputStream)
        val bufferedReader = BufferedReader(inputStreamReader)
        val stringBuilder = StringBuilder()
        var text: String? = null
        while (run {
                text = bufferedReader.readLine()
                text
            } != null){
            stringBuilder.append(text)
        }
        var m = stringBuilder.split("╠╬").toList()
        var documents = m[0].split("╠").toList()
        for (file in documents){
            var doc = file.split("*").toList()

                var newDocument = Document(doc[0], doc[1],doc[2], doc[3])
            Gallery.docList.add(newDocument)

        }
       SetDocInfo.list = m[1].split("╬").toMutableList()
        }
        catch (e: Exception){
            e.printStackTrace()
        }

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
                imFile = imageFile.toURL()
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
        var intent = Intent(this, TotalsScreen::class.java)
        startActivity(intent)
    }

}
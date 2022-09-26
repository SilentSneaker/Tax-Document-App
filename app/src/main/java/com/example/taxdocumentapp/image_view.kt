package com.example.taxdocumentapp

import android.content.Context
import android.content.Intent
import android.net.Uri
import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import android.view.View
import android.widget.EditText
import android.widget.ImageView
import android.widget.TextView
import android.widget.Toast
import java.io.File
import java.lang.Exception

class image_view : AppCompatActivity() {
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_image_view)

       findViewById<ImageView>(R.id.imageView).setImageURI(Uri.parse(MainActivity.imFile.toString()))
    }

    fun ToDocuments(view: View) {
        var intent = Intent(this, Gallery::class.java)
        startActivity(intent)
        finish()
    }
    fun toEditDocInfo(view: View) {
        startActivity(Intent(this, EditDocumentInfo::class.java))

    }

}
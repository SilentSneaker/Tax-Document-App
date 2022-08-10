package com.example.taxdocumentapp

import android.graphics.Bitmap
import android.graphics.BitmapFactory
import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import android.view.View
import android.widget.ImageView

class ImageDisplay : AppCompatActivity() {

    var imageView: ImageView? = null

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_image_display)

         imageView = findViewById<ImageView>(R.id.disImageView)

        var bitmap = BitmapFactory.decodeFile(intent.getStringExtra("image_path"))
        imageView?.setImageBitmap(bitmap)
    }
}
package com.example.taxdocumentapp

import android.content.pm.PackageManager
import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import android.widget.TextView
import android.widget.Toast
import androidx.core.app.ActivityCompat
import androidx.core.content.ContextCompat
import androidx.recyclerview.widget.GridLayoutManager
import androidx.recyclerview.widget.RecyclerView
import com.example.taxdocumentapp.ImageAdapter.PhotoListener

class Gallery : AppCompatActivity() {

    lateinit var recyclerView: RecyclerView
    lateinit var galleryAdapter: ImageAdapter
    lateinit var images: List<String>
    lateinit var gallery_number: TextView
    private var readPermission = 1





    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_tags)

        gallery_number = findViewById(R.id.gallery_number)
        recyclerView = findViewById(R.id.recycleImage_view)

        //check permissions

        if (ContextCompat.checkSelfPermission(this, android.Manifest.permission.READ_EXTERNAL_STORAGE)
            != PackageManager.PERMISSION_GRANTED){
            ActivityCompat.requestPermissions(this, arrayOf(android.Manifest.permission.READ_EXTERNAL_STORAGE), readPermission)
        }
        else
        {
            loadImages()
        }

    }

    override fun onRequestPermissionsResult(
        requestCode: Int,
        permissions: Array<out String>,
        grantResults: IntArray
    ) {
        super.onRequestPermissionsResult(requestCode, permissions, grantResults)

        if(requestCode == readPermission)
        {
            if (grantResults[0] == PackageManager.PERMISSION_GRANTED)
            {
                Toast.makeText(this, "Permissions to read storage: accepted", Toast.LENGTH_LONG).show()
                loadImages()
            }
            else{
                Toast.makeText(this, "Storage Permissions Granted", Toast.LENGTH_LONG).show()
            }
        }
    }


    private fun loadImages(){

        recyclerView.setHasFixedSize(true)
        recyclerView.layoutManager = GridLayoutManager(this, 4)
        images = ImageGallery.listOfImages(this)
        galleryAdapter = ImageAdapter(this, images, PhotoListener() {
            @Override
            fun onPhotoClick(path: String){
                //put stuff here
                Toast.makeText(this, ""+path, Toast.LENGTH_LONG).show()
            }
        })

        recyclerView.adapter = galleryAdapter
        gallery_number.text = "Photos (" + images.size + ")"
    }
}
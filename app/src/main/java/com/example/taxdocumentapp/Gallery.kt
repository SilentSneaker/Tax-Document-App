package com.example.taxdocumentapp

import android.content.Intent
import android.content.pm.PackageManager
import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import android.widget.TextView
import android.widget.Toast
import androidx.core.app.ActivityCompat
import androidx.core.content.ContextCompat
import androidx.recyclerview.widget.GridLayoutManager
import androidx.recyclerview.widget.RecyclerView
import com.example.taxdocumentapp.GalleryAdapter.PhotoListener

class Gallery : AppCompatActivity() {

    lateinit var recyclerView: RecyclerView
    lateinit var galleryAdapter: GalleryAdapter
    lateinit var images: List<String>
    lateinit var gallery_number: TextView
    private var readPermission = 1




    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.gallery_view)


        gallery_number = findViewById(R.id.gallery_number)
        recyclerView = findViewById(R.id.recycleImage_view)

        //check permissions

        if (ContextCompat.checkSelfPermission(this, android.Manifest.permission.READ_EXTERNAL_STORAGE)
            != PackageManager.PERMISSION_GRANTED){
            ActivityCompat.requestPermissions(this, arrayOf(android.Manifest.permission.READ_EXTERNAL_STORAGE), readPermission)
        }
        else
        {

            recyclerView.setHasFixedSize(true)
            recyclerView.layoutManager = GridLayoutManager(this, 4)

            images = ImageGallery.ListOfImages(this)

            galleryAdapter = GalleryAdapter(
                this, images, GalleryAdapter.PhotoListener {
                    fun onPhotoClick(path: String) {
                        //put stuff here
                        Toast.makeText(this, "" + path, Toast.LENGTH_LONG).show()
                    }
                })

            recyclerView.adapter = galleryAdapter
            gallery_number.text = "Photos (" + images.size + ")"
            Toast.makeText(this, "Done", Toast.LENGTH_LONG).show()
        }

    }
    fun galleryUp(){

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

            }
            else{
                Toast.makeText(this, "Storage Permissions Failed", Toast.LENGTH_LONG).show()
                var intent = Intent(this, MainActivity::class.java)
                startActivity(intent)
            }
        }
    }


    private fun loadImages(){

        recyclerView.setHasFixedSize(true)
        recyclerView.layoutManager = GridLayoutManager(this, 4)

        images = ImageGallery.ListOfImages(this)

        galleryAdapter = GalleryAdapter(
            this, images, GalleryAdapter.PhotoListener {
                fun onPhotoClick(path: String) {
                    //put stuff here
                    Toast.makeText(this, "" + path, Toast.LENGTH_LONG).show()
                }
            })

        recyclerView.adapter = galleryAdapter
        gallery_number.text = "Photos (" + images.size + ")"
        Toast.makeText(this, "Done", Toast.LENGTH_LONG).show()
    }
}
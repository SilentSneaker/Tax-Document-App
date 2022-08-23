package com.example.taxdocumentapp

import android.content.Intent
import android.content.pm.PackageManager
import android.net.Uri
import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import android.widget.*
import androidx.core.app.ActivityCompat
import androidx.core.content.ContextCompat
import androidx.recyclerview.widget.GridLayoutManager
import androidx.recyclerview.widget.RecyclerView
import com.nostra13.universalimageloader.BuildConfig

class Gallery : AppCompatActivity() {





    public companion object{
       var docList: ArrayList<Document> = ArrayList<Document>()

    }



    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.gallery_view)


        var tempDoc = Document("100", "10/20/2010", "Happy","android.resource://"+ BuildConfig.APPLICATION_ID+"/"+R.drawable.image_failed)

        docList.add(tempDoc)
        docList.add(tempDoc)
        docList.add(tempDoc)
        docList.add(tempDoc)
        docList.add(tempDoc)
        docList.add(tempDoc)
        docList.add(tempDoc)
        docList.add(tempDoc)
        docList.add(tempDoc)
        docList.add(tempDoc)
        docList.add(tempDoc)
        docList.add(tempDoc)

        var adapter = GalleryAdapter(this, R.layout.adapter_view_layout, docList)
        var mListView: ListView? = findViewById<ListView>(R.id.theList)
        mListView?.adapter = adapter
    }
    fun setDataMemebers() {

        var date = findViewById<EditText>(R.id.editTextDate)
        var total = findViewById<EditText>(R.id.SetTotal)
        var tag = findViewById<EditText>(R.id.objectTag)

        var Doc: Document = Document(total.text.toString(), date.text.toString(), tag.text.toString(), MainActivity::imgfil.toString() )

        docList.add(Doc)
    }
}
package com.example.taxdocumentapp

import android.content.DialogInterface
import android.content.Intent
import android.media.Image
import android.net.Uri
import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import android.view.View
import android.widget.*
import androidx.appcompat.app.AlertDialog
import androidx.core.net.toFile
import androidx.core.net.toUri
import java.io.File
import java.io.FileInputStream
import java.net.URI
import java.net.URL
import java.util.*
import kotlin.collections.ArrayList


class Gallery : AppCompatActivity() {





    companion object{
       var docList: ArrayList<Document> = ArrayList<Document>()
        var openImage = ""
    }



    var selectedTag: BooleanArray = BooleanArray(SetDocInfo.list.size)
    var checkedTags: ArrayList<Int> = ArrayList(SetDocInfo.list.size)
    var list: MutableList<String> = SetDocInfo.list
    lateinit var searchTags: MutableList<String>

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.gallery_view)

        val sortBy = findViewById<TextView>(R.id.GalleryScreenDropDownSortBy)
        val sortByList = arrayListOf<String>("Descending Date", "Ascending Date", "Descending Total", "Ascending Total")






        var newDocList = docList


        var adapter = GalleryAdapter(this, R.layout.adapter_view_layout, newDocList)
        var mListView: ListView? = findViewById<ListView>(R.id.GalleryScreenList)
        mListView?.adapter = adapter

        mListView?.setOnItemClickListener { GalleryAdapter, view, i, l->

            MainActivity.imFile = URL(adapter.getItem(i)!!.imageLocation)

            startActivity(Intent(this, image_view::class.java))
        }


        sortByWhat(1)
        sortBy.setOnClickListener(View.OnClickListener {

            var builder: AlertDialog.Builder = AlertDialog.Builder(this)

            builder.setTitle("Sort By")

            builder.setCancelable(false)
            builder.setSingleChoiceItems(sortByList.toTypedArray(), -1) {dialogInterface, i ->
                sortBy.text = sortByList[i]
                adapter.notifyDataSetChanged()
                sortByWhat(i)
                dialogInterface.dismiss()
            }


            builder.setNeutralButton("Cancel"){dialog:DialogInterface, which: Int ->
                dialog.cancel()

            }
            builder.show()
        })




        var Tag = findViewById<TextView>(R.id.GalleryScreenDropDown)


        Tag.setOnClickListener(View.OnClickListener {

            var builder: AlertDialog.Builder = AlertDialog.Builder(this)

            builder.setTitle("Select Tag")

            builder.setCancelable(false)
            builder.setMultiChoiceItems(list.toTypedArray(), selectedTag, DialogInterface.OnMultiChoiceClickListener(){
                    dialog: DialogInterface?, which: Int, isChecked: Boolean ->

                if (isChecked){
                    checkedTags.add(which)
                }
                else {
                    checkedTags.remove(which)
                }
            } )
            builder.setPositiveButton("OK", DialogInterface.OnClickListener(){
                    dialogInterface: DialogInterface?, i: Int ->

                var stringBuilder: StringBuilder = StringBuilder()

                for (i in checkedTags.indices){
                    stringBuilder.append(list[checkedTags[i]])
                    if (i != checkedTags.size -1 ){
                        stringBuilder.append(", ")
                    }
                }
                Tag.text = stringBuilder.toString()
                searchTags = if(Tag.text!= ""){
                    Tag?.text.toString().split(", ").toMutableList()
                } else{
                    mutableListOf()
                }
                newDocList = searchForDoc()
                var adapter = GalleryAdapter(this, R.layout.adapter_view_layout, newDocList)
                var mListView: ListView? = findViewById(R.id.GalleryScreenList)
                mListView?.adapter = adapter

            })
            builder.setNegativeButton("Cancel", DialogInterface.OnClickListener(){
                    dialogInterFace: DialogInterface, i: Int ->
                dialogInterFace.dismiss()
            })
            builder.setNeutralButton("Clear All", DialogInterface.OnClickListener(){
                    dialog: DialogInterface?, which: Int ->

                for (i in selectedTag.indices){
                    selectedTag[i] = false
                    checkedTags.clear()
                    Tag.text = ""
                }
                newDocList = docList
                var adapter = GalleryAdapter(this, R.layout.adapter_view_layout, newDocList)
                var mListView: ListView? = findViewById(R.id.GalleryScreenList)
                mListView?.adapter = adapter
            })
            builder.show()
        })
    }




    fun searchForDoc(): ArrayList<Document> {

        var foundDocuments: ArrayList<Document> = ArrayList()
        for (i in docList){
            if (i.Tags.containsAll(searchTags)) {
                foundDocuments.add(i)
            }
        }
        return foundDocuments
    }

    fun sortByWhat(i: Int){
        if (i==0){
            docList.sortWith(compareBy { Date.parse(it.date) })
        }
        else if (i==1){
            docList.sortWith(compareBy<Document> { Date.parse(it.date) }.reversed())
        }
        else if (i==3){
            docList.sortByDescending {  it.total.toFloat() }
        }
        else if(i==2){
            docList.sortBy { it.total.toFloat() }
        }
    }

    fun ToMain(view: View) {
        var intent = Intent(this, MainActivity::class.java)
        startActivity(intent)
    }

}
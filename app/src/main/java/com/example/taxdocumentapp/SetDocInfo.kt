package com.example.taxdocumentapp

import android.content.Intent
import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import android.view.View

class SetDocInfo : AppCompatActivity() {
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_set_doc_info)



    }

    fun Enter(view: View) {

        Gallery::setDataMemebers
        startActivity(Intent(this, Gallery::class.java))
    }

    fun Cancel(view: View) {

        startActivity(Intent(this, MainActivity::class.java))
        applicationContext.deleteFile(MainActivity::imgfil.name)
    }



}
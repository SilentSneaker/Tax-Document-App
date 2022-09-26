package com.example.taxdocumentapp

import android.annotation.SuppressLint
import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import android.view.View
import android.widget.*

class CreateTag : AppCompatActivity() {
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_create_tag)
    }

    fun ExitActivity(view: View) {

        finish()
    }
    @SuppressLint("NotConstructor")
    fun CreateTag(view: View) {

        // TODO: make it so that tags can only be added once
        SetDocInfo.list.add(findViewById<EditText>(R.id.AddTagTextBox).text.toString())
        Toast.makeText(this, findViewById<EditText>(R.id.AddTagTextBox).text.toString()
                + " has been added to tags", Toast.LENGTH_SHORT).show()
        findViewById<EditText>(R.id.AddTagTextBox).setText("")
        SetDocInfo.changed = true





    }
}
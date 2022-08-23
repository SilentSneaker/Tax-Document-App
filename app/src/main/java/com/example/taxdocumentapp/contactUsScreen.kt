package com.example.taxdocumentapp

import android.content.Intent
import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import android.view.View

class contactUsScreen : AppCompatActivity() {
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_contact_us_screen)
    }

    fun ToMain(view: View) {
        var int = Intent(this, MainActivity::class.java)
        startActivity(int)
    }
}
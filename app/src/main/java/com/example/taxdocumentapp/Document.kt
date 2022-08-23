package com.example.taxdocumentapp

public class Document(_total: String , _date: String, _tag: String, imageLoc: String) {

    var total = _total
    var date = _date
    var tag = _tag
    var imageLocation = imageLoc

    lateinit var Tags: List<String>


}
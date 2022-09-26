package com.example.taxdocumentapp

import android.app.DatePickerDialog
import android.app.Dialog
import android.content.Context
import android.content.DialogInterface
import android.content.Intent
import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import android.view.View
import android.widget.*
import androidx.appcompat.app.AlertDialog
import androidx.fragment.app.DialogFragment
import java.io.File
import java.lang.Exception
import java.text.SimpleDateFormat
import java.util.*
import kotlin.collections.ArrayList

class EditDocumentInfo : AppCompatActivity(), DateSelected {

    var selectedTag = BooleanArray(SetDocInfo.list.size)
    var checkedTags: ArrayList<Int> = ArrayList(SetDocInfo.list.size)
    var list: MutableList<String> = SetDocInfo.list
    var searchTags: MutableList<String> = mutableListOf()




    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_edit_document_info)







        var Tag = findViewById<TextView>(R.id.EditobjectTag)
        setScreen()
        Tag.setOnClickListener(View.OnClickListener {

            var builder: AlertDialog.Builder = AlertDialog.Builder(this)

            builder.setTitle("Select Tag")

            builder.setCancelable(false)
            builder.setMultiChoiceItems(list.toTypedArray(), selectedTag, DialogInterface.OnMultiChoiceClickListener(){
                    dialog: DialogInterface?, which: Int, isChecked: Boolean ->

                if (isChecked){
                    checkedTags.add(which)
                }
                else{
                    checkedTags.remove(which)
                }
            } )
            builder.setPositiveButton("OK", DialogInterface.OnClickListener(){
                    dialogInterface: DialogInterface?, i: Int ->

                var stringBuilder: StringBuilder = StringBuilder()

                for (i in checkedTags.indices){
                    stringBuilder.append(list[checkedTags[i]])
                    if (i != checkedTags.lastIndex ){
                        stringBuilder.append(", ")
                    }
                }
                Tag.text = stringBuilder.toString()

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

            })
            builder.show()
        })
        var button = findViewById<Button>(R.id.EditDocInfoAddTagButton)
        button.setOnClickListener() {
            var intent = Intent(this, CreateTag::class.java)
            startActivity(intent)
        }

    }

    private fun setDataMembers(){

        for (i in Gallery.docList){
            if (MainActivity.imFile.toString() == i.imageLocation){
                i.date = findViewById<TextView>(R.id.editTextDate).text.toString()
                i.total = findViewById<EditText>(R.id.EditTotal).text.toString()
                i.tag = findViewById<TextView>(R.id.EditobjectTag).text.toString()
            }
        }

    }
    fun Enter(view: View) {
        setDataMembers()
        startActivity(Intent(this,Gallery::class.java))
        saveDocsToFile()
    }
    fun Cancel(view: View) {
        finish()
    }

    fun setScreen(){
        for (i in Gallery.docList){
            if (i.imageLocation == MainActivity.imFile.toString()){
                findViewById<EditText>(R.id.EditTotal).setText(i.total)
                findViewById<TextView>(R.id.editTextDate).text = i.date
                findViewById<TextView>(R.id.EditobjectTag).text = i.tag


            }
        }
    }

    fun saveDocsToFile(){

        val saveFile = "SavedDocuments.txt"

        val data = docToString(Gallery.docList)
        val fileOutputStream = openFileOutput(saveFile, Context.MODE_PRIVATE)
        try {
            fileOutputStream.write(data.toByteArray())

        }
        catch (e: Exception){
            e.printStackTrace()
        }
    }
    fun docToString(doc: ArrayList<Document>): String{
        var save = ""

        for (i in doc){
            save += i.total + "*" + i.date + "*" + i.tag + "*" + i.imageLocation + "╠"
        }

        for (i in list){
            save += "╬" + i
        }

        return save
    }




    class DatePickerFragment(val dateSelected: DateSelected) : DialogFragment(), DatePickerDialog.OnDateSetListener{
        override fun onCreateDialog(savedInstanceState: Bundle?): Dialog {

            val calendar = Calendar.getInstance()
            val year = calendar.get(Calendar.YEAR)
            val month = calendar.get(Calendar.MONTH)
            val day = calendar.get(Calendar.DAY_OF_MONTH)

            return DatePickerDialog(requireContext(), this,year,month,day)
        }

        override fun onDateSet(view: DatePicker?, year: Int, month: Int, dayOfMonth: Int) {

            dateSelected.receivedDate(year, month, dayOfMonth)
        }
    }

    fun pullUpCalendar(view: View) {

        val datePickerFragment = DatePickerFragment(this)
        datePickerFragment.show(supportFragmentManager, "DatePicker")
    }

   override fun receivedDate(year: Int, month: Int, day: Int) {

        val calendar = GregorianCalendar()
        calendar.set(year, month, day)

        val viewFormat = SimpleDateFormat("dd-MMM-yyyy")
        var viewFormattedDate = viewFormat.format(calendar.time)
        findViewById<TextView>(R.id.editTextDate).text = viewFormattedDate
    }

    fun Delete(view: View) {

        val file = File(MainActivity.imFile.file)
        for (i in Gallery.docList){
            if (i.imageLocation == MainActivity.imFile.toString()){
                Gallery.docList.remove(i)
                break
            }

        }
        if(file.delete())
            Toast.makeText(this, "Image Deleted", Toast.LENGTH_SHORT).show()
        else
            Toast.makeText(this, "Not Deleted", Toast.LENGTH_SHORT).show()
        startActivity(Intent(this, Gallery::class.java))
    }

    override fun onStop() {
        super.onStop()
        saveDocsToFile()
    }
}
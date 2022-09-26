package com.example.taxdocumentapp

import android.content.DialogInterface
import android.content.Intent
import android.icu.text.DateTimePatternGenerator
import android.icu.text.MeasureFormat
import android.os.Bundle
import android.view.View
import android.widget.Button
import android.widget.TextView
import androidx.appcompat.app.AlertDialog
import androidx.appcompat.app.AppCompatActivity
import androidx.core.view.isVisible
import com.github.mikephil.charting.charts.BarChart
import com.github.mikephil.charting.components.AxisBase
import com.github.mikephil.charting.components.Description
import com.github.mikephil.charting.components.XAxis
import com.github.mikephil.charting.data.BarData
import com.github.mikephil.charting.data.BarDataSet
import com.github.mikephil.charting.data.BarEntry
import com.github.mikephil.charting.formatter.IAxisValueFormatter
import com.github.mikephil.charting.formatter.IndexAxisValueFormatter
import com.github.mikephil.charting.formatter.ValueFormatter
import org.w3c.dom.Text
import java.time.LocalDate
import java.time.format.DateTimeFormatter
import kotlin.math.roundToInt


class TotalsScreen : AppCompatActivity() {

    var barList: ArrayList<BarEntry> = ArrayList()
    lateinit var barDataSet: BarDataSet
    lateinit var barData: BarData


    var selectedTag: BooleanArray = BooleanArray(SetDocInfo.list.size)
    var checkedTags: ArrayList<Int> = ArrayList(SetDocInfo.list.size)
    var list: MutableList<String> = SetDocInfo.list
    var searchTags: MutableList<String> = mutableListOf()
    var ytd1: Float = 0F

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.totals_activity_screen)

        val chart = findViewById<BarChart>(R.id.barChart)
        val Tag = findViewById<TextView>(R.id.TotalsScreenDropDown)

        barList = sortDates()
        barDataSet = BarDataSet(barList," Total " + Tag.text.toString())
        barData = BarData(barDataSet)
        chart.data = barData
        chart.isDragEnabled = true
        chart.data.barWidth = .5f

        var xLabel = arrayListOf<String>()

        for (i in 0..11){
            var m =""
            for (y in 0..2){
                m += LocalDate.now().minusMonths(i.toLong()).month.toString()[y]

            }


           xLabel.add(m)
        }
        xLabel.reverse()
        val xAxis: XAxis = chart.xAxis


        xAxis.position = XAxis.XAxisPosition.TOP;
        barDataSet.valueTextSize = 10f
        chart.xAxis.valueFormatter = IndexAxisValueFormatter(xLabel)
        chart.animateY(1000)
        chart.data = barData
        xAxis.granularity = 1f
        xAxis.setDrawGridLines(false)
        xAxis.labelCount = 12
        chart.setScaleMinima(1f, 1f)
        chart.zoom(3f, 0f, 12f, chart.axisLeft.mAxisMaximum)
        chart.axisLeft.granularity = 1f
        chart.moveViewToX(12f)
        chart.description.text = "Total YTD: $ytd1"
        chart.description.textSize = 15f
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

                barList = sortDates()


                barDataSet = BarDataSet(barList," Total " + Tag.text.toString())
                barData = BarData(barDataSet)
                chart.data = barData
                chart.notifyDataSetChanged()
                chart.invalidate()
                barDataSet.color = resources.getColor(R.color.teal_200)
                barDataSet.valueTextSize = 10f
                chart.animateY(1000)
                chart.description.text = "Total YTD: $ytd1"
                chart.description.textSize = 15f
                barDataSet.valueTextSize = 10f
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

                }
                Tag.text = ""
                searchTags = mutableListOf()
                barList = sortDates()


                barDataSet = BarDataSet(barList," Total " + Tag.text.toString())
                barData = BarData(barDataSet)
                chart.data = barData
                chart.notifyDataSetChanged()
                chart.invalidate()
                barDataSet.color = resources.getColor(R.color.teal_200)
                barDataSet.valueTextSize = 10f
                chart.animateY(1000)
                chart.description.text = "Total YTD: $ytd1"
                chart.description.textSize = 15f
                barDataSet.valueTextSize = 10f
            })
            builder.show()
        })


        findViewById<Button>(R.id.compareButton).setOnClickListener(View.OnClickListener {
            startActivity(Intent(this, compareIncome::class.java))
        })



    }


    fun searchForDoc(): ArrayList<Document> {


        var foundDocuments: ArrayList<Document> = ArrayList()

        for (i in Gallery.docList){
            if (i.Tags.containsAll(searchTags)) {
                foundDocuments.add(i)
            }
        }
        return foundDocuments
    }




    fun sortDates(): ArrayList<BarEntry>{
        ytd1 = 0f
        barList = ArrayList()
        var a = 0f
        var b = 0f
        var c = 0f
        var d = 0f
        var e = 0f
        var f = 0f
        var g = 0f
        var h = 0f
        var j = 0f
        var k = 0f
        var l = 0f
        var m = 0f


        var format = DateTimeFormatter.ofPattern("dd-MMM-yyyy")
        var newList = searchForDoc()
        for (i in newList){
            when (LocalDate.parse(i.date, format).month) {
                LocalDate.now().month -> {
                    a += i.total.toFloat()

                }
                LocalDate.now().minusMonths(1).month -> {
                    b += i.total.toFloat()
                }
                LocalDate.now().minusMonths(2).month -> {
                    c += i.total.toFloat()
                }
                LocalDate.now().minusMonths(3).month -> {
                    d += i.total.toFloat()
                }
                LocalDate.now().minusMonths(4).month -> {
                    e += i.total.toFloat()
                }

                LocalDate.now().minusMonths(5).month -> {
                    f += i.total.toFloat()
                }
                LocalDate.now().minusMonths(6).month -> {
                    g += i.total.toFloat()
                }
                LocalDate.now().minusMonths(7).month -> {
                    h += i.total.toFloat()
                }
                LocalDate.now().minusMonths(8).month -> {
                    j += i.total.toFloat()
                }
                LocalDate.now().minusMonths(9).month -> {
                    k += i.total.toFloat()
                }
                LocalDate.now().minusMonths(10).month -> {
                    l += i.total.toFloat()
                }
                LocalDate.now().minusMonths(11).month -> {
                    m += i.total.toFloat()
                }
                else ->{

                }
            }

        }
        barList.add(BarEntry(11f,a))
        barList.add(BarEntry(10f,b))
        barList.add(BarEntry(9f,c))
        barList.add(BarEntry(8f,d))
        barList.add(BarEntry(7f,e))
        barList.add(BarEntry(6f,f))
        barList.add(BarEntry(5f,g))
        barList.add(BarEntry(4f,h))
        barList.add(BarEntry(3f,j))
        barList.add(BarEntry(2f,k))
        barList.add(BarEntry(1f,l))
        barList.add(BarEntry(0f,m))

        for (i in newList){
            if (LocalDate.now().minusMonths(12) < LocalDate.parse(i.date,format)){
                ytd1 += i.total.toFloat()
            }
        }

        return barList
    }

    fun toMain(view: View) {
        startActivity(Intent(this,MainActivity::class.java))
    }

    fun compare(view: View) {
        startActivity(Intent(this, compareIncome::class.java))
    }

}

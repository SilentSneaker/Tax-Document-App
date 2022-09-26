package com.example.taxdocumentapp

import android.content.DialogInterface
import android.content.Intent
import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import android.view.View
import android.widget.TextView
import androidx.appcompat.app.AlertDialog
import com.github.mikephil.charting.charts.BarChart
import com.github.mikephil.charting.components.XAxis
import com.github.mikephil.charting.data.BarData
import com.github.mikephil.charting.data.BarDataSet
import com.github.mikephil.charting.data.BarEntry
import com.github.mikephil.charting.formatter.IndexAxisValueFormatter
import java.time.LocalDate
import java.time.format.DateTimeFormatter

class compareIncome : AppCompatActivity() {


    var barList: ArrayList<BarEntry> = ArrayList()
    lateinit var barDataSet: BarDataSet
    lateinit var barData: BarData

    var selectedTag: BooleanArray = BooleanArray(SetDocInfo.list.size)
    var checkedTags: ArrayList<Int> = ArrayList(SetDocInfo.list.size)
    var list: MutableList<String> = SetDocInfo.list
    var searchTags: MutableList<String> = mutableListOf()


    var barList2 = ArrayList<BarEntry>()
    lateinit var barDataSet2: BarDataSet


    var selectedTag2 = BooleanArray(SetDocInfo.list.size)
    var checkedTags2 = ArrayList<Int>(SetDocInfo.list.size)
    var list2 = SetDocInfo.list
    var searchTags2 = mutableListOf<String>()


    var ytd1 = 0f
    var ytd2 = 0f


    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_compare_income)


        val chart = findViewById<BarChart>(R.id.compareBarChart)
        val Tag = findViewById<TextView>(R.id.compareScreenDropDown)
        val Tag2 = findViewById<TextView>(R.id.compareTags)


        val xLabel = arrayListOf<String>()

        for (i in 0..11) {
            var m = ""
            for (y in 0..2) {
                m += LocalDate.now().minusMonths(i.toLong()).month.toString()[y]

            }


            xLabel.add(m)
        }

        barList = sortDates()
        barList2 = sortDates2()
        barDataSet = BarDataSet(barList, " Total " + Tag.text.toString())
        barDataSet2 = BarDataSet(barList2, " Total " + Tag2.text.toString())
        barDataSet.color = resources.getColor(R.color.teal_200)
        barDataSet2.color = resources.getColor(R.color.teal_700)
        barData = BarData(barDataSet, barDataSet2)
        barData.barWidth = 0.3f







        chart.data = barData

        chart.isDragEnabled = true
        xLabel.reverse()
        val xAxis: XAxis = chart.xAxis


        xAxis.position = XAxis.XAxisPosition.TOP
        barDataSet.valueTextSize = 10f
        barDataSet2.valueTextSize = 10f
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
        chart.description.text = "Total YTD: $ytd1 \t\tTotal YTD: $ytd2"
        chart.description.textSize = 15f


        Tag.setOnClickListener(View.OnClickListener {

            var builder: AlertDialog.Builder = AlertDialog.Builder(this)

            builder.setTitle("Select Tag")

            builder.setCancelable(false)
            builder.setMultiChoiceItems(
                list.toTypedArray(),
                selectedTag,
                DialogInterface.OnMultiChoiceClickListener() { dialog: DialogInterface?, which: Int, isChecked: Boolean ->

                    if (isChecked) {
                        checkedTags.add(which)
                    } else {
                        checkedTags.remove(which)
                    }
                })
            builder.setPositiveButton(
                "OK",
                DialogInterface.OnClickListener() { dialogInterface: DialogInterface?, i: Int ->

                    var stringBuilder: StringBuilder = StringBuilder()

                    for (i in checkedTags.indices) {
                        stringBuilder.append(list[checkedTags[i]])
                        if (i != checkedTags.size - 1) {
                            stringBuilder.append(", ")
                        }
                    }
                    Tag.text = stringBuilder.toString()
                    searchTags = if (Tag.text != "") {
                        Tag?.text.toString().split(", ").toMutableList()
                    } else {
                        mutableListOf()
                    }

                    barList = sortDates()


                    barDataSet = BarDataSet(barList, " Total " + Tag.text.toString())
                    barData = BarData(barDataSet, barDataSet2)
                    chart.data = barData
                    chart.notifyDataSetChanged()
                    chart.invalidate()
                    barDataSet.color = resources.getColor(R.color.teal_200)
                    barDataSet2.color = resources.getColor(R.color.teal_700)
                    barData.barWidth = 0.3f
                    barDataSet.valueTextSize = 10f
                    barDataSet2.valueTextSize = 10f
                    chart.animateY(1000)
                    chart.description.text = "Total YTD: $ytd1 \t\tTotal YTD: $ytd2"
                    chart.description.textSize = 15f
                })
            builder.setNegativeButton(
                "Cancel",
                DialogInterface.OnClickListener() { dialogInterFace: DialogInterface, i: Int ->
                    dialogInterFace.dismiss()
                })
            builder.setNeutralButton(
                "Clear All",
                DialogInterface.OnClickListener() { dialog: DialogInterface?, which: Int ->

                    for (i in selectedTag.indices) {
                        selectedTag[i] = false
                        checkedTags.clear()

                    }
                    Tag.text = ""
                    searchTags = mutableListOf()
                    barList = sortDates()


                    barDataSet = BarDataSet(barList, " Total " + Tag.text.toString())
                    barData = BarData(barDataSet, barDataSet2)
                    chart.data = barData
                    chart.notifyDataSetChanged()
                    chart.invalidate()
                    barDataSet.color = resources.getColor(R.color.teal_200)
                    barDataSet2.color = resources.getColor(R.color.teal_700)
                    barData.barWidth = 0.3f
                    barDataSet.valueTextSize = 10f
                    barDataSet2.valueTextSize = 10f
                    chart.animateY(1000)
                    chart.description.text = "Total YTD: $ytd1 \t\tTotal YTD: $ytd2"
                    chart.description.textSize = 15f
                })
            builder.show()
        })





        Tag2.setOnClickListener(View.OnClickListener {

            var builder: AlertDialog.Builder = AlertDialog.Builder(this)

            builder.setTitle("Select Tag")

            builder.setCancelable(false)
            builder.setMultiChoiceItems(
                list2.toTypedArray(),
                selectedTag2,
                DialogInterface.OnMultiChoiceClickListener() { dialog: DialogInterface?, which: Int, isChecked: Boolean ->

                    if (isChecked) {
                        checkedTags2.add(which)
                    } else {
                        checkedTags2.remove(which)
                    }
                })
            builder.setPositiveButton(
                "OK",
                DialogInterface.OnClickListener() { dialogInterface: DialogInterface?, i: Int ->

                    var stringBuilder: StringBuilder = StringBuilder()

                    for (i in checkedTags2.indices) {
                        stringBuilder.append(list2[checkedTags2[i]])
                        if (i != checkedTags2.size - 1) {
                            stringBuilder.append(", ")
                        }
                    }
                    Tag2.text = stringBuilder.toString()

                    searchTags2 = if (Tag2.text != "") {
                        Tag2?.text.toString().split(", ").toMutableList()
                    } else {
                        mutableListOf()
                    }

                    barList2 = sortDates2()


                    barDataSet2 = BarDataSet(barList2, " Total " + Tag2.text.toString())
                    barData = BarData(barDataSet, barDataSet2)
                    chart.data = barData
                    chart.notifyDataSetChanged()
                    chart.invalidate()
                    barDataSet.color = resources.getColor(R.color.teal_200)
                    barDataSet2.color = resources.getColor(R.color.teal_700)
                    barData.barWidth = 0.3f
                    barDataSet.valueTextSize = 10f
                    barDataSet2.valueTextSize = 10f
                    chart.animateY(1000)
                    chart.description.text = "Total YTD: $ytd1 \t\tTotal YTD: $ytd2"
                    chart.description.textSize = 15f
                })
            builder.setNegativeButton(
                "Cancel",
                DialogInterface.OnClickListener() { dialogInterFace: DialogInterface, i: Int ->
                    dialogInterFace.dismiss()
                })
            builder.setNeutralButton(
                "Clear All",
                DialogInterface.OnClickListener() { dialog: DialogInterface?, which: Int ->

                    for (i in selectedTag2.indices) {
                        selectedTag2[i] = false
                        checkedTags2.clear()

                    }
                    Tag2.text = ""
                    searchTags2 = mutableListOf()
                    barList2 = sortDates2()


                    barDataSet2 = BarDataSet(barList2, " Total " + Tag2.text.toString())
                    barData = BarData(barDataSet, barDataSet2)
                    chart.data = barData
                    chart.notifyDataSetChanged()
                    chart.invalidate()
                    barDataSet.color = resources.getColor(R.color.teal_200)
                    barDataSet2.color = resources.getColor(R.color.teal_700)
                    barData.barWidth = 0.3f
                    barDataSet.valueTextSize = 10f
                    barDataSet2.valueTextSize = 10f
                    chart.animateY(1000)
                    chart.description.text = "Total YTD: $ytd1 \t\tTotal YTD: $ytd2"
                    chart.description.textSize = 15f
                })
            builder.show()
        })
    }


    fun searchForDoc(): ArrayList<Document> {


        var foundDocuments: ArrayList<Document> = ArrayList()

        for (i in Gallery.docList) {
            if (i.Tags.containsAll(searchTags)) {
                foundDocuments.add(i)
            }
        }
        return foundDocuments
    }

    fun searchForDoc2(): ArrayList<Document> {


        var foundDocuments: ArrayList<Document> = ArrayList()

        for (i in Gallery.docList) {
            if (i.Tags.containsAll(searchTags2)) {
                foundDocuments.add(i)
            }
        }
        return foundDocuments
    }


    fun sortDates(): ArrayList<BarEntry> {
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
        for (i in newList) {
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
                else -> {

                }
            }

        }
        barList.add(BarEntry(11f, a))
        barList.add(BarEntry(10f, b))
        barList.add(BarEntry(9f, c))
        barList.add(BarEntry(8f, d))
        barList.add(BarEntry(7f, e))
        barList.add(BarEntry(6f, f))
        barList.add(BarEntry(5f, g))
        barList.add(BarEntry(4f, h))
        barList.add(BarEntry(3f, j))
        barList.add(BarEntry(2f, k))
        barList.add(BarEntry(1f, l))
        barList.add(BarEntry(0f, m))


        for (i in newList){
            if (LocalDate.now().minusMonths(12)< LocalDate.parse(i.date, format)){
                ytd1+=i.total.toFloat()
            }
        }
        return barList
    }

    fun sortDates2(): ArrayList<BarEntry> {

        ytd2 = 0f
        barList2 = ArrayList()
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
        var newList = searchForDoc2()
        for (i in newList) {
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
                else -> {

                }
            }

        }
        barList2.add(BarEntry(11.5f, a))
        barList2.add(BarEntry(10.5f, b))
        barList2.add(BarEntry(9.5f, c))
        barList2.add(BarEntry(8.5f, d))
        barList2.add(BarEntry(7.5f, e))
        barList2.add(BarEntry(6.5f, f))
        barList2.add(BarEntry(5.5f, g))
        barList2.add(BarEntry(4.5f, h))
        barList2.add(BarEntry(3.5f, j))
        barList2.add(BarEntry(2.5f, k))
        barList2.add(BarEntry(1.5f, l))
        barList2.add(BarEntry(0.5f, m))

        for (i in newList){
            if (LocalDate.now().minusMonths(12)< LocalDate.parse(i.date, format)){
                ytd2+=i.total.toFloat()
            }
        }
        return barList2
    }

    fun toMain(view: View) {
        startActivity(Intent(this, MainActivity::class.java))
    }

    fun compare(view: View) {
        startActivity(Intent(this, TotalsScreen::class.java))
    }
}
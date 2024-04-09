package com.example.HomeAccounting

import android.content.Intent
import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import android.view.MenuItem
import android.view.View
import android.widget.ArrayAdapter
import android.widget.ListView
import android.widget.Spinner
import androidx.lifecycle.asLiveData
import com.example.HomeAccounting.databinding.ActivityHistoryBinding

class ActivityHistory : AppCompatActivity() {

    lateinit var bindingHistory: ActivityHistoryBinding
    @Override
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        bindingHistory = ActivityHistoryBinding.inflate(layoutInflater)
        setContentView(bindingHistory.root)
        supportActionBar?.setDisplayHomeAsUpEnabled(true)
        supportActionBar?.title = "История"

        val spinner: Spinner = findViewById(R.id.spinnerMonth)
        ArrayAdapter.createFromResource(
            this,
            R.array.months,
            android.R.layout.simple_spinner_item
        ).also { adapter ->
            adapter.setDropDownViewResource(android.R.layout.simple_spinner_dropdown_item)
            spinner.adapter = adapter
        }

        val spinnerVid: Spinner = findViewById(R.id.spinnerVid)
        ArrayAdapter.createFromResource(
            this,
            R.array.vid,
            android.R.layout.simple_spinner_item
        ).also { adapter ->
            adapter.setDropDownViewResource(android.R.layout.simple_spinner_dropdown_item)
            spinnerVid.adapter = adapter
        }

        val db = MainDb.getDb(this)

        val historyList = ArrayList<String>()
        val listHistory: ListView = findViewById(R.id.listHistory)
        val adapter = ArrayAdapter<String>(
            this,
            android.R.layout.simple_list_item_1,
            historyList
        )
        listHistory.adapter = adapter

        fun dBTotal() {
            db.getDao().getAllItem().asLiveData().observe(this) {
                historyList.clear()
                it.forEach {
                    historyList.add(history(it.month, it.vid, it.category, it.summa))
                }
                if(historyList.size == 0)
                    historyList.add("У вас пока что нет транзакций!")
                adapter.notifyDataSetChanged()
            }
        }
        dBTotal()

        bindingHistory.buttonSearch.setOnClickListener {
            val month = spinner.getSelectedItem().toString()
            val vid = spinnerVid.getSelectedItem().toString()

            if (month == "Выберите месяц" && vid == "Выберите вид") {
                dBTotal()
            } else {
                db.getDao().getAllItem().asLiveData().observe(this) {
                    historyList.clear()
                    if (month == "Выберите месяц" && vid != "Выберите вид") {
                        it.forEach {
                            if (it.vid == vid) {
                                historyList.add(history(it.month, it.vid, it.category, it.summa))
                            }
                        }
                        if(historyList.size == 0)
                            historyList.add("У вас пока что нет транзакций этого вида!")
                        adapter.notifyDataSetChanged()
                    } else if (month != "Выберите месяц" && vid == "Выберите вид") {
                        it.forEach {
                            if (it.month == month)
                                historyList.add(history(it.month, it.vid, it.category, it.summa))
                        }
                        if(historyList.size == 0)
                            historyList.add("В этом месяце нет транзакций!")
                        adapter.notifyDataSetChanged()
                    } else {
                        it.forEach {
                            if (it.month == month && it.vid == vid)
                                historyList.add(history(it.month, it.vid, it.category, it.summa))
                        }
                        if(historyList.size == 0)
                            historyList.add("В этом месяце нет транзакций этого вида!")
                        adapter.notifyDataSetChanged()
                    }

                }
            }
        }
    }

    fun history(month: String, vid: String, kategoriya: String, summa: String): String{
        return "${month}, ${vid}: ${kategoriya} = ${summa}\n"
    }
    override fun onOptionsItemSelected(item: MenuItem): Boolean {
        if(item.itemId == android.R.id.home)  finish()
        return true
    }

}
package com.example.HomeAccounting

import android.content.Intent
import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import android.view.MenuItem
import android.view.View
import android.widget.ArrayAdapter
import android.widget.Spinner
import com.example.HomeAccounting.databinding.ActivityMainIncomeBinding


class MainActivityIncome : AppCompatActivity() {

    lateinit var bindingIncome: ActivityMainIncomeBinding

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        bindingIncome = ActivityMainIncomeBinding.inflate(layoutInflater)
        setContentView(bindingIncome.root)
        supportActionBar?.setDisplayHomeAsUpEnabled(true)
        supportActionBar?.title = "Добавить доход"

        val db = MainDb.getDb(this)


        val spinnerCategories: Spinner = findViewById(R.id.spinnerCategories)
        ArrayAdapter.createFromResource(
            this,
            R.array.categories_income,
            android.R.layout.simple_spinner_item
        ).also { adapter ->
            adapter.setDropDownViewResource(android.R.layout.simple_spinner_dropdown_item)
            spinnerCategories.adapter = adapter
        }

        val spinner: Spinner = findViewById(R.id.spinnerMonth)
        ArrayAdapter.createFromResource(
            this,
            R.array.months,
            android.R.layout.simple_spinner_item
        ).also { adapter ->
            adapter.setDropDownViewResource(android.R.layout.simple_spinner_dropdown_item)
            spinner.adapter = adapter
        }

        bindingIncome.buttonActivityIncome.setOnClickListener {
            val summa = bindingIncome.editTextSumma.text.toString().replace("\\s".toRegex(),"")
            val category = spinnerCategories.getSelectedItem().toString()
            val month = spinner.getSelectedItem().toString()
            if(summa.equals("") || category == "Выберите категорию" || month == "Выберите месяц") {

                bindingIncome.errors1.text = "Все поля должны быть заполнены!"

            }
            else{
                    val item = Accounting(null,
                        summa,
                        "Доход",
                        category,
                        month,
                    )
                    Thread{
                        db.getDao().insertItem(item)
                    }.start()
                    finish()
            }
        }

    }
    override fun onOptionsItemSelected(item: MenuItem): Boolean {
        if(item.itemId == android.R.id.home) finish()
        return true
    }
}
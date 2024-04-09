package com.example.HomeAccounting

import android.content.Intent
import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import android.view.MenuItem
import android.view.View
import android.widget.ArrayAdapter
import android.widget.Spinner
import com.example.HomeAccounting.databinding.ActivityMainExpensesBinding

class MainActivityExpenses : AppCompatActivity() {
    lateinit var bindingExpenses: ActivityMainExpensesBinding

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        bindingExpenses = ActivityMainExpensesBinding.inflate(layoutInflater)
        setContentView(bindingExpenses.root)
        supportActionBar?.setDisplayHomeAsUpEnabled(true)
        supportActionBar?.title = "Добавить расход"

        val db = MainDb.getDb(this)

        val spinnerCategories: Spinner = findViewById(R.id.spinnerCategories)

        ArrayAdapter.createFromResource(
            this,
            R.array.categories_expenses,
            android.R.layout.simple_spinner_item
        ).also { adapter ->
            adapter.setDropDownViewResource(android.R.layout.simple_spinner_dropdown_item)
            spinnerCategories.adapter = adapter
        }

        val spinnerMonth: Spinner = findViewById(R.id.spinnerMonth)
        ArrayAdapter.createFromResource(
            this,
            R.array.months,
            android.R.layout.simple_spinner_item
        ).also { adapter ->
            adapter.setDropDownViewResource(android.R.layout.simple_spinner_dropdown_item)
            spinnerMonth.adapter = adapter
        }
       bindingExpenses.buttonActivityExpenses.setOnClickListener {

           val summa = bindingExpenses.editTextSumma1.text.toString().replace("\\s".toRegex(),"")
           val category = spinnerCategories.getSelectedItem().toString()
           val month = spinnerMonth.getSelectedItem().toString()

            if(summa.equals("") || category == "Выберите категорию" || month == "Выберите месяц") {

                bindingExpenses.errors12.text = "Все поля должны быть заполнены!"

            }
            else{
                val item = Accounting(null,
                    summa,
                    "Расход",
                    category,
                    month,
                )
                Thread{
                    db.getDao().insertItem(item)
                }.start()
                finish()
//                val intentActivityMain = Intent(this, MainActivity::class.java)
//                startActivity(intentActivityMain)

            }
       }
    }
    override fun onOptionsItemSelected(item: MenuItem): Boolean {
        if(item.itemId == android.R.id.home) finish()
        return true
    }
}
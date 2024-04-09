package com.example.HomeAccounting

import android.content.Intent
import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import android.view.MenuItem
import android.view.View
import android.widget.ArrayAdapter
import android.widget.Spinner
import androidx.lifecycle.asLiveData
import com.example.HomeAccounting.databinding.ActivityFinancialAnalysisBinding
import kotlin.math.roundToInt

class FinancialAnalysis : AppCompatActivity() {

    lateinit var bindingAnalysis: ActivityFinancialAnalysisBinding

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        bindingAnalysis = ActivityFinancialAnalysisBinding.inflate(layoutInflater)
        setContentView(bindingAnalysis.root)
        supportActionBar?.setDisplayHomeAsUpEnabled(true)
        supportActionBar?.title = "Анализ финансов"

        var expens = 0.0
        var income = 0.0
        var total = 0.0

        var expensesStatistics: Int
        var incomeStatistics: Int

        val db = MainDb.getDb(this)

        val spinner: Spinner = findViewById(R.id.spinnerMonth)
        ArrayAdapter.createFromResource(
            this,
            R.array.months,
            android.R.layout.simple_spinner_item
        ).also { adapter ->
            adapter.setDropDownViewResource(android.R.layout.simple_spinner_dropdown_item)
            spinner.adapter = adapter
        }


        fun dBTotal() {
            db.getDao().getAllItem().asLiveData().observe(this) {
                expens = 0.0
                income = 0.0
                total = 0.0
                it.forEach {
                    if (it.vid == "Доход") {
                        income += it.summa.toDouble()
                    }
                    if (it.vid == "Расход") {
                        expens += it.summa.toDouble()
                    }
                    total += it.summa.toDouble()
                }

                if(income == 0.0 && expens == 0.0 && total == 0.0) {
                    bindingAnalysis.Statistics.text = "У вас пока что нет транзакций!"
                    bindingAnalysis.Income.visibility = View.INVISIBLE
                    bindingAnalysis.Expenses.visibility = View.INVISIBLE
                    bindingAnalysis.grafIncome.visibility = View.INVISIBLE
                    bindingAnalysis.grafIncome2.visibility = View.INVISIBLE
                    bindingAnalysis.textExpense.visibility = View.INVISIBLE
                    bindingAnalysis.textIncome.visibility = View.INVISIBLE
                    bindingAnalysis.textView5.visibility = View.INVISIBLE
                    bindingAnalysis.textView16.visibility = View.INVISIBLE
                    bindingAnalysis.textView17.visibility = View.INVISIBLE
                    bindingAnalysis.textView18.visibility = View.INVISIBLE
                    bindingAnalysis.textView10.visibility = View.INVISIBLE
                    bindingAnalysis.textView11.visibility = View.INVISIBLE
                    bindingAnalysis.textView6.visibility = View.INVISIBLE
                    bindingAnalysis.textView8.visibility = View.INVISIBLE
                    bindingAnalysis.textView9.visibility = View.INVISIBLE
                    bindingAnalysis.textView12.visibility = View.INVISIBLE
                    bindingAnalysis.textView13.visibility = View.INVISIBLE
                    bindingAnalysis.textView14.visibility = View.INVISIBLE
                }
                else{
                    bindingAnalysis.Income.visibility = View.VISIBLE
                    bindingAnalysis.Expenses.visibility = View.VISIBLE
                    bindingAnalysis.grafIncome.visibility = View.VISIBLE
                    bindingAnalysis.grafIncome2.visibility = View.VISIBLE
                    bindingAnalysis.textExpense.visibility = View.VISIBLE
                    bindingAnalysis.textIncome.visibility = View.VISIBLE
                    bindingAnalysis.textView5.visibility = View.VISIBLE
                    bindingAnalysis.textView16.visibility = View.VISIBLE
                    bindingAnalysis.textView17.visibility = View.VISIBLE
                    bindingAnalysis.textView18.visibility = View.VISIBLE
                    bindingAnalysis.textView10.visibility = View.VISIBLE
                    bindingAnalysis.textView11.visibility = View.VISIBLE
                    bindingAnalysis.textView6.visibility = View.VISIBLE
                    bindingAnalysis.textView8.visibility = View.VISIBLE
                    bindingAnalysis.textView9.visibility = View.VISIBLE
                    bindingAnalysis.textView12.visibility = View.VISIBLE
                    bindingAnalysis.textView13.visibility = View.VISIBLE
                    bindingAnalysis.textView14.visibility = View.VISIBLE

                    bindingAnalysis.Statistics.text = statistics(income, expens)

                    incomeStatistics = incomeStatistics(income, total)
                    expensesStatistics = expensesStatistics(expens, total)

                    bindingAnalysis.Income.text = "${income}(${incomeStatistics}%)"
                    bindingAnalysis.Expenses.text = "${expens}(${expensesStatistics}%)"

                    bindingAnalysis.grafIncome.text = stringStatistics(expensesStatistics)
                    bindingAnalysis.grafIncome2.text = stringStatistics(incomeStatistics)
                }
            }
        }
        dBTotal()
        bindingAnalysis.buttonSerch.setOnClickListener {
            val month = spinner.getSelectedItem().toString()
            if (month == "Выберите месяц") {
                dBTotal()
            } else {
                db.getDao().getAllItem().asLiveData().observe(this) {
                    income = 0.0
                    expens = 0.0
                    total = 0.0
                    it.forEach {
                        if (it.month == month && it.vid == "Доход") {
                            income += it.summa.toDouble()
                        }
                        if (it.month == month && it.vid == "Расход") {
                            expens += it.summa.toDouble()
                        }
                        if (it.month == month) {
                            total += it.summa.toDouble()
                        }
                    }
                    if(income == 0.0 && expens == 0.0 && total == 0.0){
                        bindingAnalysis.Statistics.text = "В этом месяце нет транзакций!"
                        bindingAnalysis.Income.visibility = View.INVISIBLE
                        bindingAnalysis.Expenses.visibility = View.INVISIBLE
                        bindingAnalysis.grafIncome.visibility = View.INVISIBLE
                        bindingAnalysis.grafIncome2.visibility = View.INVISIBLE
                        bindingAnalysis.textExpense.visibility = View.INVISIBLE
                        bindingAnalysis.textIncome.visibility = View.INVISIBLE
                        bindingAnalysis.textView5.visibility = View.INVISIBLE
                        bindingAnalysis.textView16.visibility = View.INVISIBLE
                        bindingAnalysis.textView17.visibility = View.INVISIBLE
                        bindingAnalysis.textView18.visibility = View.INVISIBLE
                        bindingAnalysis.textView10.visibility = View.INVISIBLE
                        bindingAnalysis.textView11.visibility = View.INVISIBLE
                        bindingAnalysis.textView6.visibility = View.INVISIBLE
                        bindingAnalysis.textView8.visibility = View.INVISIBLE
                        bindingAnalysis.textView9.visibility = View.INVISIBLE
                        bindingAnalysis.textView12.visibility = View.INVISIBLE
                        bindingAnalysis.textView13.visibility = View.INVISIBLE
                        bindingAnalysis.textView14.visibility = View.INVISIBLE
                    }
                    else{
                        bindingAnalysis.Income.visibility = View.VISIBLE
                        bindingAnalysis.Expenses.visibility = View.VISIBLE
                        bindingAnalysis.grafIncome.visibility = View.VISIBLE
                        bindingAnalysis.grafIncome2.visibility = View.VISIBLE
                        bindingAnalysis.textExpense.visibility = View.VISIBLE
                        bindingAnalysis.textIncome.visibility = View.VISIBLE
                        bindingAnalysis.textView5.visibility = View.VISIBLE
                        bindingAnalysis.textView16.visibility = View.VISIBLE
                        bindingAnalysis.textView17.visibility = View.VISIBLE
                        bindingAnalysis.textView18.visibility = View.VISIBLE
                        bindingAnalysis.textView10.visibility = View.VISIBLE
                        bindingAnalysis.textView11.visibility = View.VISIBLE
                        bindingAnalysis.textView6.visibility = View.VISIBLE
                        bindingAnalysis.textView8.visibility = View.VISIBLE
                        bindingAnalysis.textView9.visibility = View.VISIBLE
                        bindingAnalysis.textView12.visibility = View.VISIBLE
                        bindingAnalysis.textView13.visibility = View.VISIBLE
                        bindingAnalysis.textView14.visibility = View.VISIBLE

                        bindingAnalysis.Statistics.text = statistics(income, expens)

                        incomeStatistics = incomeStatistics(income, total)
                        expensesStatistics = expensesStatistics(expens, total)

                        bindingAnalysis.Income.text = "${income}(${incomeStatistics}%)"
                        bindingAnalysis.Expenses.text = "${expens}(${expensesStatistics}%)"

                        bindingAnalysis.grafIncome.text = stringStatistics(expensesStatistics)
                        bindingAnalysis.grafIncome2.text = stringStatistics(incomeStatistics)
                    }
                }
            }
        }
    }
    fun incomeStatistics(income: Double, total: Double): Int{
        return ((income * 100)/total).roundToInt()
    }
    fun expensesStatistics(expens: Double, total: Double): Int{
        return ((expens * 100)/total).roundToInt()
    }
    fun stringStatistics(x: Int): String{
        var s = ""
        for(i in 1..x/10){
            s += "--"
        }
        if(x % 10 >= 5){
            s+= "-"
        }
        return s
    }
    fun statistics(income: Double, expenses: Double): String{
        if(income > expenses){
            return "Доходы на ${income - expenses} больше расходов."
        }
        else if(expenses > income)
            return "Расходы на ${expenses - income} больше доходов."
        else
            return "Доходы и расходы равны."
    }
    override fun onOptionsItemSelected(item: MenuItem): Boolean {
        if(item.itemId == android.R.id.home) finish()
        return true
    }
}
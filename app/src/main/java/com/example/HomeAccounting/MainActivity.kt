package com.example.HomeAccounting

import android.annotation.SuppressLint
import android.app.Dialog
import android.content.DialogInterface
import android.content.Intent
import android.os.Build
import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import android.view.Menu
import android.view.MenuItem
import android.view.View
import android.widget.TextView
import androidx.annotation.RequiresApi
import androidx.appcompat.app.AlertDialog
import androidx.fragment.app.DialogFragment
import androidx.lifecycle.asLiveData
import com.example.HomeAccounting.databinding.ActivityMainBinding
import java.lang.System.exit
import java.text.SimpleDateFormat
import java.time.LocalDate
import java.time.format.DateTimeFormatter

class MainActivity : AppCompatActivity() {

    lateinit var binding: com.example.HomeAccounting.databinding.ActivityMainBinding


    @SuppressLint("SetTextI18n")
    @RequiresApi(Build.VERSION_CODES.O)
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        binding = ActivityMainBinding.inflate(layoutInflater)
        setContentView(binding.root)
        val date = LocalDate.now().format(DateTimeFormatter.ofPattern("dd MMMM yyyy"))
        supportActionBar?.setDisplayHomeAsUpEnabled(true)
        supportActionBar?.title = "Домашняя бухгалтерия"
        binding.textViewDate.text = "Сегодня, ${date}"

        val db = MainDb.getDb(this)
        db.getDao().getAllItem().asLiveData().observe(this){
            var income = 0.0
            var expens = 0.0
            var total = 0.0

            it.forEach{
                if(it.vid == "Доход"){
                    income += it.summa.toDouble()
                }
                if(it.vid == "Расход") {
                    expens += it.summa.toDouble()
                }
            }
            binding.TextIncome.text = income.toString()
            binding.textExpenses.text = expens.toString()
            total += (income - expens)
            binding.TotalFunds.text = total.toString()


        }
    }

    fun onClickPlus(view: View) {
        val intentActivityIncome = Intent(this, MainActivityIncome::class.java)
        startActivity(intentActivityIncome)
    }
    fun onClickMinus(view: View) {
        val intentActivityExpenses = Intent(this, MainActivityExpenses::class.java)
        startActivity(intentActivityExpenses)
    }

    fun onClickAnalysis(view: View) {
        val intentActivityAnalisis= Intent(this, FinancialAnalysis::class.java)
        startActivity(intentActivityAnalisis)
    }

    fun onClickHistory(view: View) {
        val intentActivatyHistory = Intent(this, ActivityHistory::class.java)
        startActivity(intentActivatyHistory)
    }

    override fun onCreateOptionsMenu(menu: Menu?): Boolean {
        menuInflater.inflate(R.menu.menu_exit, menu)
        return true
    }
    override fun onOptionsItemSelected(item: MenuItem): Boolean {
        when(item.itemId){
            android.R.id.home -> finish()
            R.id.info -> {
                val myDialogFragment = MyDialogFragment()
                val manager = supportFragmentManager
                myDialogFragment.show(manager, "myDialog")
            }
        }
        return true
    }
    class MyDialogFragment : DialogFragment(){
        override fun onCreateDialog(savedInstanceState: Bundle?): Dialog {
            return activity?.let {
                val dialog = AlertDialog.Builder(it)
                dialog.setTitle("О приложении")
                    .setMessage("Приложение предназначено для ведения учёта и контроля личных" +
                            " финансов")
                    .setPositiveButton("Понятно") {
                            dialog, which -> dialog.cancel()
                    }
                dialog.create()
            } ?: throw IllegalStateException("Activity cannot be null")
        }
    }
}
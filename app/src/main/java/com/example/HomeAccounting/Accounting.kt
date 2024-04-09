package com.example.HomeAccounting

import androidx.room.ColumnInfo
import androidx.room.Entity
import androidx.room.PrimaryKey

@Entity(tableName = "accounting")
data class Accounting (
    @PrimaryKey(autoGenerate = true)
    var id: Int? = null,
    @ColumnInfo(name = "Сумма")
    var summa: String,
    @ColumnInfo(name = "Вид")
    var vid: String,
    @ColumnInfo(name = "Категория")
    var category: String,
    @ColumnInfo(name = "Месяц")
    var month: String,
        )


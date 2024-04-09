package com.example.HomeAccounting

import androidx.room.Insert
import androidx.room.Query
import kotlinx.coroutines.flow.Flow

@androidx.room.Dao
interface Dao {
    @Insert
    fun insertItem(item: Accounting)
    @Query("SELECT * FROM accounting ORDER BY id DESC")
    fun getAllItem(): Flow<List<Accounting>>

}
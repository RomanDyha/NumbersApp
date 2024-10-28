package com.mornhouse.numbersinfoapp.data.data_source.local

import androidx.room.Dao
import androidx.room.Insert
import androidx.room.OnConflictStrategy
import androidx.room.Query

@Dao
interface NumbersDao {

    // save numbers to database
    @Insert(onConflict = OnConflictStrategy.REPLACE)
    suspend fun insertNumbersInfo(number: NumberEntity)

    // get number details by id
    @Query("SELECT * FROM number WHERE number= :number")
    fun getNumberInfo(number: Long): NumberEntity

    // get numbers details
    @Query("SELECT * FROM number")
    fun getNumbersInfo(): List<NumberEntity>

}
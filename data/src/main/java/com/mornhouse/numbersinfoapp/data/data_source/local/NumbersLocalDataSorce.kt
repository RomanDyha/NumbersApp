package com.mornhouse.numbersinfoapp.data.data_source.local

// all database queries
interface NumbersLocalDataSorce {

    fun getNumberInfo(number: Long): NumberEntity

    fun getNumbersInfo(): List<NumberEntity>

    suspend fun saveNumbers(number: NumberEntity)

}
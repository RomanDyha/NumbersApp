package com.mornhouse.numbersinfoapp.data.data_source.local

// implementation LocalDataSource
class NumbersLocalDataSourceImpl(private val numbersDao: NumbersDao): NumbersLocalDataSorce {

    override fun getNumberInfo(number: Long): NumberEntity {
        return numbersDao.getNumberInfo(number)
    }

    override fun getNumbersInfo(): List<NumberEntity> {
        return numbersDao.getNumbersInfo()
    }

    override suspend fun saveNumbers(number: NumberEntity) {
        numbersDao.insertNumbersInfo(number)
    }

}
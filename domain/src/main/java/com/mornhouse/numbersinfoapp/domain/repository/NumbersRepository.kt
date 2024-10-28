package com.mornhouse.numbersinfoapp.domain.repository

import com.mornhouse.numbersinfoapp.domain.models.DataStatus
import com.mornhouse.numbersinfoapp.domain.models.NumberInfo
import kotlinx.coroutines.flow.Flow

// all methods for working with data sources
interface NumbersRepository {

    fun getSearchHistory(): Flow<DataStatus<List<NumberInfo>>>

    suspend fun getNumberInfo(number: Long): Flow<DataStatus<NumberInfo>>

    suspend fun getRandomNumberInfo(): Flow<DataStatus<NumberInfo>>

}
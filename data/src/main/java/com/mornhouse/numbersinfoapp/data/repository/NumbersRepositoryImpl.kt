package com.mornhouse.numbersinfoapp.data.repository

import com.mornhouse.numbersinfoapp.data.data_source.local.NumbersLocalDataSorce
import com.mornhouse.numbersinfoapp.data.data_source.local.toDomainModel
import com.mornhouse.numbersinfoapp.data.data_source.remote.NumbersRemoteDataSource
import com.mornhouse.numbersinfoapp.data.models.NumberInfoResponse
import com.mornhouse.numbersinfoapp.data.models.toDomainModel
import com.mornhouse.numbersinfoapp.data.models.toEntityModel
import com.mornhouse.numbersinfoapp.domain.models.DataStatus
import com.mornhouse.numbersinfoapp.domain.repository.NumbersRepository
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.flow.catch
import kotlinx.coroutines.flow.flow
import kotlinx.coroutines.flow.flowOn
import retrofit2.Response

// pattern Repository used to abstract the origin of data, whether it's coming from a local database, a network request, or any other source.
class NumbersRepositoryImpl(
    val numbersRemoteDataSource: NumbersRemoteDataSource,
    val numbersLocalDataSource: NumbersLocalDataSorce
) : NumbersRepository {

    override fun getSearchHistory() = flow {
        emit(DataStatus.loading())
        val cachedNumbersList = numbersLocalDataSource.getNumbersInfo()
        val numbersInfoList = DataStatus.success(data = cachedNumbersList.toDomainModel())
        emit(numbersInfoList)
    }.catch {
        emit(DataStatus.error(it.message.toString()))
    }
        .flowOn(Dispatchers.IO)

    override suspend fun getNumberInfo(number: Long) = flow {
        emit(DataStatus.loading())
        val cachedNumberInfo = numbersLocalDataSource.getNumberInfo(number)
        if (cachedNumberInfo != null) {
            val numberInfo = DataStatus.success(data = cachedNumberInfo.toDomainModel())
            emit(numberInfo)
        } else {
            val apiNumberInfo = numbersRemoteDataSource.getNumberInfo(number)
            if (responseIsSuccessful(apiNumberInfo)) {
                val numberInfo = DataStatus.success(data = apiNumberInfo.body()!!.toDomainModel())

                //save received number to the database
                numbersLocalDataSource.saveNumbers(numberInfo.data!!.toEntityModel())

                emit(numberInfo)
            } else {
                emit(DataStatus.error(apiNumberInfo.message()))
            }
        }
    }
        .catch {
            emit(DataStatus.error(it.message.toString()))
        }
        .flowOn(Dispatchers.IO)


    override suspend fun getRandomNumberInfo() = flow {
        emit(DataStatus.loading())
        val apiNumberInfo = numbersRemoteDataSource.getRandomNumberInfo()
        if (responseIsSuccessful(apiNumberInfo)) {
            val numberInfo = DataStatus.success(data = apiNumberInfo.body()!!.toDomainModel())

            //save received number to the database
            numbersLocalDataSource.saveNumbers(numberInfo.data!!.toEntityModel())

            emit(numberInfo)
        } else {
            emit(DataStatus.error(apiNumberInfo.message()))
        }
    }
        .catch {
            emit(DataStatus.error(it.message.toString()))
        }
        .flowOn(Dispatchers.IO)

    // check response
    private fun responseIsSuccessful(result: Response<NumberInfoResponse>): Boolean {
        if (result.isSuccessful && result.body() != null) {
            return true
        }
        return false
    }

}
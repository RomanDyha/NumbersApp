package com.mornhouse.numbersinfoapp.data.data_source.remote

import com.mornhouse.numbersinfoapp.data.models.NumberInfoResponse
import retrofit2.Response
import java.math.BigInteger

// implementation RemoteDataSource
class NumbersRemoteDataSourceImpl(private val numbersApiService: NumbersApiService): NumbersRemoteDataSource {

    override suspend fun getNumberInfo(number: Long): Response<NumberInfoResponse>  {
        return numbersApiService.getNumberFact(number)
    }

    override suspend fun getRandomNumberInfo(): Response<NumberInfoResponse>{
        return numbersApiService.getRandomNumberFact()
    }

}
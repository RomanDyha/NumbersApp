package com.mornhouse.numbersinfoapp.data.data_source.remote

import com.mornhouse.numbersinfoapp.data.models.NumberInfoResponse
import retrofit2.Response

// all requests to the backend
interface NumbersRemoteDataSource {

    suspend fun getNumberInfo(number: Long): Response<NumberInfoResponse>

    suspend fun getRandomNumberInfo(): Response<NumberInfoResponse>

}
package com.mornhouse.numbersinfoapp.data.data_source.remote

import com.mornhouse.numbersinfoapp.data.models.NumberInfoResponse
import retrofit2.Response
import retrofit2.http.GET
import retrofit2.http.Headers
import retrofit2.http.Path

interface NumbersApiService {

    //http://numbersapi.com/number
    @Headers("Content-Type: application/json")
    @GET("{number}")
    suspend fun getNumberFact(@Path("number") number: Long): Response<NumberInfoResponse>

    //http://numbersapi.com/random/math
    @Headers("Content-Type: application/json")
    @GET("random/math")
    suspend fun getRandomNumberFact(): Response<NumberInfoResponse>

}


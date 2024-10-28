package com.mornhouse.numbersinfoapp.data.di

import com.mornhouse.numbersinfoapp.data.BuildConfig
import com.mornhouse.numbersinfoapp.data.data_source.remote.NumbersApiService
import okhttp3.OkHttpClient
import org.koin.dsl.module
import retrofit2.Retrofit
import retrofit2.converter.gson.GsonConverterFactory


// DI retrofit 2
val networkModule = module {

    single {
        Retrofit.Builder()
            .baseUrl(BuildConfig.BASE_URL)
            .addConverterFactory(GsonConverterFactory.create())
            .client(OkHttpClient.Builder().build())
            .build()
    }

    single { get<Retrofit>().create(NumbersApiService::class.java) }

}
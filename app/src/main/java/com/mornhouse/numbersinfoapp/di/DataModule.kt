package com.mornhouse.numbersinfoapp.di

import com.mornhouse.numbersinfoapp.data.data_source.local.NumbersLocalDataSorce
import com.mornhouse.numbersinfoapp.data.data_source.local.NumbersLocalDataSourceImpl
import com.mornhouse.numbersinfoapp.data.data_source.remote.NumbersRemoteDataSource
import com.mornhouse.numbersinfoapp.data.data_source.remote.NumbersRemoteDataSourceImpl
import com.mornhouse.numbersinfoapp.data.repository.NumbersRepositoryImpl
import com.mornhouse.numbersinfoapp.domain.repository.NumbersRepository
import org.koin.dsl.module

val dataModule = module {

    single<NumbersRemoteDataSource> {
        NumbersRemoteDataSourceImpl(numbersApiService = get())
    }

    single<NumbersLocalDataSorce> {
        NumbersLocalDataSourceImpl(numbersDao = get())
    }

    single<NumbersRepository> {
        NumbersRepositoryImpl(numbersRemoteDataSource = get(), numbersLocalDataSource = get())
    }
}
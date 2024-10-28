package com.mornhouse.numbersinfoapp.data.di

import androidx.room.Room
import com.mornhouse.numbersinfoapp.data.data_source.local.AppDatabase
import org.koin.android.ext.koin.androidContext
import org.koin.dsl.module

// DI room database
val databaseModule = module {

    single {
        Room.databaseBuilder(
            androidContext(),
            AppDatabase::class.java,
            "numbers-database"
        ).build()
    }

    single { get<AppDatabase>().numbersDao() }

}
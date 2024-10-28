package com.mornhouse.numbersinfoapp.app

import android.app.Application
import com.mornhouse.numbersinfoapp.data.di.databaseModule
import com.mornhouse.numbersinfoapp.data.di.networkModule
import com.mornhouse.numbersinfoapp.di.appModule
import com.mornhouse.numbersinfoapp.di.dataModule
import com.mornhouse.numbersinfoapp.di.domainModule
import org.koin.android.ext.koin.androidContext
import org.koin.android.ext.koin.androidLogger
import org.koin.core.context.startKoin
import org.koin.core.logger.Level

class NumbersInfoApplication: Application() {

    override fun onCreate() {
        super.onCreate()

        // included modules
        startKoin {
            androidLogger(Level.ERROR)
            androidContext(this@NumbersInfoApplication)
            modules(listOf(appModule, domainModule, dataModule, networkModule, databaseModule))
        }

    }
}
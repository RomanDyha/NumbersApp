package com.mornhouse.numbersinfoapp.di

import com.mornhouse.numbersinfoapp.domain.use_cases.GetNumberInfoUseCase
import com.mornhouse.numbersinfoapp.domain.use_cases.GetRandomNumberInfoUseCase
import com.mornhouse.numbersinfoapp.domain.use_cases.GetSearchHistoryUseCase
import org.koin.dsl.module

val domainModule = module {

    factory {
        GetNumberInfoUseCase(numbersRepository = get())
    }

    factory {
        GetSearchHistoryUseCase(numbersRepository = get())
    }

    factory {
        GetRandomNumberInfoUseCase(numbersRepository = get())
    }

}
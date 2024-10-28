package com.mornhouse.numbersinfoapp.di

import com.mornhouse.numbersinfoapp.presentation.details.NumberDetailsViewModel
import com.mornhouse.numbersinfoapp.presentation.search.SearchNumberInfoViewModel
import org.koin.androidx.viewmodel.dsl.viewModel
import org.koin.dsl.module

val appModule = module {

    viewModel {
        SearchNumberInfoViewModel(
            getSearchHistoryUseCase = get(),
            getNumberInfoUseCase = get(),
            getRandomNumberInfoUseCase = get()
        )
    }

    viewModel { (number: Long) ->
        NumberDetailsViewModel(
            getNumberInfoUseCase = get(),
            number = number
        )
    }

}
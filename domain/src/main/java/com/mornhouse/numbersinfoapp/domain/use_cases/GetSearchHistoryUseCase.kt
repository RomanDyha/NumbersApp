package com.mornhouse.numbersinfoapp.domain.use_cases

import com.mornhouse.numbersinfoapp.domain.models.DataStatus
import com.mornhouse.numbersinfoapp.domain.models.NumberInfo
import com.mornhouse.numbersinfoapp.domain.repository.NumbersRepository
import kotlinx.coroutines.flow.Flow

class GetSearchHistoryUseCase(val numbersRepository: NumbersRepository) {

     fun invoke(): Flow<DataStatus<List<NumberInfo>>> =
        numbersRepository.getSearchHistory()

}
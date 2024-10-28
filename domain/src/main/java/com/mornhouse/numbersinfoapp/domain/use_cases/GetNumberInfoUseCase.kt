package com.mornhouse.numbersinfoapp.domain.use_cases

import com.mornhouse.numbersinfoapp.domain.models.DataStatus
import com.mornhouse.numbersinfoapp.domain.models.NumberInfo
import com.mornhouse.numbersinfoapp.domain.repository.NumbersRepository
import kotlinx.coroutines.flow.Flow
import java.math.BigInteger

class GetNumberInfoUseCase(val numbersRepository: NumbersRepository) {

     suspend fun invoke(number: Long): Flow<DataStatus<NumberInfo>> =
        numbersRepository.getNumberInfo(number)

}
package com.mornhouse.numbersinfoapp.presentation.details

import androidx.lifecycle.LiveData
import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.mornhouse.numbersinfoapp.domain.models.DataStatus
import com.mornhouse.numbersinfoapp.domain.models.NumberInfo
import com.mornhouse.numbersinfoapp.domain.use_cases.GetNumberInfoUseCase
import kotlinx.coroutines.launch

class NumberDetailsViewModel(
    private val getNumberInfoUseCase: GetNumberInfoUseCase,
    var number: Long
): ViewModel() {

    private val _numberInfo = MutableLiveData<DataStatus<NumberInfo>>()
    val numberInfo: LiveData<DataStatus<NumberInfo>>
        get() = _numberInfo

    init {
       searchNumberInfo()
    }

    fun searchNumberInfo() {
        viewModelScope.launch {
            getNumberInfoUseCase.invoke(number = number).collect {
                _numberInfo.value = it
            }
        }
    }

}
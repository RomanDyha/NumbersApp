package com.mornhouse.numbersinfoapp.presentation.search

import androidx.lifecycle.LiveData
import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.mornhouse.numbersinfoapp.domain.models.DataStatus
import com.mornhouse.numbersinfoapp.domain.models.NumberInfo
import com.mornhouse.numbersinfoapp.domain.use_cases.GetNumberInfoUseCase
import com.mornhouse.numbersinfoapp.domain.use_cases.GetRandomNumberInfoUseCase
import com.mornhouse.numbersinfoapp.domain.use_cases.GetSearchHistoryUseCase
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.launch

class SearchNumberInfoViewModel(
    private val getSearchHistoryUseCase: GetSearchHistoryUseCase,
    private val getNumberInfoUseCase: GetNumberInfoUseCase,
    private val getRandomNumberInfoUseCase: GetRandomNumberInfoUseCase
) : ViewModel() {

    private val _numberInfo = MutableLiveData<DataStatus<NumberInfo>>()
    val numberInfo: LiveData<DataStatus<NumberInfo>>
        get() = _numberInfo

    private val _numbersInfoList = MutableLiveData<DataStatus<List<NumberInfo>>>()
    val numbersInfoList: LiveData<DataStatus<List<NumberInfo>>>
        get() = _numbersInfoList

    init {
        getSearchHistory()
    }

    fun searchNumberInfo(number: Long) {
        viewModelScope.launch {
            getNumberInfoUseCase.invoke(number = number).collect {
                _numberInfo.value = it
            }
        }
    }

    fun searchRandomNumberInfo() {
        viewModelScope.launch {
            getRandomNumberInfoUseCase.invoke().collect {
                _numberInfo.value = it
            }
        }
    }

    fun getSearchHistory() {
        viewModelScope.launch {
            getSearchHistoryUseCase.invoke().collect {
                _numbersInfoList.value = it
            }
        }
    }

}
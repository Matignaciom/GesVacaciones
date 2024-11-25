package com.iplacex.gesvacacionesapp.viewmodel

import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.flow.StateFlow
import kotlinx.coroutines.flow.catch
import kotlinx.coroutines.launch
import com.iplacex.gesvacacionesapp.api.ExchangeRateClient
import com.iplacex.gesvacacionesapp.api.ExchangeRateResponse
import kotlinx.coroutines.flow.flow

class ExchangeRateViewModel : ViewModel() {

    private val _exchangeRate = MutableStateFlow<ExchangeRateResponse?>(null)
    val exchangeRate: StateFlow<ExchangeRateResponse?> = _exchangeRate

    init {
        viewModelScope.launch {
            getExchangeRate()
        }
    }

    private suspend fun getExchangeRate() {
        flow {
            val response = ExchangeRateClient.exchangeRateService.getExchangeRate()
            if (response.isSuccessful) {
                emit(response.body())
            } else {
                emit(null)
            }
        }
            .catch { e ->
                _exchangeRate.value = null
            }
            .collect { data ->
                _exchangeRate.value = data
            }
    }
}

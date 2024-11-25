package com.iplacex.gesvacacionesapp.api

data class ExchangeRateResponse(
    val version: String,
    val autor: String,
    val fecha: String,
    val uf: CurrencyDetail,
    val ivp: CurrencyDetail,
    val dolar: CurrencyDetail,
    val dolar_intercambio: CurrencyDetail,
    val euro: CurrencyDetail,
    val ipc: CurrencyDetail,
    val utm: CurrencyDetail,
    val imacec: CurrencyDetail,
    val tpm: CurrencyDetail,
    val libra_cobre: CurrencyDetail,
    val tasa_desempleo: CurrencyDetail,
    val bitcoin: CurrencyDetail
)

package com.iplacex.gesvacacionesapp.api

import retrofit2.Retrofit
import retrofit2.converter.gson.GsonConverterFactory

object ExchangeRateClient {
    val exchangeRateService: ExchangeRateService by lazy {
        Retrofit.Builder()
            .baseUrl("https://mindicador.cl/")
            .addConverterFactory(GsonConverterFactory.create())
            .build()
            .create(ExchangeRateService::class.java)
    }
}

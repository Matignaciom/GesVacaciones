package com.iplacex.gesvacacionesapp.api

import retrofit2.Response
import retrofit2.http.GET

interface ExchangeRateService {
    @GET("api")
    suspend fun getExchangeRate(): Response<ExchangeRateResponse>
}

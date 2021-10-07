package com.chethan.currencycalculator.api

import androidx.lifecycle.LiveData
import com.chethan.currencycalculator.constants.Constants.ACCESS_KEY
import com.chethan.currencycalculator.model.CurrencyResponse
import com.chethan.currencycalculator.model.ExchangeResponse
import retrofit2.http.GET
import retrofit2.http.Query

interface NetWorkApi {
    @GET("list")
    fun getCurrentList(
        @Query("access_key") appId: String = ACCESS_KEY
    ): LiveData<ApiResponse<CurrencyResponse>>

    @GET("live")
    fun getExchangeRate(
        @Query("access_key") appId: String = ACCESS_KEY,
        @Query("source") source: String,
        @Query("currencies") currencies: String
    ): LiveData<ApiResponse<ExchangeResponse>>
}

package com.chethan.currencycalculator.model

import com.google.gson.annotations.SerializedName

data class CurrencyResponse(
    @SerializedName("success") val success: Boolean,
    @SerializedName("terms") val terms: String,
    @SerializedName("privacy") val privacy: String,
    @SerializedName("currencies") val currencies: Map<String, String>
)

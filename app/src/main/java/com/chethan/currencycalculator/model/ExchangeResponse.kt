package com.chethan.currencycalculator.model

import com.google.gson.annotations.SerializedName

class ExchangeResponse(
    @SerializedName("success") val success: Boolean,
    @SerializedName("terms") val terms: String,
    @SerializedName("privacy") val privacy: String,
    @SerializedName("timestamp") val timestamp: String,
    @SerializedName("source") val source: String,
    @SerializedName("quotes") val quotes: Map<String, String>,
)

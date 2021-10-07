package com.chethan.currencycalculator.database.entity

import androidx.room.Entity
import com.google.gson.annotations.SerializedName
import java.io.Serializable

@Entity(primaryKeys = ["sourceToCountryCode"])
data class UserExchangeRate(
    @field:SerializedName("sourceToCountryCode")
    val sourceToCountryCode: String,
    @field:SerializedName("exchangeRate")
    val exchangeRate: String
) : Serializable

package com.chethan.currencycalculator.database.entity

import androidx.room.Entity
import com.google.gson.annotations.SerializedName
import java.io.Serializable

@Entity(primaryKeys = ["countryCode"])
data class UserCurrencies(
    @field:SerializedName("countryCode")
    val countryCode: String,
    @field:SerializedName("countryName")
    val countryName: String
) : Serializable

package com.chethan.currencycalculator.database

import androidx.room.Database
import androidx.room.RoomDatabase
import com.chethan.currencycalculator.database.dao.CurrencyDao
import com.chethan.currencycalculator.database.dao.ExchangeRateDao
import com.chethan.currencycalculator.database.entity.UserCurrencies
import com.chethan.currencycalculator.database.entity.UserExchangeRate

@Database(
    entities = [
        UserCurrencies::class,
        UserExchangeRate::class
    ],
    version = 1,
    exportSchema = false
)

abstract class AppDatabase : RoomDatabase() {
    abstract fun currencyDao(): CurrencyDao
    abstract fun exchangeRateDaoDao(): ExchangeRateDao
}

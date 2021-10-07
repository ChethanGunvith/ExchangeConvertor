package com.chethan.currencycalculator.database.dao

import androidx.lifecycle.LiveData
import androidx.room.Dao
import androidx.room.Insert
import androidx.room.OnConflictStrategy
import androidx.room.Query
import com.chethan.currencycalculator.database.entity.UserExchangeRate
import com.chethan.currencycalculator.testing.OpenForTesting

@Dao
@OpenForTesting
abstract class ExchangeRateDao {

    @Insert(onConflict = OnConflictStrategy.REPLACE)
    abstract fun insertExchangeRates(list: List<UserExchangeRate>)

    @Query("SELECT * FROM UserExchangeRate WHERE sourceToCountryCode LIKE :search_query || '%'")
    abstract fun loadExchangeRates(search_query: String): LiveData<List<UserExchangeRate>>
}

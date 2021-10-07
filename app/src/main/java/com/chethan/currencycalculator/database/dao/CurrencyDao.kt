package com.chethan.currencycalculator.database.dao

import androidx.lifecycle.LiveData
import androidx.room.Dao
import androidx.room.Insert
import androidx.room.OnConflictStrategy
import androidx.room.Query
import com.chethan.currencycalculator.database.entity.UserCurrencies
import com.chethan.currencycalculator.testing.OpenForTesting

@Dao
@OpenForTesting
abstract class CurrencyDao {

    @Insert(onConflict = OnConflictStrategy.REPLACE)
    abstract fun insertCurrencies(list: List<UserCurrencies>)

    @Query("SELECT * FROM UserCurrencies")
    abstract fun loadCurrencies(): LiveData<List<UserCurrencies>>
}

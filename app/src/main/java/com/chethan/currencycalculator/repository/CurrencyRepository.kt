package com.chethan.currencycalculator.repository

import androidx.lifecycle.LiveData
import com.chethan.currencycalculator.api.ApiSuccessResponse
import com.chethan.currencycalculator.api.NetWorkApi
import com.chethan.currencycalculator.database.dao.CurrencyDao
import com.chethan.currencycalculator.database.dao.ExchangeRateDao
import com.chethan.currencycalculator.database.entity.UserCurrencies
import com.chethan.currencycalculator.database.entity.UserExchangeRate
import com.chethan.currencycalculator.model.CurrencyResponse
import com.chethan.currencycalculator.model.ExchangeResponse
import com.chethan.currencycalculator.testing.OpenForTesting
import com.chethan.currencycalculator.utils.RateLimiter
import java.util.concurrent.TimeUnit

@OpenForTesting
class CurrencyRepository constructor(
    private val apiServices: NetWorkApi,
    private val currencyDao: CurrencyDao,
    private val exchangeRateDao: ExchangeRateDao
) {

    // 30 minutes restriction
    private val repoListRateLimit = RateLimiter<String>(30, TimeUnit.MINUTES)

    fun getAvailableCurrency(): LiveData<Resource<List<UserCurrencies>>> {
        return object : NetworkBoundResource<List<UserCurrencies>, CurrencyResponse>() {

            // inserting into data base
            override fun saveCallResult(item: CurrencyResponse) {
                val listOfUserCurrencies = mutableListOf<UserCurrencies>()
                item.currencies.map {
                    listOfUserCurrencies.add(UserCurrencies(it.key, it.value))
                }
                currencyDao.insertCurrencies(listOfUserCurrencies)
            }

            override fun shouldFetch(data: List<UserCurrencies>?) = true

            override fun loadFromDb(): LiveData<List<UserCurrencies>> {
                return currencyDao.loadCurrencies()
            }

            override fun createCall() = apiServices.getCurrentList()

            override fun processResponse(response: ApiSuccessResponse<CurrencyResponse>):
                CurrencyResponse {
                    return response.body
                }
        }.asLiveData()
    }

    fun getExchangeRate(
        listOfCurrencies: List<String>,
        source: String
    ): LiveData<Resource<List<UserExchangeRate>>> {
        return object : NetworkBoundResource<List<UserExchangeRate>, ExchangeResponse>() {
            // inserting into data base
            override fun saveCallResult(item: ExchangeResponse) {
                val listOfUserExchangeRate = mutableListOf<UserExchangeRate>()
                if (item.quotes.isNullOrEmpty().not()) {
                    item.quotes.map {
                        listOfUserExchangeRate.add(
                            UserExchangeRate(
                                it.key,
                                it.value
                            )
                        )
                    }
                    exchangeRateDao.insertExchangeRates(listOfUserExchangeRate)
                }
            }

            override fun shouldFetch(data: List<UserExchangeRate>?) =
                repoListRateLimit.shouldFetch(source)

            override fun loadFromDb(): LiveData<List<UserExchangeRate>> {
                return exchangeRateDao.loadExchangeRates(source)
            }

            override fun createCall() =
                apiServices.getExchangeRate(
                    source = source.trim(),
                    currencies = listOfCurrencies.toString().removePrefix("[").removeSuffix("]")
                )

            override fun processResponse(response: ApiSuccessResponse<ExchangeResponse>):
                ExchangeResponse {
                    return response.body
                }
        }.asLiveData()
    }
}

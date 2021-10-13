package com.chethan.currencycalculator.view

import androidx.lifecycle.*
import com.chethan.currencycalculator.database.entity.UserCurrencies
import com.chethan.currencycalculator.database.entity.UserExchangeRate
import com.chethan.currencycalculator.repository.CurrencyRepository
import com.chethan.currencycalculator.repository.Resource
import com.chethan.currencycalculator.repository.Status
import com.chethan.currencycalculator.testing.OpenForTesting
import dagger.hilt.android.lifecycle.HiltViewModel
import javax.inject.Inject

@OpenForTesting
@HiltViewModel
class HomeScreenViewModel @Inject constructor(currencyRepository: CurrencyRepository) :
    ViewModel() {

    private val _userSelectedCurrency = MutableLiveData<String>()
    private val _availableCountryCode = MutableLiveData<List<String>>()
    private val _userPrice = MutableLiveData<Double>()

    fun setUserSelectedCountry(country: String) {
        _userSelectedCurrency.value = country
    }
    fun setUserPrice(price: String) {
        _userPrice.value = price.toDoubleOrNull()
    }

    val listOfUserCurrencies: LiveData<Resource<List<UserCurrencies>>> =
        currencyRepository.getAvailableCurrency().map { response ->
            when (response.status) {
                Status.SUCCESS -> {
                    _availableCountryCode.value = response.data?.map { item -> item.countryCode.trim() }
                    Resource.success(response.data)
                }

                Status.ERROR -> {
                    Resource.error("", response.data)
                }

                Status.LOADING -> {
                    Resource.loading(response.data)
                }
            }
        }

    val exchangeRatesFromRemote = _userSelectedCurrency.switchMap { currencyCode ->
        currencyRepository.getExchangeRate(
            source = currencyCode,
            listOfCurrencies = _availableCountryCode.value ?: emptyList()
        )
    }

    val exchangeRates = MediatorLiveData<Resource<List<UserExchangeRate>>>()
    init {
        exchangeRates.addSource(_userPrice) { price ->
            val list = extractUiDataOnExchangeRates(
                price ?: 1.0,
                exchangeRatesFromRemote.value?.data ?: emptyList()
            )
            exchangeRates.value = Resource.success(list)
        }

        exchangeRates.addSource(exchangeRatesFromRemote) {
            val list = extractUiDataOnExchangeRates(
                _userPrice.value ?: 1.0,
                exchangeRatesFromRemote.value?.data ?: emptyList()
            )
            exchangeRates.value = Resource.success(list)
        }
    }

    private fun extractUiDataOnExchangeRates(
        userPrice: Double,
        list: List<UserExchangeRate>
    ): List<UserExchangeRate> {
        val newList = mutableListOf<UserExchangeRate>()
        list.forEach {
            val newValue = it.exchangeRate.toDouble() * userPrice
            newList.add(UserExchangeRate(it.sourceToCountryCode, newValue.toString()))
        }
        return newList
    }
}

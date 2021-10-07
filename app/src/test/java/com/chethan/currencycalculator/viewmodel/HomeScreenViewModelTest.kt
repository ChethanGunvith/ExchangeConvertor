package com.chethan.currencycalculator.viewmodel

import androidx.arch.core.executor.testing.InstantTaskExecutorRule
import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.Observer
import com.chethan.currencycalculator.BaseViewModelTest
import com.chethan.currencycalculator.TestUtil
import com.chethan.currencycalculator.api.ApiResponse
import com.chethan.currencycalculator.api.NetWorkApi
import com.chethan.currencycalculator.constants.Constants.ACCESS_KEY
import com.chethan.currencycalculator.database.dao.CurrencyDao
import com.chethan.currencycalculator.database.dao.ExchangeRateDao
import com.chethan.currencycalculator.database.entity.UserCurrencies
import com.chethan.currencycalculator.database.entity.UserExchangeRate
import com.chethan.currencycalculator.model.ExchangeResponse
import com.chethan.currencycalculator.repository.CurrencyRepository
import com.chethan.currencycalculator.repository.Resource
import com.chethan.currencycalculator.utils.AbsentLiveData
import com.chethan.currencycalculator.view.HomeScreenViewModel
import com.nhaarman.mockitokotlin2.mock
import com.nhaarman.mockitokotlin2.verify
import com.squareup.moshi.JsonAdapter
import kotlinx.coroutines.ExperimentalCoroutinesApi
import org.hamcrest.CoreMatchers
import org.hamcrest.MatcherAssert
import org.junit.Before
import org.junit.Rule
import org.junit.Test
import org.junit.runner.RunWith
import org.junit.runners.JUnit4
import org.mockito.Mockito.*
import retrofit2.Response

@RunWith(JUnit4::class)
class HomeScreenViewModelTest : BaseViewModelTest() {

    @Rule
    @JvmField
    val instantExecutor = InstantTaskExecutorRule()

    private lateinit var viewModel: HomeScreenViewModel
    private lateinit var jsonAdapter: JsonAdapter<ExchangeResponse>
    private lateinit var repository: CurrencyRepository
    private val currencyDao = mock(CurrencyDao::class.java)
    private val exchangeRateDao = mock(ExchangeRateDao::class.java)
    private val service = mock(NetWorkApi::class.java)

    @Before
    fun init() {
        // mock the call
        val dbData = MutableLiveData<List<UserCurrencies>>()
        `when`(currencyDao.loadCurrencies()).thenReturn(dbData)
        repository = CurrencyRepository(service, currencyDao, exchangeRateDao)
        viewModel = HomeScreenViewModel(repository)
        jsonAdapter = moshi.adapter(ExchangeResponse::class.java)
    }

    @Test
    fun testNotNull() {
        MatcherAssert.assertThat(viewModel.listOfUserCurrencies, CoreMatchers.notNullValue())
    }

    @ExperimentalCoroutinesApi
    @Test
    fun load_fromServer() {
        val resultJson = TestUtil.loadJsonFile("currencyList.json")
        val apiResponse = jsonAdapter.fromJson(resultJson)

        val repositories = MutableLiveData<List<UserExchangeRate>>()
        val dbSearchResult = MutableLiveData<List<UserExchangeRate>>()

        val listOfUserCurrencies = mutableListOf<UserExchangeRate>()
        apiResponse?.quotes?.map {
            listOfUserCurrencies.add(UserExchangeRate(it.key, it.value))
        }

        val result = MutableLiveData<Resource<List<UserExchangeRate>>>()
        `when`(repository.getExchangeRate(emptyList(), "USD")).thenReturn(result)

        val callLiveData = MutableLiveData<ApiResponse<ExchangeResponse>>()
        `when`(service.getExchangeRate(ACCESS_KEY, "USD", "")).thenReturn(callLiveData)

        // Set observer
        val observer = mock<Observer<Resource<List<UserExchangeRate>>>>()
        viewModel.exchangeRatesFromRemote.observeForever(observer)

        viewModel.setUserSelectedCountry("USD")
        verify(observer).onChanged(Resource.loading(null))
        verifyNoMoreInteractions(service)
        // reset(observer)

        `when`(exchangeRateDao.loadExchangeRates("USD")).thenReturn(repositories)
        dbSearchResult.postValue(null)
        verify(exchangeRateDao).loadExchangeRates("USD")

        callLiveData.postValue(ApiResponse.create(Response.success(apiResponse)))
        repositories.postValue(listOfUserCurrencies)
        verifyNoMoreInteractions(service)
    }

    @Test
    fun server_error() {
        `when`(exchangeRateDao.loadExchangeRates("USD")).thenReturn(AbsentLiveData.create())
        val apiResponse = MutableLiveData<ApiResponse<ExchangeResponse>>()
        `when`(service.getExchangeRate(ACCESS_KEY, "USD", "")).thenReturn(apiResponse)

        val observer = mock<Observer<Resource<List<UserExchangeRate>>>>()
        repository.getExchangeRate(emptyList(), "USD").observeForever(observer)
        verify(observer).onChanged(Resource.loading(null))

        apiResponse.postValue(ApiResponse.create(Exception("idk")))
        verify(observer).onChanged(Resource.error("idk", null))
    }
}

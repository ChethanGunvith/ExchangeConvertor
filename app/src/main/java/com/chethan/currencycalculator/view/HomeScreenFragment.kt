package com.chethan.currencycalculator.view

import android.os.Bundle
import android.os.Handler
import android.os.Looper
import android.text.Editable
import android.text.TextWatcher
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.ArrayAdapter
import androidx.fragment.app.Fragment
import androidx.fragment.app.viewModels
import com.chethan.currencycalculator.R
import com.chethan.currencycalculator.database.entity.UserCurrencies
import com.chethan.currencycalculator.database.entity.UserExchangeRate
import com.chethan.currencycalculator.databinding.FragmentHomeBinding
import com.chethan.currencycalculator.repository.Resource
import com.chethan.currencycalculator.repository.Status
import com.chethan.currencycalculator.utils.UtilUIComponents
import com.example.tipjar.utils.AppExecutors
import dagger.hilt.android.AndroidEntryPoint
import javax.inject.Inject

@AndroidEntryPoint
class HomeScreenFragment : Fragment() {
    private lateinit var binding: FragmentHomeBinding
    private val homeScreenViewModel: HomeScreenViewModel by viewModels()
    private val adapterData = mutableListOf<UserExchangeRate>()
    private lateinit var exchangeRateAdapter: ExchangeRateAdapter

    @Inject
    lateinit var appExecutors: AppExecutors

    override fun onCreateView(
        inflater: LayoutInflater,
        container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View {
        binding = FragmentHomeBinding.inflate(inflater, container, false)
        return binding.root
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)

        val exchangeRateAdapter = ExchangeRateAdapter(appExecutors) {}
        binding.exchangeRateList.adapter = exchangeRateAdapter
        exchangeRateAdapter.submitList(adapterData)

        homeScreenViewModel.listOfUserCurrencies.observe(viewLifecycleOwner) { response ->
            when (response.status) {
                Status.SUCCESS -> {
                    setListOfCurrencyAdapter(response)
                }

                Status.LOADING -> {
                }

                Status.ERROR -> {
                    if (response.data.isNullOrEmpty().not())
                        setListOfCurrencyAdapter(response)
                }
            }
        }

        homeScreenViewModel.exchangeRates.observe(viewLifecycleOwner) { response ->
            when (response.status) {
                Status.SUCCESS -> {
                    if (response.data.isNullOrEmpty().not()) {
                        binding.exchangeRateList.visibility = View.VISIBLE
                        binding.networkError.visibility = View.GONE
                        response.data?.let { list ->
                            adapterData.clear()
                            adapterData.addAll(list)
                            exchangeRateAdapter.notifyDataSetChanged()
                        }
                    } else {
                        binding.exchangeRateList.visibility = View.GONE
                        binding.networkError.visibility = View.VISIBLE
                    }
                }

                Status.LOADING -> {
                }

                Status.ERROR -> {
                }
            }
        }

        binding.enterAmountEditText.addTextChangedListener(object : TextWatcher {
            override fun beforeTextChanged(s: CharSequence?, start: Int, count: Int, after: Int) {
            }

            override fun onTextChanged(s: CharSequence?, start: Int, before: Int, count: Int) {
            }

            override fun afterTextChanged(userText: Editable?) {
                val userPrice =
                    if (userText.isNullOrEmpty().not()) userText.toString() else 1.toString()
                homeScreenViewModel.setUserPrice(userPrice)
            }
        })
    }

    private fun setListOfCurrencyAdapter(response: Resource<List<UserCurrencies>>) {

        response.data?.let { list ->
            val currencyList = list.map { it.countryCode + " : " + it.countryName }
            val defaultCountrySelection = list.indexOfFirst { it.countryCode == "USD" }
            val arrayAdapter =
                ArrayAdapter(
                    requireContext(),
                    R.layout.adapter_user_currency_item,
                    R.id.textView,
                    currencyList
                )
            binding.autoCompleteTextView.setAdapter(arrayAdapter)
            binding.autoCompleteTextView.setOnItemClickListener { _, _, position, _ ->
                homeScreenViewModel.setUserSelectedCountry(list[position].countryCode)
            }

            binding.autoCompleteTextView.setOnClickListener {
                UtilUIComponents.hideKeyBoard(requireActivity())
            }

            // Default Currency
            binding.autoCompleteTextView.setText(
                arrayAdapter.getItem(
                    defaultCountrySelection
                ),
                false
            )

            // We calling exchanges rate with 1 second delay since API is limited rate access,
            // You can't call two api or same api within 2 second for the host - http://api.currencylayer.com/
            // Retrieved Data will be persisted locally in db,
            // and the next call for the same source will be called after 30 minutes,
            // please check repository class of CurrencyRepository for 30 minutes restriction
            Handler(Looper.getMainLooper()).postDelayed(
                {
                    homeScreenViewModel.setUserSelectedCountry(list[defaultCountrySelection].countryCode)
                },
                2000
            )
        }
    }
}

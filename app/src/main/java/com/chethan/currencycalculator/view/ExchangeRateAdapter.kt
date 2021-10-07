package com.chethan.currencycalculator.view

import android.view.LayoutInflater
import android.view.ViewGroup
import androidx.recyclerview.widget.DiffUtil
import com.chethan.currencycalculator.common.DataBoundListAdapter
import com.chethan.currencycalculator.database.entity.UserExchangeRate
import com.chethan.currencycalculator.databinding.AdapterExchangeRateItemBinding
import com.example.tipjar.utils.AppExecutors

class ExchangeRateAdapter(
    appExecutors: AppExecutors,
    private val clickListener: ((UserExchangeRate) -> Unit)
) :
    DataBoundListAdapter<UserExchangeRate, AdapterExchangeRateItemBinding>(
        appExecutors,
        diffCallback = object : DiffUtil.ItemCallback<UserExchangeRate>() {
            override fun areItemsTheSame(
                oldItem: UserExchangeRate,
                newItem: UserExchangeRate
            ) = oldItem == newItem

            override fun areContentsTheSame(
                oldItem: UserExchangeRate,
                newItem: UserExchangeRate
            ) = oldItem == newItem
        }
    ) {

    override fun createBinding(parent: ViewGroup): AdapterExchangeRateItemBinding =
        AdapterExchangeRateItemBinding.inflate(
            LayoutInflater.from(parent.context), parent, false
        )

    override fun bind(
        binding: AdapterExchangeRateItemBinding,
        item: UserExchangeRate
    ) {
        binding.item = item
        binding.root.setOnClickListener {
            clickListener(item)
        }
    }
}

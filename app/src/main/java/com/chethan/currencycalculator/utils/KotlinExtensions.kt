package com.example.tipjar.utils

import android.content.Context
import android.widget.Toast
import androidx.databinding.Observable
import java.math.BigDecimal.ROUND_CEILING

fun <T : Observable> T.addOnPropertyChanged(callback: (T) -> Unit) =
    addOnPropertyChangedCallback(
        object : Observable.OnPropertyChangedCallback() {
            override fun onPropertyChanged(
                observable: Observable?,
                i: Int
            ) =
                callback(observable as T)
        })

fun Context.presentUserMessage(userMessage: String) {
    Toast.makeText(this, userMessage, Toast.LENGTH_SHORT).show()
}

fun Double.roundOff(scale: Int): Double {
    return this.toBigDecimal()
        .setScale(scale, ROUND_CEILING)
        .toDouble()
}

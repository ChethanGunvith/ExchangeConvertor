package com.chethan.currencycalculator.utils

import android.app.Activity
import android.content.Context
import android.view.View
import android.view.inputmethod.InputMethodManager

object UtilUIComponents {

    fun hideKeyBoard(activity: Activity) {
        val inputManager =
            activity.getSystemService(Context.INPUT_METHOD_SERVICE) as InputMethodManager

        var view = activity.currentFocus
        if (view == null) {
            view = View(activity)
        }
        inputManager.hideSoftInputFromWindow(
            view.windowToken,
            InputMethodManager.HIDE_NOT_ALWAYS
        )
    }
}

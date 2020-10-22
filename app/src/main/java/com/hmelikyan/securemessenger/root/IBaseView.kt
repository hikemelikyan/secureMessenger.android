package com.hmelikyan.securemessenger.root

import androidx.annotation.StringRes

interface IBaseView {

 fun onNetworkError()

    fun onError(@StringRes resId: Int)

    fun onError(message: String)

    fun setLightStatusBar()

    fun clearLightStatusBar()

    fun isMainLoading(isLoading: Boolean)

}
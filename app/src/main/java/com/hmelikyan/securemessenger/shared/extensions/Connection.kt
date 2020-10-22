package com.hmelikyan.securemessenger.shared.extensions

import android.content.Context
import android.net.ConnectivityManager
import android.net.NetworkCapabilities
import android.net.NetworkInfo
import android.os.Build
import com.hmelikyan.securemessenger.App

fun isInternetAvailable(): Boolean {
    val connectivityManager = App.getInstance()!!.getSystemService(Context.CONNECTIVITY_SERVICE) as ConnectivityManager

    return if (Build.VERSION.SDK_INT > Build.VERSION_CODES.M) {
        connectivityManager.getNetworkCapabilities(connectivityManager.activeNetwork)?.let {
            it.hasTransport(NetworkCapabilities.TRANSPORT_WIFI) || it.hasTransport(NetworkCapabilities.TRANSPORT_CELLULAR)
        } ?: false
    } else {
        var netInfo = connectivityManager.getNetworkInfo(0)
        if (netInfo != null && netInfo.state == NetworkInfo.State.CONNECTED) {
            true
        } else {
            netInfo = connectivityManager.getNetworkInfo(1)
            netInfo != null && netInfo.state == NetworkInfo.State.CONNECTED
        }
    }
}
package com.hmelikyan.securemessenger.root.networking

import com.hmelikyan.securemessenger.App
import com.hmelikyan.securemessenger.shared.configs.AppConstants
import com.hmelikyan.securemessenger.shared.extensions.isInternetAvailable
import com.hmelikyan.securemessenger.shared.helpers.SharedPreferencesHelper
import okhttp3.Interceptor
import okhttp3.Response

class Authenticator : Interceptor {

    override fun intercept(chain: Interceptor.Chain): Response {
        val mShared = SharedPreferencesHelper.getInstance(App.getInstance())
        val newRequest = chain.request()
            .newBuilder()
            .header("Content-Type", "application/json")
            .header("Authorization", "Bearer ${mShared.readString(AppConstants.TOKEN)}")
            .header("DeviceToken", mShared.readString(AppConstants.FIREBASE_TOKEN) ?: "")
            .header("DeviceId", mShared.readString(AppConstants.DEVICE_ID, ""))
            .header("OsType","1")
            .header("Cache-Control", if (isInternetAvailable()) "public, max-age=${10 * 1024 * 1024}" else "public, max-stale=${10 * 1024 * 1024}")
            .build()

        val response = chain.proceed(newRequest)
        response.cacheResponse()
        return response
    }
}
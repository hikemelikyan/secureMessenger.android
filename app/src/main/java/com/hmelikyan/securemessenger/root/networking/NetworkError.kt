package com.hmelikyan.securemessenger.root.networking

import com.armboldmind.baggleapp.root.networking.UnauthorizedException
import com.hmelikyan.securemessenger.App
import com.hmelikyan.securemessenger.R
import retrofit2.HttpException
import java.net.HttpURLConnection

class NetworkError(private var mError: Throwable) : Throwable(mError) {
    private val mContext = App.getInstance()
    private lateinit var errorMessage: String

    init {
        isAuthError()
    }

    private fun isAuthError() {
        if (mError is HttpException) {
            //todo add default message
            errorMessage = mContext?.resources?.getString(R.string.default_error_message).toString()
//            errorMessage = mError.message!!
            if ((mError as HttpException).code() == HttpURLConnection.HTTP_UNAUTHORIZED) {
                throw UnauthorizedException(errorMessage)
            }
        } else {
            errorMessage = mError.message!!
        }
    }

    fun getAppErrorMessage(): String {
        return errorMessage
    }
}
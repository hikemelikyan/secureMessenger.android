package com.hmelikyan.securemessenger.root.handlers

import android.util.Log
import com.hmelikyan.securemessenger.root.networking.NetworkError
import com.armboldmind.baggleapp.root.networking.UnauthorizedException
import com.armboldmind.baggleapp.root.viewCommand.*
import com.hmelikyan.securemessenger.root.view_model.BaseViewModel
import com.hmelikyan.securemessenger.R
import com.hmelikyan.securemessenger.shared.extensions.isInternetAvailable
import kotlinx.coroutines.Job
import kotlinx.coroutines.delay
import kotlinx.coroutines.flow.Flow
import kotlinx.coroutines.flow.collect
import javax.net.ssl.SSLHandshakeException

interface ResponseHandler : LoadingHandler, ExceptionHandler {

    val target: BaseViewModel

    suspend fun <T> handle(
            processingTriple: Pair<Flow<Triple<Boolean, Throwable?, T?>?>, Job>,
            isSecondaryLoading: Boolean = false,
            doOnError: (() -> Any)? = null,
            doOnSuccess: suspend (T?) -> Unit
    ) {
        registerScope(processingTriple.second)
        processingTriple.first.collect {
            it?.let {
                Log.d("Response", it.toString())
                if (it.first) {
                    handle(it.first, isSecondaryLoading)
                } else {
                    delay(300)
                    if (it.second != null) {
                        handle(exception = it.second as Throwable)
                        doOnError?.invoke()
                    } else {
                        doOnSuccess.invoke(it.third)
                        delay(300)
                        handle(it.first, isSecondaryLoading)
                    }
                }
            }
        }
    }

    override fun handle(isLoading: Boolean, isSecondaryLoading: Boolean) {
        if (isLoading) {
            if (isSecondaryLoading)
                target.viewCommands.postValue(ShowSecondaryLoading())
            else {
                target.viewCommands.postValue(ShowLoadingViewCommand())
            }
        } else {
            if (isSecondaryLoading) {
                target.viewCommands.postValue(HideSecondaryLoading())
            } else {
                target.viewCommands.postValue(HideLoadingViewCommand())
            }
        }
    }

    override fun handle(exception: Throwable) {
        when (exception) {
            is NetworkError -> {
                if (!isInternetAvailable()) {
                    target.viewCommands.postValue(NetworkErrorViewCommand())
                } else {
                    target.viewCommands.postValue(ShowMessageTextViewCommand(exception.getAppErrorMessage()))
                }
            }
            is UnauthorizedException -> {
                target.viewCommands.postValue(RefreshTokenViewCommand())
            }
            is SSLHandshakeException -> {
                target.viewCommands.postValue(NetworkErrorViewCommand())
            }
            else -> {
                target.viewCommands.postValue(ShowMessageViewCommand(R.string.default_error_message))
            }
        }
    }

    fun registerScope(job: Job)
}
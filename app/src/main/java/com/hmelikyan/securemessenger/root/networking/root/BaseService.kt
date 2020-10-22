package com.hmelikyan.securemessenger.root.networking.root

import com.hmelikyan.securemessenger.root.networking.NetworkError
import com.hmelikyan.securemessenger.shared.extensions.getString
import com.hmelikyan.securemessenger.R
import com.hmelikyan.securemessenger.model.Response
import kotlinx.coroutines.*
import kotlinx.coroutines.channels.ConflatedBroadcastChannel
import kotlinx.coroutines.flow.*
import retrofit2.HttpException

@DslMarker
annotation class Request

@DslMarker
annotation class RequestWithPaging

@FlowPreview
@ExperimentalCoroutinesApi
open class BaseService {

    @Request
    protected infix fun <T> callAsync(request: suspend () -> Response<T>): Pair<Flow<Triple<Boolean, Throwable?, T?>?>, Job> {
        val broadcastChannel: ConflatedBroadcastChannel<Triple<Boolean, Throwable?, T?>?> = ConflatedBroadcastChannel(null)
        val scope = CoroutineScope(Job()).launch {
            broadcastChannel.send(Triple(true, null, null))
            proceed(broadcastChannel, request)
        }

        return Pair(broadcastChannel.asFlow(), scope)
    }

    private suspend fun <T> proceed(broadcastChannel: ConflatedBroadcastChannel<Triple<Boolean, Throwable?, T?>?>,
        request: suspend () -> Response<T>) {
        try {
            request.asFlow().map {
                if (!it.isSuccessful) {
                    throw NetworkError(HttpException(it))
                }
                if (it.body() == null) {
                    throw NetworkError(IllegalStateException(getString(R.string.default_error_message)))
                }
                if (it.body()?.success != true) {
                    throw NetworkError(IllegalStateException(it.body()?.messages!![0].value.toString()))
                }
                it.body()?.data
            }.catch { exception ->
                broadcastChannel.send(Triple(false, exception, null))
            }.collect {
                broadcastChannel.send(Triple(false, null, it))
            }
        } catch (e: Exception) {
            broadcastChannel.send(Triple(false, e, null))
        }
    }
}
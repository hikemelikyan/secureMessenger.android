package com.hmelikyan.securemessenger.model

import retrofit2.Response

data class ResponseModel<T>(
    val success: Boolean,
    val data: T?,
    val messages: List<MessageModel>
)

typealias Response<T> = Response<ResponseModel<T>>
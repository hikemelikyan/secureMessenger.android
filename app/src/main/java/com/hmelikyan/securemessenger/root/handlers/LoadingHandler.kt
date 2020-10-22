package com.hmelikyan.securemessenger.root.handlers

interface LoadingHandler {

    fun handle(isLoading: Boolean, isSecondaryLoading: Boolean)

}
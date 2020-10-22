package com.hmelikyan.securemessenger.root.handlers

interface ExceptionHandler {
    fun handle(exception: Throwable)
}
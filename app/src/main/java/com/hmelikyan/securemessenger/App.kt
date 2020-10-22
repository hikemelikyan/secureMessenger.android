package com.hmelikyan.securemessenger

import android.app.Application

class App : Application() {

    companion object {
        private var applicationInstance:App? = null

        fun getInstance() = applicationInstance
    }

    override fun onCreate() {
        super.onCreate()
        applicationInstance = this
    }
}
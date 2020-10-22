package com.hmelikyan.securemessenger.shared.helpers

import android.content.Context
import android.content.SharedPreferences
import androidx.core.content.edit
import com.google.gson.Gson
import com.hmelikyan.securemessenger.App

class SharedPreferencesHelper private constructor(context: Context) {

    companion object {
        private var INSTANCE: SharedPreferencesHelper? = null

        fun getInstance(context: Context? = null): SharedPreferencesHelper {
            if (INSTANCE == null) {
                INSTANCE = SharedPreferencesHelper(context ?: App.getInstance()!!)
            }
            return INSTANCE!!
        }
    }

    private val mShared: SharedPreferences = context.getSharedPreferences("Configs", Context.MODE_PRIVATE)

    fun writeString(key: String, value: String?) = mShared.edit(true) { putString(key, value) }

    fun readString(key: String): String? = mShared.getString(key, null)

    fun readString(key: String, defValue: String): String = mShared.getString(key, defValue)!!

    fun writeInt(key: String, value: Int) = mShared.edit(true) { putInt(key, value) }

    fun readInt(key: String): Int = mShared.getInt(key, 0)

    fun writeBoolean(key: String, value: Boolean) = mShared.edit(true) { putBoolean(key, value) }

    fun readBoolean(key: String): Boolean = mShared.getBoolean(key, false)

    fun <T> writeObject(key: String, `object`: T?) {
        val gson = Gson()
        writeString(key, `object`?.let { gson.toJson(it) })
    }

    inline fun <reified T> readObject(key: String): T? {
        val json = readString(key)
        json?.let {
            val gson = Gson()
            return gson.fromJson(json, T::class.java)
        }
        return null
    }

    fun clearSharedPreferences() = mShared.edit(true) { clear() }

    fun getSharedPreferences(): SharedPreferences = mShared

}
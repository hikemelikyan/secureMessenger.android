package com.hmelikyan.securemessenger.shared.extensions

import android.content.Context
import android.content.Intent
import android.net.Uri
import android.provider.Settings
import androidx.appcompat.app.AppCompatDelegate
import com.hmelikyan.securemessenger.App

enum class AppMode {
    LIGHT,
    DARK;

    companion object {
        fun get(isDark: Boolean) = if (isDark) DARK else LIGHT
    }
}

fun getAppMode(): AppMode {
    return AppMode.get(AppCompatDelegate.getDefaultNightMode() == AppCompatDelegate.MODE_NIGHT_YES)
}

fun createIntentToApplicationPackage(): Intent {
    val intent = Intent(Settings.ACTION_APPLICATION_DETAILS_SETTINGS)
    val uri = Uri.fromParts(
        "package",
        App.getInstance()!!.packageName,
        null
    )
    intent.data = uri
    return intent
}

infix fun String.callFrom(context: Context){
    val intent = Intent(Intent.ACTION_DIAL, Uri.parse("tel: $this"))
    context.startActivity(intent)
}
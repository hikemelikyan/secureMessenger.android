//package com.hmelikyan.securemessenger.shared.extensions
//
//import com.hmelikyan.securemessenger.R
//import com.hmelikyan.securemessenger.shared.configs.AppConstants
//import com.hmelikyan.securemessenger.shared.helpers.SharedPreferencesHelper
//import java.text.ParseException
//import java.text.SimpleDateFormat
//import java.util.*
//
//private const val UTC = "yyyy-MM-dd'T'HH:mm:ss"
//
//infix fun String.serverDateTo(pattern: String): String {
//    val sdf = SimpleDateFormat(
//        UTC, Locale(
//            SharedPreferencesHelper.getInstance().readString(AppConstants.LANGUAGE) ?: getString(R.string.english_lang_code)
//        )
//    )
//    sdf.timeZone = TimeZone.getTimeZone("UTC")
//
//    val sdf1 = SimpleDateFormat(
//        pattern, Locale(
//            SharedPreferencesHelper.getInstance().readString(AppConstants.LANGUAGE) ?: getString(R.string.english_lang_code)
//        )
//    )
//    sdf1.timeZone = TimeZone.getDefault()
//    return try {
//        sdf1.format(sdf.parse(this)!!)
//    } catch (e: ParseException) {
//        e.printStackTrace()
//        ""
//    }
//}
//
//fun String.fromTo(convertingDatePattern: String, datePattern: String): String {
//    val sdf = SimpleDateFormat(
//        datePattern, Locale(
//            SharedPreferencesHelper.getInstance().readString(AppConstants.LANGUAGE) ?: getString(R.string.english_lang_code)
//        )
//    )
//    sdf.timeZone = TimeZone.getTimeZone("UTC")
//
//    val sdf1 = SimpleDateFormat(
//        convertingDatePattern, Locale(
//            SharedPreferencesHelper.getInstance().readString(AppConstants.LANGUAGE) ?: getString(R.string.english_lang_code)
//        )
//    )
//    sdf1.timeZone = TimeZone.getDefault()
//    return try {
//        sdf1.format(sdf.parse(this)!!)
//    } catch (e: ParseException) {
//        e.printStackTrace()
//        ""
//    }
//}
//
//infix fun Date.parseTo(pattern: String): String {
//    val sdf = SimpleDateFormat(
//        pattern,
//        Locale(
//            SharedPreferencesHelper.getInstance().readString(AppConstants.LANGUAGE) ?: getString(R.string.english_lang_code)
//        )
//    )
//    return try {
//        sdf.format(this)
//    } catch (e: Throwable) {
//        e.printStackTrace()
//        ""
//    }
//}
//
//infix fun Date.parseToForServer(pattern: String): String {
//    val sdf = SimpleDateFormat(
//        pattern,
//        Locale(
//            SharedPreferencesHelper.getInstance().readString(AppConstants.LANGUAGE) ?: getString(R.string.english_lang_code)
//        )
//    )
//    sdf.timeZone = TimeZone.getTimeZone("UTC")
//    return try {
//        sdf.format(this)
//    } catch (e: Throwable) {
//        e.printStackTrace()
//        ""
//    }
//}
//
//infix fun String.convertStringToDate(datePattern: String): Date? {
//    val sdf = SimpleDateFormat(
//        datePattern, Locale(
//            SharedPreferencesHelper.getInstance().readString(AppConstants.LANGUAGE) ?: getString(R.string.english_lang_code)
//        )
//    )
//    sdf.timeZone = TimeZone.getTimeZone("UTC")
//    return try {
//        sdf.parse(this)
//    } catch (e: ParseException) {
//        e.printStackTrace()
//        Date()
//    }
//
//}
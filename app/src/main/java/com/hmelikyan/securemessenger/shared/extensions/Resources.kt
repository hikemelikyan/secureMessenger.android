package com.hmelikyan.securemessenger.shared.extensions

import android.content.res.ColorStateList
import android.graphics.Typeface
import android.os.Build
import android.util.TypedValue
import androidx.annotation.*
import androidx.core.content.res.ResourcesCompat
import com.hmelikyan.securemessenger.App

fun getColor(@ColorRes resId: Int): Int =
    if (Build.VERSION.SDK_INT > Build.VERSION_CODES.M)
        App.getInstance()!!.applicationContext.resources.getColor(resId, App.getInstance()!!.theme)
    else {
        App.getInstance()!!.applicationContext.resources.getColor(resId)
    }

fun getColorStateList(@ColorRes resId: Int): ColorStateList =
    if (Build.VERSION.SDK_INT > Build.VERSION_CODES.M)
        App.getInstance()!!.applicationContext.resources.getColorStateList(resId, App.getInstance()!!.theme)
    else {
        App.getInstance()!!.applicationContext.resources.getColorStateList(resId)
    }

fun getString(@StringRes resId: Int): String = App.getInstance()!!.applicationContext.resources.getString(resId)

fun getFont(@FontRes resId: Int): Typeface? =
    if (Build.VERSION.SDK_INT < Build.VERSION_CODES.O) {
        ResourcesCompat.getFont(App.getInstance()!!.applicationContext, resId)
    } else {
        App.getInstance()!!.applicationContext.resources.getFont(resId)
    }

fun getDrawable(@DrawableRes resId: Int) = ResourcesCompat.getDrawable(App.getInstance()?.resources!!, resId, App.getInstance()!!.theme)!!

fun getDimensions(@DimenRes resId: Int) = App.getInstance()!!.applicationContext.resources.getDimension(resId)

fun getStyledAttribute(@AttrRes resId: Int): Int {
    val typedValue = TypedValue()
    App.getInstance()!!.theme.resolveAttribute(resId, typedValue, true)
    return typedValue.data
}
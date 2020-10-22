package com.hmelikyan.securemessenger.shared.extensions

import android.R
import android.app.Activity
import android.content.Context
import android.content.res.Resources
import android.util.DisplayMetrics
import android.util.TypedValue
import android.view.Window
import android.view.WindowManager
import com.hmelikyan.securemessenger.App
import kotlin.math.roundToInt


private val density by lazy { Resources.getSystem().displayMetrics.density }

private val densityDpi by lazy { Resources.getSystem().displayMetrics.densityDpi }

fun Int.dpToPx(): Int {
    return (this * density).roundToInt()
}

fun Int.pxToDp(): Float {
    return this / (densityDpi / 160f)
}

fun Float.dpToPx(): Int {
    return (this * density).roundToInt()
}

fun Float.pxToDp(): Float {
    return this / (densityDpi / 160f)
}

fun Context.getDisplayHeight(): Int {
    val displayMetrics = DisplayMetrics()
    val windowManager = this.getSystemService(Context.WINDOW_SERVICE) as WindowManager
    windowManager.defaultDisplay.getMetrics(displayMetrics)
    return displayMetrics.heightPixels
}

fun Context.getDisplayWidth(): Int {
    val displayMetrics = DisplayMetrics()
    val windowManager = this.getSystemService(Context.WINDOW_SERVICE) as WindowManager
    windowManager.defaultDisplay.getMetrics(displayMetrics)
    return displayMetrics.widthPixels
}

fun Context.getListPreferedItemHeight(): Int {
    return TypedValue.complexToDimensionPixelSize(getStyledAttribute(R.attr.listPreferredItemHeight), this.resources.displayMetrics)
}

fun Context.getNavigationBarHeight(): Int {
    val resources = this.resources
    val resourceId = resources.getIdentifier("navigation_bar_height", "dimen", "android")
    return resources.getDimensionPixelSize(resourceId)
}

fun Activity.getStatusBarHeight(): Int {
    val rectangle = android.graphics.Rect()
    val window: Window = this.window
    window.decorView.getWindowVisibleDisplayFrame(rectangle)
    return rectangle.top
}

fun Context.getActionBarHeight(): Int {
    var actionBarHeight = 0
    val tv = TypedValue()
    if (this.theme.resolveAttribute(android.R.attr.actionBarSize, tv, true)) {
        actionBarHeight =
            TypedValue.complexToDimensionPixelSize(
                tv.data,
                App.getInstance()!!.applicationContext.resources.displayMetrics
            )
    }
    return actionBarHeight
}
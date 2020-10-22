package com.hmelikyan.securemessenger.shared.extensions

import android.graphics.Typeface
import android.os.Build
import android.text.SpannableString
import android.text.TextPaint
import android.text.style.ClickableSpan
import android.text.style.ForegroundColorSpan
import android.text.style.StyleSpan
import android.text.style.TypefaceSpan
import android.view.View
import com.hmelikyan.securemessenger.R

enum class FontType {
    BOLD,
    REGULAR,
    LIGHT
}

fun SpannableString.markText(text: String, color: Int = getColor(R.color.baseGreen)): SpannableString {
    this.setSpan(
        ForegroundColorSpan(color),
        this.indexOf(text),
        this.indexOf(text) + text.length,
        SpannableString.SPAN_EXCLUSIVE_EXCLUSIVE
    )
    return this
}

fun SpannableString.setClickable(text: String, click: () -> Unit): SpannableString {
    this.setSpan(
        object : ClickableSpan() {
            override fun onClick(p0: View) {
                click()
            }

            override fun updateDrawState(ds: TextPaint) {
                ds.isUnderlineText = false
            }
        },
        this.indexOf(text),
        this.indexOf(text) + text.length,
        SpannableString.SPAN_EXCLUSIVE_EXCLUSIVE
    )
    return this
}

fun CharSequence.markText(text: String, color: Int = getColor(R.color.baseGreen)): SpannableString {
    val span = SpannableString(this)
    span.setSpan(
        ForegroundColorSpan(color),
        span.indexOf(text),
        span.indexOf(text) + text.length,
        SpannableString.SPAN_EXCLUSIVE_EXCLUSIVE
    )
    return span
}
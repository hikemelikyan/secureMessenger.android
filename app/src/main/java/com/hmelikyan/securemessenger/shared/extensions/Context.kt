package com.hmelikyan.securemessenger.shared.extensions

import android.content.Context
import android.view.LayoutInflater

fun Context.inflater(): LayoutInflater = LayoutInflater.from(this)
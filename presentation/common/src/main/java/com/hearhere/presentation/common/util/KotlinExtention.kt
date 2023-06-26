package com.hearhere.presentation.util

import android.content.Context
import android.content.res.Resources
import android.util.TypedValue
import java.text.DecimalFormat
import java.text.SimpleDateFormat
import java.util.*

val Int.dp
    get() = Resources.getSystem().displayMetrics?.let { dm ->
        TypedValue.applyDimension(TypedValue.COMPLEX_UNIT_DIP, this.toFloat(), dm)
    } ?: 0f

val Int.dpToPx
    get() = Resources.getSystem().displayMetrics?.let { dm ->
        this * dm.density
    } ?: 0f

val Float.dpToPx
    get() = Resources.getSystem().displayMetrics?.let { dm ->
        this * dm.density
    } ?: 0f

fun Int.toConcurrency(): String {
    return DecimalFormat("#,###").format(this)
}

val screenWidth: Int
    get() = Resources.getSystem().displayMetrics?.widthPixels ?: 0

val screenHeight: Int
    get() = Resources.getSystem().displayMetrics?.heightPixels ?: 0

fun String.toDigit(): Int {
    return filter { it.isDigit() }.toIntOrNull() ?: 0
}

fun String.toNumber(): String {
    return (filter { it.isDigit() }.toLongOrNull() ?: 0L).toString()
}

fun String.toNumberFormat(): String {
    return toNumber()
        .reversed()
        .chunked(3)
        .joinToString(",")
        .reversed()
}

fun String.toDate(pattern: String): Date {
    val format = SimpleDateFormat(pattern, Locale.getDefault())
    return format.parse(this) ?: Date(0)
}

fun ConvertDPtoPX(context: Context, dp: Int): Int {
    val density = context.resources.displayMetrics.density
    return Math.round(dp.toFloat() * density)
}

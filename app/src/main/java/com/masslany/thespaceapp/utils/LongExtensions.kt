package com.masslany.thespaceapp.utils

import java.text.SimpleDateFormat
import java.util.*
import kotlin.math.abs

fun Long.toDateSting(): String {
    val date = Date(this * 1000)
    val sdf = SimpleDateFormat("dd.MM.yyyy, hh:mm", Locale.GERMANY)
    return sdf.format(date)
}

fun Long.toCountdownString(currentTimeInMillis: Long = System.currentTimeMillis()): String {
    val dateNow = currentTimeInMillis / 1000
    val diff = this - dateNow

    val format = if (diff > 0) "T-%s:%s:%s:%s" else "T+%s:%s:%s:%s"

    return String.format(
        format,
        (abs(diff) / 60 / 60 / 24).toString().padStart(2, '0'), // days
        (abs(diff) / 60 / 60 % 24).toString().padStart(2, '0'), // hours
        (abs(diff) / 60 % 60).toString().padStart(2, '0'), // minutes
        (abs(diff) % 60).toString().padStart(2, '0') // seconds
    )
}
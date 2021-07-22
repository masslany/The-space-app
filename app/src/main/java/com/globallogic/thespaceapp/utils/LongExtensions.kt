package com.globallogic.thespaceapp.utils

import java.text.SimpleDateFormat
import java.util.*

fun Long.toDateSting(): String {
    val date = Date(this * 1000)
    val sdf = SimpleDateFormat("dd.MM.yyyy, hh:mm", Locale.GERMANY)
    return sdf.format(date)
}

fun Long.toCountdownString(): String {
    val dateNow = System.currentTimeMillis() / 1000
    val diff = this - dateNow

    val format = if (diff > 0) "T-%s:%s:%s:%s" else "T+%s:%s:%s:%s"

    return String.format(
        format,
        (diff / 60 / 60 / 24).toString().padStart(2, '0'), // days
        (diff / 60 / 60 % 24).toString().padStart(2, '0'), // hours
        (diff / 60 % 60).toString().padStart(2, '0'), // minutes
        (diff % 60).toString().padStart(2, '0') // seconds
    )
}
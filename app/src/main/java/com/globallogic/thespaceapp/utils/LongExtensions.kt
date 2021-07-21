package com.globallogic.thespaceapp.utils

import java.text.SimpleDateFormat
import java.util.*

fun Long.toDateSting(): String {
    val date = Date(this * 1000)
    val sdf = SimpleDateFormat("dd.MM.yyyy, hh:mm", Locale.GERMANY)
    return sdf.format(date)
}
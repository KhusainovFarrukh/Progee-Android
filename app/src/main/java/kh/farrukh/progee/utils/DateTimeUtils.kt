package kh.farrukh.progee.utils

import java.text.DateFormat
import java.text.SimpleDateFormat
import java.util.*

/**
 *Created by farrukh_kh on 3/14/22 5:43 PM
 *kh.farrukh.movix.utils
 **/
val apiDateFormat = SimpleDateFormat("yyyy-MM-dd", Locale.US)

fun String.apiDateToUiDateStr(): String = try {
    val outputDate = apiDateFormat.parse(this) ?: throw Exception("Cannot parse date")
    DateFormat.getDateInstance(DateFormat.LONG, Locale.US).format(outputDate)
} catch (e: Exception) {
    e.printStackTrace()
    ""
}
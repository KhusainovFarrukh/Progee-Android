package kh.farrukh.progee.utils

import android.content.Context
import kh.farrukh.progee.R
import java.text.DateFormat
import java.text.SimpleDateFormat
import java.util.*

/**
 *Created by farrukh_kh on 3/14/22 5:43 PM
 *kh.farrukh.movix.utils
 **/
val apiDateTimeFormat = SimpleDateFormat("yyyy-MM-dd'T'HH:mm:ss.S'Z'", Locale.US)

fun String.parseDateString(): String = parseDate()?.let {
    DateFormat.getDateInstance(DateFormat.LONG, Locale.US).format(it)
} ?: ""

fun String.parseDate(): Date? = try {
    apiDateTimeFormat.parse(this) ?: throw Exception("Cannot parse date")
} catch (e: Exception) {
    e.printStackTrace()
    null
}

fun String.calculateCreatedOn(context: Context) = try {
    val createdOn = parseDate()
    val current = Date()

    val differenceInMillis = current.time - (createdOn?.time ?: current.time)

    val seconds = (differenceInMillis / 1000).toInt()
    val minutes = seconds / 60
    val hours = minutes / 60
    val days = hours / 24
    val weeks = days / 7
    val months = days / 30
    val years = days / 365

    when {
        years != 0 -> {
            if (years == 1) context.getString(
                R.string.year_ago,
                years
            ) else context.getString(R.string.years_ago, years)
        }
        months != 0 -> {
            if (months == 1) context.getString(
                R.string.month_ago,
                months
            ) else context.getString(R.string.months_ago, months)
        }
        weeks != 0 -> {
            if (weeks == 1) context.getString(
                R.string.week_ago,
                weeks
            ) else context.getString(R.string.weeks_ago, weeks)
        }
        days != 0 -> {
            if (days == 1) context.getString(R.string.day_ago, days)
            else context.getString(R.string.days_ago, days)
        }
        hours != 0 -> {
            if (hours == 1) context.getString(R.string.hour_ago, hours)
            else context.getString(R.string.hours_ago, hours)
        }
        minutes != 0 -> {
            if (minutes == 1) context.getString(R.string.minute_ago, minutes)
            else context.getString(R.string.minutes_ago, minutes)
        }
        seconds != 0 -> {
            if (seconds == 1) context.getString(R.string.second_ago, seconds)
            else context.getString(R.string.seconds_ago, seconds)
        }
        else -> context.getString(R.string.recently)
    }

} catch (e: Exception) {
    "Unknown"
}
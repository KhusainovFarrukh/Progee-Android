package kh.farrukh.progee.utils

import android.content.Context
import android.widget.Toast
import androidx.annotation.StringRes
import androidx.fragment.app.Fragment
import kh.farrukh.progee.R

/**
 *Created by farrukh_kh on 3/6/22 8:56 PM
 *kh.farrukh.movix.utils
 **/
private fun Context.toast(message: String, length: Int) {
    Toast.makeText(this, message, length).show()
}

private fun Context.toast(@StringRes messageId: Int, length: Int) {
    Toast.makeText(this, getString(messageId), length).show()
}

fun Context.toastShort(message: String = "") = toast(message, Toast.LENGTH_SHORT)

fun Context.toastLong(message: String = "") = toast(message, Toast.LENGTH_LONG)

fun Context.toastShort(@StringRes messageId: Int = R.string.app_name) =
    toast(messageId, Toast.LENGTH_SHORT)

fun Context.toastLong(@StringRes messageId: Int = R.string.app_name) =
    toast(messageId, Toast.LENGTH_LONG)

fun Fragment.toast(message: String = "", length: Int = Toast.LENGTH_SHORT) =
    requireContext().toast(message, length)

fun Fragment.toast(@StringRes messageId: Int, length: Int) =
    requireContext().toast(messageId, length)

fun Fragment.toastShort(message: String = "") = toast(message, Toast.LENGTH_SHORT)

fun Fragment.toastShort(@StringRes messageId: Int = R.string.app_name) =
    toast(messageId, Toast.LENGTH_SHORT)

fun Fragment.toastLong(message: String = "") = toast(message, Toast.LENGTH_LONG)

fun Fragment.toastLong(@StringRes messageId: Int = R.string.app_name) =
    toast(messageId, Toast.LENGTH_LONG)
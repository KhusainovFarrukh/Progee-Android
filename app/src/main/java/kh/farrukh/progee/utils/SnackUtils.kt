package kh.farrukh.progee.utils

import androidx.fragment.app.Fragment
import com.google.android.material.snackbar.Snackbar

/**
 *Created by farrukh_kh on 3/25/22 11:51 PM
 *kh.farrukh.movix.utils
 **/

fun Fragment.snackLong(
    message: String = "",
    action: (() -> Unit)? = null,
) = snack(message, action, Snackbar.LENGTH_LONG)

fun Fragment.snackShort(
    message: String = "",
    action: (() -> Unit)? = null,
) = snack(message, action, Snackbar.LENGTH_SHORT)

fun Fragment.snackIndefinite(
    message: String = "",
    action: (() -> Unit)? = null,
) = snack(message, action, Snackbar.LENGTH_INDEFINITE)

private fun Fragment.snack(
    message: String = "",
    action: (() -> Unit)? = null,
    duration: Int = Snackbar.LENGTH_LONG
) {
    val snackBar = Snackbar.make(requireActivity().findViewById(android.R.id.content), message, duration)
    action?.let { snackBar.setAction("Retry") { action() } }
    snackBar.show()
}
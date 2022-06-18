package kh.farrukh.progee.utils

import android.content.Context
import android.view.View
import android.view.inputmethod.InputMethodManager
import android.view.inputmethod.InputMethodManager.SHOW_FORCED
import android.view.inputmethod.InputMethodManager.SHOW_IMPLICIT
import androidx.appcompat.app.AppCompatActivity
import androidx.appcompat.widget.SearchView
import androidx.fragment.app.Fragment

/**
 *Created by farrukh_kh on 4/16/22 8:29 AM
 *kh.farrukh.movix.utils
 **/
fun Fragment.hideKeyboard() = try {
    val imm =
        requireActivity().getSystemService(Context.INPUT_METHOD_SERVICE) as InputMethodManager
    imm.hideSoftInputFromWindow(view?.windowToken, 0)
} catch (e: Exception) {
    e.printStackTrace()
    false
}

fun AppCompatActivity.hideKeyboard() = try {
    val imm = getSystemService(Context.INPUT_METHOD_SERVICE) as InputMethodManager
    imm.hideSoftInputFromWindow(currentFocus?.windowToken, 0)
} catch (e: Exception) {
    e.printStackTrace()
    false
}

fun Fragment.showKeyboard(view: View) = try {
    view.requestFocus()
    val imm =
        requireActivity().getSystemService(Context.INPUT_METHOD_SERVICE) as InputMethodManager
    imm.showSoftInput(view, SHOW_FORCED)
} catch (e: Exception) {
    e.printStackTrace()
    false
}

fun AppCompatActivity.showKeyboard(view: View) = try {
    view.requestFocus()
    val imm = getSystemService(Context.INPUT_METHOD_SERVICE) as InputMethodManager
    imm.showSoftInput(view, SHOW_FORCED)
} catch (e: Exception) {
    e.printStackTrace()
    false
}
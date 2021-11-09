package com.dateforyou.best.utils

import android.app.Activity
import android.content.Context
import android.util.Patterns
import android.view.View
import android.view.inputmethod.InputMethodManager
import android.widget.EditText
import android.widget.TextView
import androidx.annotation.ColorInt
import androidx.core.content.ContextCompat
import com.dateforyou.best.R
import com.google.android.material.snackbar.Snackbar

fun showKeyboard(context: Context, editText: EditText?) {
    editText?.requestFocus()
    val imm = context.getSystemService(Context.INPUT_METHOD_SERVICE) as InputMethodManager
    imm.toggleSoftInput(InputMethodManager.SHOW_FORCED, 0)
}

fun hideKeyboard(context: Context, editText: EditText?, parentView: View?) {
    editText?.clearFocus()
    val inputMethodManager =
        context.getSystemService(Activity.INPUT_METHOD_SERVICE) as InputMethodManager
    inputMethodManager.hideSoftInputFromWindow(parentView?.windowToken, 0)
}

fun Snackbar.withColor(@ColorInt colorInt: Int): Snackbar {
    this.view.setBackgroundColor(colorInt)
    return this
}

fun showErrorSnackbar(context: Context, parentView: View?, errorMessage: String) {

    if (parentView != null) {
        Snackbar.make(
            parentView,
            errorMessage,
            Snackbar.LENGTH_SHORT
        )
            .withColor(ContextCompat.getColor(context, R.color.red))
            .apply {
                this.view.findViewById<TextView>(com.google.android.material.R.id.snackbar_text).maxLines =
                    10
            }
            .show()
    }
}

fun showSimpleSnackbar(context: Context, parentView: View?, message: String) {

    if (parentView != null) {
        Snackbar.make(
            parentView,
            message,
            Snackbar.LENGTH_LONG
        )
            .withColor(ContextCompat.getColor(context, R.color.green))
            .apply {
                this.view.findViewById<TextView>(com.google.android.material.R.id.snackbar_text).maxLines =
                    10
            }
            .show()
    }
}

fun String.isEmailValid() = Patterns.EMAIL_ADDRESS.matcher(this).matches()
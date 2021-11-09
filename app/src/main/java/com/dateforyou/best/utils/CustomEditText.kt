package com.dateforyou.best.utils

import android.content.Context
import android.util.AttributeSet
import android.view.KeyEvent
import androidx.appcompat.widget.AppCompatEditText

class CustomEditText(context: Context, attrs: AttributeSet) : AppCompatEditText(context, attrs) {

    override fun onKeyPreIme(keyCode: Int, event: KeyEvent): Boolean {
        if (keyCode == KeyEvent.KEYCODE_BACK) {
            // User has pressed Back key. So hide the keyboard

            clearFocus()
        }
        return false
    }
}
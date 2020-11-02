package com.sunasterisk.appchat.utils

import android.view.View
import com.google.android.material.snackbar.Snackbar

fun View.showSnackBar(msg: String, length: Int = Snackbar.LENGTH_LONG) {
    Snackbar.make(this, msg, length).show()
}

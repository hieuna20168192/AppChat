package com.sunasterisk.appchat.ui.binding

import android.view.View
import android.widget.EditText
import androidx.databinding.BindingAdapter
import com.google.android.material.button.MaterialButton
import com.google.android.material.textfield.TextInputLayout
import com.sunasterisk.appchat.R
import com.sunasterisk.appchat.utils.isEmail
import com.sunasterisk.appchat.utils.isValidPassword
import com.sunasterisk.appchat.utils.showSnackBar

@BindingAdapter("onEmailError")
fun TextInputLayout.onEmailError(email: String?) {
    if (email != null && !email.isEmail) {
        isErrorEnabled = true
        error = context.resources.getString(R.string.msg_email_invalid)
    } else {
        isErrorEnabled = false
    }
}

@BindingAdapter("onPasswordError")
fun TextInputLayout.onPasswordError(password: String?) {
    if (password != null && !password.isValidPassword) {
        isErrorEnabled = true
        error = context.resources.getString(R.string.msg_password_invalid)
    } else {
        error = null
    }
}

@BindingAdapter("isVisible")
fun View.isVisible(isVisible: Boolean?) {
    visibility = if (isVisible == true) View.VISIBLE else View.INVISIBLE
}

@BindingAdapter("showError")
fun View.showError(msg: String?) {
    if (!msg.isNullOrBlank()) {
        showSnackBar(msg)
    }
}

@BindingAdapter(value = ["inputEmail", "inputPassword", "onListenerLogin"], requireAll = true)
fun onListenerLogin(
    loginButton: MaterialButton,
    inputEmail: EditText,
    inputPassword: EditText,
    listener: (email: String, password: String) -> Unit,
) {
    var email: String
    var password: String
    var isValidFormat: Boolean
    loginButton.setOnClickListener {
        password = inputPassword.text.toString()
        email = inputEmail.text.toString()
        isValidFormat =
            checkValidFormatInput(email, password)
        if (isValidFormat) {
            listener.invoke(email, password)
        }
    }
}

private fun checkValidFormatInput(email: String?, password: String?) =
    !email.isNullOrEmpty() && !password.isNullOrEmpty() &&
            email.isEmail && password.isValidPassword

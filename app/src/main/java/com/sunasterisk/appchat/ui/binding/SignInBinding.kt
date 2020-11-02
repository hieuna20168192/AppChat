package com.sunasterisk.appchat.ui.binding

import android.view.View
import android.widget.EditText
import androidx.databinding.BindingAdapter
import com.facebook.AccessToken
import com.facebook.CallbackManager
import com.facebook.FacebookCallback
import com.facebook.FacebookException
import com.facebook.login.LoginResult
import com.facebook.login.widget.LoginButton
import com.google.android.material.button.MaterialButton
import com.google.android.material.textfield.TextInputLayout
import com.shobhitpuri.custombuttons.GoogleSignInButton
import com.sunasterisk.appchat.R
import com.sunasterisk.appchat.db.Result
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

@BindingAdapter("onListenerGoogleSignIn")
fun GoogleSignInButton.onListenerGoogleSignIn(listener: () -> Unit) {
    setOnClickListener {
        listener.invoke()
    }
}

@BindingAdapter("readPermissions")
fun LoginButton.readPermissions(permissions: List<String>?) {
    if (!permissions.isNullOrEmpty()) {
        setPermissions(permissions)
    }
}

@BindingAdapter(value = ["registerCallback", "listenerFacebookLogin"])
fun registerCallback(
    loginButton: LoginButton,
    callbackManager: CallbackManager?,
    listener: (tokenResult: Result<AccessToken>) -> Unit,
) {
    loginButton.registerCallback(callbackManager, object : FacebookCallback<LoginResult> {
        override fun onSuccess(result: LoginResult) {
            val token = result.accessToken
            listener.invoke(Result.success(token))
        }

        override fun onCancel() = Unit

        override fun onError(error: FacebookException) {
            listener.invoke(Result.failed(error))
        }
    })
}

private fun checkValidFormatInput(email: String?, password: String?) =
    !email.isNullOrEmpty() && !password.isNullOrEmpty() &&
            email.isEmail && password.isValidPassword

package com.sunasterisk.appchat.ui.binding

import android.graphics.ImageDecoder
import android.net.Uri
import android.os.Build
import android.widget.EditText
import android.widget.ImageView
import androidx.annotation.RequiresApi
import androidx.databinding.BindingAdapter
import com.google.android.material.button.MaterialButton
import com.google.android.material.imageview.ShapeableImageView
import com.google.android.material.textfield.TextInputLayout
import com.sunasterisk.appchat.R
import com.sunasterisk.appchat.utils.isEmail
import com.sunasterisk.appchat.utils.isValidPassword

@RequiresApi(Build.VERSION_CODES.P)
@BindingAdapter("imageUri")
fun ShapeableImageView.imageUri(imageUri: Uri?) {
    if (imageUri == null) return
    val source = ImageDecoder.createSource(context.contentResolver, imageUri)
    val bitmap = ImageDecoder.decodeBitmap(source)
    setImageBitmap(bitmap)
}

@BindingAdapter("onListenerPick")
fun ImageView.onListenerPick(listener: () -> Unit) {
    setOnClickListener {
        listener.invoke()
    }
}

@BindingAdapter(value = ["onErrorConfirmPass", "inputPassword"], requireAll = true)
fun onErrorConfirmPass(
    confirmPassLayout: TextInputLayout,
    confirmPass: String?,
    inputPassword: EditText,
) {
    if (!confirmPass.isNullOrEmpty() && confirmPass != inputPassword.text.toString()) {
        confirmPassLayout.error =
            confirmPassLayout.context.resources.getString(R.string.msg_confirm_password_unmatched)
    } else {
        confirmPassLayout.error = null
    }
}

@BindingAdapter(
    value = ["inputEmail", "inputPassword", "inputConfirmPass", "onListenerRegister"],
    requireAll = true
)
fun onListenerRegister(
    loginButton: MaterialButton,
    inputEmail: EditText,
    inputPassword: EditText,
    inputConfirmPass: EditText,
    listener: (email: String, password: String) -> Unit,
) {
    var email: String
    var password: String
    var confirmPass: String
    var isValidFormat: Boolean
    loginButton.setOnClickListener {
        password = inputPassword.text.toString()
        email = inputEmail.text.toString()
        confirmPass = inputConfirmPass.text.toString()
        isValidFormat =
            checkValidFormatInput(email, password, confirmPass)
        if (isValidFormat) {
            listener.invoke(email, password)
        }
    }
}

private fun checkValidFormatInput(email: String?, password: String?, confirmPass: String?) =
    !email.isNullOrEmpty() && !password.isNullOrEmpty() &&
            email.isEmail && password.isValidPassword &&
            confirmPass == password

package com.sunasterisk.appchat.ui.auth.signup

import com.sunasterisk.appchat.db.entity.User

interface SignUpNavigator {
    fun onRegisterSuccess(user: User? = null)
    fun onPickPhoto()
}

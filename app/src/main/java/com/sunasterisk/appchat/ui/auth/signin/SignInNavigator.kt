package com.sunasterisk.appchat.ui.auth.signin

import com.sunasterisk.appchat.db.entity.User

interface SignInNavigator {
    fun onSignInSuccess(user: User? = null)
    fun onGoogleSignInClient()
}

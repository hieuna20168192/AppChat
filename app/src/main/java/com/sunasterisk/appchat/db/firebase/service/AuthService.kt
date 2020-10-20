package com.sunasterisk.appchat.db.firebase.service

import com.google.firebase.auth.AuthResult
import com.sunasterisk.appchat.db.Result

interface AuthService {
    suspend fun logIn(username: String, password: String): Result<AuthResult>
}

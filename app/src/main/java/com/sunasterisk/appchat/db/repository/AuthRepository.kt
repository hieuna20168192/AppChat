package com.sunasterisk.appchat.db.repository

import android.net.Uri
import com.google.firebase.auth.AuthCredential
import com.google.firebase.auth.AuthResult
import com.sunasterisk.appchat.db.Result
import com.sunasterisk.appchat.db.entity.*
import com.sunasterisk.appchat.db.firebase.service.AuthService
import com.sunasterisk.appchat.db.persistent.database.dao.UserDao
import com.sunasterisk.appchat.db.persistent.pref.UserPref
import kotlinx.coroutines.flow.*

class AuthRepository(
    private val authService: AuthService,
    private val userDao: UserDao,
) : Repository.AuthRepository {

    override fun logIn(username: String, password: String): Flow<Result<AuthResult>> =
        authService.logIn(username, password).onEach { authResult ->
            if (authResult is Result.Success) {
                authResult.data.user?.uid?.let { userId -> UserPref.saveUserId(userId) }
            }
        }

    override fun register(
        username: String,
        password: String,
        selectedPhotoUri: Uri
    ): Flow<Result<User>> =
        authService.register(username, password, selectedPhotoUri).onEach { authUser ->
            if (authUser is Result.Success) {
                userDao.insert(authUser.data)
            }
        }

    override fun firebaseSignInWithCredential(googleAuthCredential: AuthCredential): Flow<Result<User>> =
        authService.firebaseSignInWithCredential(googleAuthCredential).onEach { authUser ->
            if (authUser is Result.Success) {
                userDao.insert(authUser.data)
            }
        }
}

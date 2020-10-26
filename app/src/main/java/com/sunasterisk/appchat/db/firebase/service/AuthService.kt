package com.sunasterisk.appchat.db.firebase.service

import android.net.Uri
import com.google.firebase.auth.AuthCredential
import com.google.firebase.auth.AuthResult
import com.sunasterisk.appchat.db.Result
import com.sunasterisk.appchat.db.entity.User
import kotlinx.coroutines.flow.Flow

interface AuthService {
    fun logIn(username: String, password: String): Flow<Result<AuthResult>>
    fun firebaseSignInWithCredential(googleAuthCredential: AuthCredential): Flow<Result<User>>
    suspend fun createUserInFireStoreIfNotExists(authenticatedUser: User): Result<User>
    fun register(
        username: String,
        password: String,
        selectedPhotoUri: Uri
    ): Flow<Result<User>>
    suspend fun uploadImageToFireBaseStorage(selectedPhotoUri: Uri): Result<String>
}

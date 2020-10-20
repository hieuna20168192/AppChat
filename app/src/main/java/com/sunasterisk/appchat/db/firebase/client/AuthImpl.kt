package com.sunasterisk.appchat.db.firebase.client

import com.google.firebase.auth.FirebaseAuth
import com.google.firebase.auth.FirebaseAuthEmailException
import com.google.firebase.firestore.FirebaseFirestore
import com.sunasterisk.appchat.db.firebase.RemoteConstant.EMAIL_NOT_VERIFIED
import com.sunasterisk.appchat.db.firebase.RemoteConstant.MSG_EMAIL_NOT_VERIFIED
import com.sunasterisk.appchat.db.firebase.service.AuthService
import com.sunasterisk.appchat.utils.getOneShotResult
import kotlinx.coroutines.ExperimentalCoroutinesApi
import kotlinx.coroutines.tasks.await

@ExperimentalCoroutinesApi
class AuthImpl(
    private val firebaseAuth: FirebaseAuth,
    private val fireStore: FirebaseFirestore
) : AuthService {

    override suspend fun logIn(username: String, password: String) = getOneShotResult {
        firebaseAuth.signInWithEmailAndPassword(username, password).await().run {
            if (user?.isEmailVerified == false) {
                throw FirebaseAuthEmailException(EMAIL_NOT_VERIFIED, MSG_EMAIL_NOT_VERIFIED)
            } else {
                this
            }
        }
    }
}

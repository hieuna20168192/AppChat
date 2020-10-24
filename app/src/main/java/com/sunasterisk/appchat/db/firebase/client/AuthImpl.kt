package com.sunasterisk.appchat.db.firebase.client

import android.net.Uri
import com.google.firebase.auth.AuthCredential
import com.google.firebase.auth.AuthResult
import com.google.firebase.auth.FirebaseAuth
import com.google.firebase.auth.FirebaseAuthEmailException
import com.google.firebase.firestore.CollectionReference
import com.google.firebase.firestore.FirebaseFirestore
import com.google.firebase.storage.FirebaseStorage
import com.sunasterisk.appchat.App
import com.sunasterisk.appchat.R
import com.sunasterisk.appchat.db.Result
import com.sunasterisk.appchat.db.entity.User
import com.sunasterisk.appchat.db.firebase.RemoteConstant.COLLECTION_USER
import com.sunasterisk.appchat.db.firebase.RemoteConstant.EMAIL_NOT_VERIFIED
import com.sunasterisk.appchat.db.firebase.RemoteConstant.MSG_EMAIL_NOT_VERIFIED
import com.sunasterisk.appchat.db.firebase.service.AuthService
import kotlinx.coroutines.ExperimentalCoroutinesApi
import kotlinx.coroutines.flow.flow
import kotlinx.coroutines.tasks.await
import java.util.*

@ExperimentalCoroutinesApi
class AuthImpl(
    private val firebaseAuth: FirebaseAuth,
    private val fireStore: FirebaseFirestore,
    private val firebaseStorage: FirebaseStorage,
) : AuthService {

    private val usersRef: CollectionReference = fireStore.collection(COLLECTION_USER)

    override fun logIn(username: String, password: String) =
        flow<Result<AuthResult>> {
            firebaseAuth.signInWithEmailAndPassword(username, password).await().run {
                if (user?.isEmailVerified == false) {
                    val throwable =
                        FirebaseAuthEmailException(EMAIL_NOT_VERIFIED, MSG_EMAIL_NOT_VERIFIED)
                    error(Result.Failed(throwable))
                } else {
                    emit(Result.Success(this))
                }
            }
        }

    override fun firebaseSignInWithGoogle(googleAuthCredential: AuthCredential) =
        flow {
            firebaseAuth.signInWithCredential(googleAuthCredential).await().run {
                val isNewUser = additionalUserInfo?.isNewUser ?: false
                if (isNewUser) {
                    user?.let {
                        val authenticatedUser =
                            User(userId = it.uid, email = it.email)
                        val signInResult = createUserInFireStoreIfNotExists(authenticatedUser)
                        if (signInResult is Result.Success) {
                            emit(signInResult)
                        } else if (signInResult is Result.Failed) {
                            error(signInResult)
                        }
                    }
                }
            }
        }

    override suspend fun createUserInFireStoreIfNotExists(authenticatedUser: User): Result<User> {
        val uidRef = usersRef.document(authenticatedUser.userId)
        uidRef.get().await().run {
            if (!this.exists()) {
                uidRef.set(authenticatedUser).await().run {
                    return Result.success(authenticatedUser)
                }
            } else {
                return Result.failed(
                    Throwable(
                        App.getContext().resources.getString(R.string.msg_account_has_been_existed)
                    )
                )
            }
        }
    }

    override fun register(
        username: String,
        password: String,
        selectedPhotoUri: Uri,
    ) = flow {
        firebaseAuth.createUserWithEmailAndPassword(username, password).await().run {
            user?.let {
                val profileUrl = uploadImageToFireBaseStorage(selectedPhotoUri)
                val authenticatedUser =
                    User(userId = it.uid, email = it.email)
                if (profileUrl is Result.Success) {
                    authenticatedUser.profileUrl = profileUrl.data
                    val registerResult = createUserInFireStoreIfNotExists(authenticatedUser)
                    if (registerResult is Result.Success) {
                        emit(registerResult)
                    }
                }
            }
        }
    }

    override suspend fun uploadImageToFireBaseStorage(selectedPhotoUri: Uri): Result<String> {
        val filename = UUID.randomUUID().toString()
        val ref = firebaseStorage.getReference("/images/$filename").apply {
            putFile(selectedPhotoUri).await()
        }
        return Result.success(ref.downloadUrl.await().toString())
    }
}

package com.sunasterisk.appchat.db.firebase.client

import android.net.Uri
import com.facebook.AccessToken
import com.google.android.gms.auth.api.signin.GoogleSignInAccount
import com.google.android.gms.common.api.ApiException
import com.google.android.gms.tasks.Task
import com.google.firebase.auth.*
import com.google.firebase.firestore.CollectionReference
import com.google.firebase.firestore.FirebaseFirestore
import com.google.firebase.storage.FirebaseStorage
import com.sunasterisk.appchat.db.Result
import com.sunasterisk.appchat.db.entity.User
import com.sunasterisk.appchat.db.firebase.RemoteConstant.COLLECTION_USER
import com.sunasterisk.appchat.db.firebase.RemoteConstant.EMAIL_NOT_VERIFIED
import com.sunasterisk.appchat.db.firebase.RemoteConstant.MSG_EMAIL_NOT_VERIFIED
import com.sunasterisk.appchat.db.firebase.RemoteConstant.MSG_USER_HAS_BEEN_EXISTED
import com.sunasterisk.appchat.db.firebase.service.AuthService
import kotlinx.coroutines.ExperimentalCoroutinesApi
import kotlinx.coroutines.flow.flow
import kotlinx.coroutines.tasks.await
import java.util.UUID

@ExperimentalCoroutinesApi
class AuthImpl(
    private val firebaseAuth: FirebaseAuth,
    fireStore: FirebaseFirestore,
    private val firebaseStorage: FirebaseStorage,
) : AuthService {

    private val usersRef: CollectionReference = fireStore.collection(COLLECTION_USER)

    override fun logIn(username: String, password: String) =
        flow {
            firebaseAuth.signInWithEmailAndPassword(username, password).await().run {
                if (user?.isEmailVerified == false) {
                    val throwable =
                        FirebaseAuthEmailException(EMAIL_NOT_VERIFIED, MSG_EMAIL_NOT_VERIFIED)
                    error(throwable)
                } else {
                    emit(Result.Success(this))
                }
            }
        }

    override suspend fun firebaseSignInWithCredential(
        googleAuthCredential: AuthCredential
    ): Result<User> {
        firebaseAuth.signInWithCredential(googleAuthCredential).await().run {
            val isNewUser = additionalUserInfo?.isNewUser ?: false
            if (isNewUser) {
                user?.let {
                    val authenticatedUser =
                        User(
                            userId = it.uid,
                            email = it.email,
                            userName = it.displayName,
                            profileUrl = it.photoUrl.toString()
                        )
                    return createUserInFireStoreIfNotExists(authenticatedUser)
                }
            }
            return Result.failed(Throwable(MSG_USER_HAS_BEEN_EXISTED))
        }
    }

    override fun signInWithGoogle(task: Task<GoogleSignInAccount>) =
        flow {
            val account = task.getResult(ApiException::class.java)
            val idToken: String? = account?.idToken
            val credential = GoogleAuthProvider.getCredential(idToken, null)
            val authUser = firebaseSignInWithCredential(credential)
            emit(authUser)
        }

    override fun loginWithFacebook(token: AccessToken) =
        flow {
            val credential = FacebookAuthProvider.getCredential(token.token)
            val authUser = firebaseSignInWithCredential(credential)
            emit(authUser)
        }

    override suspend fun createUserInFireStoreIfNotExists(authenticatedUser: User): Result<User> {
        val uidRef = usersRef.document(authenticatedUser.userId)
        uidRef.get().await().run {
            if (!this.exists()) {
                uidRef.set(authenticatedUser).await().run {
                    return Result.success(authenticatedUser)
                }
            } else {
                return Result.failed(Throwable(MSG_USER_HAS_BEEN_EXISTED))
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
                it.sendEmailVerification()
                val profileUrl = uploadImageToFireBaseStorage(selectedPhotoUri)
                val authenticatedUser =
                    User(
                        userId = it.uid,
                        email = it.email,
                        userName = it.displayName,
                        profileUrl = it.photoUrl.toString()
                    )
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

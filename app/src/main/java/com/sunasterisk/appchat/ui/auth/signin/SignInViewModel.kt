package com.sunasterisk.appchat.ui.auth.signin

import androidx.lifecycle.LiveData
import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.viewModelScope
import com.facebook.AccessToken
import com.facebook.CallbackManager
import com.google.android.gms.auth.api.signin.GoogleSignInAccount
import com.google.android.gms.tasks.Task
import com.sunasterisk.appchat.App
import com.sunasterisk.appchat.R
import com.sunasterisk.appchat.base.BaseViewModel
import com.sunasterisk.appchat.db.Result
import com.sunasterisk.appchat.db.repository.Repository
import kotlinx.coroutines.ExperimentalCoroutinesApi
import kotlinx.coroutines.flow.*

@ExperimentalCoroutinesApi
class SignInViewModel(
    private val authRepository: Repository.AuthRepository,
) : BaseViewModel() {

    val emailLiveData = MutableLiveData<String>()
    val passwordLiveData = MutableLiveData<String>()

    private var signInNavigator: SignInNavigator? = null

    val listenerLogin: (email: String, password: String) -> Unit =
        { email, password -> loginWithEmailAndPassword(email, password) }

    val listenerGoogleSignIn: () -> Unit =
        { signInNavigator?.onGoogleSignInClient() }

    val callbackFacebookManager: CallbackManager = CallbackManager.Factory.create()

    val listenerFacebookLogin: (tokenResult: Result<AccessToken>) -> Unit =
        { tokenResult -> loginWithFacebook(tokenResult) }

    private var _permissionLiveData = MutableLiveData<List<String>>()
    val permissionLiveData: LiveData<List<String>> = _permissionLiveData

    init {
        _permissionLiveData.postValue(
            App.getContext().resources.getStringArray(R.array.facebook_permissions).toList()
        )
    }

    private fun loginWithEmailAndPassword(email: String, password: String) {
        authRepository.logIn(email, password)
            .onStart {
                _isError.value = ""
                _isLoading.value = true
            }
            .onEach { authResult ->
                if (authResult is Result.Success) {
                    signInNavigator?.onSignInSuccess()
                }
            }
            .catch { cause: Throwable ->
                _isError.value = cause.message
            }
            .onCompletion {
                _isLoading.value = false
            }
            .launchIn(viewModelScope)
    }

    fun signInWithGoogle(task: Task<GoogleSignInAccount>) {
        authRepository.signInWithGoogle(task)
            .onStart {
                _isError.postValue("")
                _isLoading.value = true
            }
            .onEach { authResult ->
                if (authResult is Result.Success) {
                    signInNavigator?.onSignInSuccess(authResult.data)
                } else if (authResult is Result.Failed) {
                    signInNavigator?.onSignInSuccess()
                }
            }
            .catch { cause: Throwable ->
                _isError.postValue(cause.message)
            }
            .onCompletion {
                _isLoading.value = false
            }
            .launchIn(viewModelScope)
    }

    private fun loginWithFacebook(tokenResult: Result<AccessToken>) {
        if (tokenResult is Result.Success) {
            authRepository.loginWithFacebook(tokenResult.data)
                .onStart {
                    _isError.postValue("")
                    _isLoading.value = true
                }
                .onEach { authResult ->
                    if (authResult is Result.Success) {
                        signInNavigator?.onSignInSuccess(authResult.data)
                    } else if (authResult is Result.Failed) {
                        signInNavigator?.onSignInSuccess()
                    }
                }
                .catch { cause: Throwable ->
                    _isError.postValue(cause.message)
                }
                .onCompletion {
                    _isLoading.value = false
                }
                .launchIn(viewModelScope)
        } else if (tokenResult is Result.Failed) {
            _isError.postValue(tokenResult.cause.message)
            _isLoading.value = false
        }
    }

    fun setHomeNavigator(navigator: SignInNavigator) {
        signInNavigator = navigator
    }
}

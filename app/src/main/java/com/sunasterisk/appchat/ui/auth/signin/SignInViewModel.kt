package com.sunasterisk.appchat.ui.auth.signin

import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.viewModelScope
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

    private fun loginWithEmailAndPassword(email: String, password: String) {
        _isError.value = ""
        _isLoading.value = true
        authRepository.logIn(email, password)
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

    fun setHomeNavigator(navigator: SignInNavigator) {
        signInNavigator = navigator
    }
}

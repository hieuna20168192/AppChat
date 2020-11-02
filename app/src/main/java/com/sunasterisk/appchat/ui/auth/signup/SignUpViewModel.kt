package com.sunasterisk.appchat.ui.auth.signup

import android.net.Uri
import androidx.lifecycle.LiveData
import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.viewModelScope
import com.sunasterisk.appchat.App
import com.sunasterisk.appchat.R
import com.sunasterisk.appchat.base.BaseViewModel
import com.sunasterisk.appchat.db.Result
import com.sunasterisk.appchat.db.repository.Repository
import kotlinx.coroutines.ExperimentalCoroutinesApi
import kotlinx.coroutines.flow.catch
import kotlinx.coroutines.flow.launchIn
import kotlinx.coroutines.flow.onCompletion
import kotlinx.coroutines.flow.onEach

@ExperimentalCoroutinesApi
class SignUpViewModel(
    private val authRepository: Repository.AuthRepository
) : BaseViewModel() {
    val emailLiveData = MutableLiveData<String>()
    val passwordLiveData = MutableLiveData<String>()
    val confirmPasswordLiveData = MutableLiveData<String>()

    private var _imageProfileLiveData = MutableLiveData<Uri>()
    val imageProfileLiveData: LiveData<Uri> = _imageProfileLiveData

    private var signUpNavigator: SignUpNavigator? = null

    val listenerPick: () -> Unit = {
        signUpNavigator?.onPickPhoto()
    }

    val listenerRegister: (email: String, password: String) -> Unit = { email, password ->
        register(email, password)
    }

    fun updateImageProfile(imageUri: Uri?) {
        _imageProfileLiveData.postValue(imageUri)
    }

    fun setSignUpNavigator(navigator: SignUpNavigator) {
        signUpNavigator = navigator
    }

    private fun register(email: String, password: String) {
        _isLoading.value = true
        _isError.postValue("")
        val imageUri = imageProfileLiveData.value
            ?: Uri.parse(
                "android.resource://" + App.getContext().packageName
                        + "/" + R.drawable.ic_arrow_back_24
            )
        authRepository.register(email, password, imageUri)
            .onEach { authResult ->
                if (authResult is Result.Success) {
                    signUpNavigator?.onRegisterSuccess(authResult.data)
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
}

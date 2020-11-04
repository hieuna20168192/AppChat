package com.sunasterisk.appchat.di

import com.sunasterisk.appchat.ui.auth.signin.SignInViewModel
import com.sunasterisk.appchat.ui.auth.signup.SignUpViewModel
import com.sunasterisk.appchat.ui.chat.ChatViewModel
import kotlinx.coroutines.ExperimentalCoroutinesApi
import org.koin.android.viewmodel.dsl.viewModel
import org.koin.dsl.module

@ExperimentalCoroutinesApi
val viewModelModule = module {
    viewModel { SignInViewModel(get()) }
    viewModel { SignUpViewModel(get()) }
    viewModel { ChatViewModel(get()) }
}

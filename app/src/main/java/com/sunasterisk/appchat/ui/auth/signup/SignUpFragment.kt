package com.sunasterisk.appchat.ui.auth.signup

import android.app.Activity
import android.content.Intent
import androidx.navigation.Navigation
import androidx.navigation.fragment.findNavController
import androidx.navigation.navOptions
import com.sunasterisk.appchat.R
import com.sunasterisk.appchat.base.BaseBindingFragment
import com.sunasterisk.appchat.databinding.FragmentSignUpBinding
import com.sunasterisk.appchat.db.entity.User
import kotlinx.android.synthetic.main.fragment_sign_up.*
import kotlinx.coroutines.ExperimentalCoroutinesApi
import org.koin.android.viewmodel.ext.android.viewModel

@ExperimentalCoroutinesApi
class SignUpFragment : BaseBindingFragment<FragmentSignUpBinding>(),
    SignUpNavigator {

    override val layoutResource = R.layout.fragment_sign_up

    private val signUpViewModel: SignUpViewModel by viewModel()

    override fun initComponents() {
        binding.lifecycleOwner = this
        binding.signUpViewModel = signUpViewModel
        signUpViewModel.setSignUpNavigator(this)
    }

    override fun initListeners() {
        textViewSignInRoute.setOnClickListener(
            Navigation.createNavigateOnClickListener(R.id.actionToSignInFragment, null)
        )
        toolbarSignUp.setNavigationOnClickListener(
            Navigation.createNavigateOnClickListener(R.id.actionToSignInFragment, null)
        )
    }

    override fun onActivityResult(requestCode: Int, resultCode: Int, data: Intent?) {
        super.onActivityResult(requestCode, resultCode, data)
        if (requestCode == RC_PICK && resultCode == Activity.RESULT_OK
            && data != null
        ) {
            val selectedPhotoUri = data.data
            signUpViewModel.updateImageProfile(selectedPhotoUri)
        }
    }

    companion object {
        const val RC_PICK = 99
    }

    override fun onRegisterSuccess(user: User?) {
        val options = navOptions {
            anim {
                enter = R.anim.fade_in
                exit = R.anim.fade_out
            }
        }
        findNavController().navigate(R.id.chatFragment, null, options)
    }

    override fun onPickPhoto() {
        val intent = Intent(Intent.ACTION_PICK)
        intent.type = "image/*"
        startActivityForResult(intent, RC_PICK)
    }
}

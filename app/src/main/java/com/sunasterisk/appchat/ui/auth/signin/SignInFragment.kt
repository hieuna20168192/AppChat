package com.sunasterisk.appchat.ui.auth.signin

import androidx.navigation.Navigation
import androidx.navigation.fragment.findNavController
import androidx.navigation.navOptions
import com.sunasterisk.appchat.R
import com.sunasterisk.appchat.base.BaseBindingFragment
import com.sunasterisk.appchat.databinding.FragmentSignInBinding
import com.sunasterisk.appchat.db.entity.User
import kotlinx.android.synthetic.main.fragment_sign_in.*
import kotlinx.coroutines.ExperimentalCoroutinesApi
import org.koin.android.viewmodel.ext.android.viewModel

@ExperimentalCoroutinesApi
class SignInFragment : BaseBindingFragment<FragmentSignInBinding>(), SignInNavigator {

    override val layoutResource = R.layout.fragment_sign_in
    private val viewModel: SignInViewModel by viewModel()

    override fun initComponents() {
        binding.signInViewModel = viewModel
        binding.lifecycleOwner = this
        viewModel.setHomeNavigator(this)
    }

    override fun initListeners() {
        textViewSignUpRoute.setOnClickListener(
            Navigation.createNavigateOnClickListener(R.id.actionToSignUpFragment, null)
        )
    }

    override fun onSignInSuccess(user: User?) {
        val options = navOptions {
            anim {
                enter = R.anim.fade_in
                exit = R.anim.fade_out
            }
        }
        findNavController().navigate(R.id.chatFragment, null, options)
    }
}

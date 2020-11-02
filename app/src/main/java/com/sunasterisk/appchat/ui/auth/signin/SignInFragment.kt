package com.sunasterisk.appchat.ui.auth.signin

import android.content.Intent
import androidx.navigation.Navigation
import androidx.navigation.fragment.findNavController
import androidx.navigation.navOptions
import com.google.android.gms.auth.api.signin.GoogleSignIn
import com.google.android.gms.auth.api.signin.GoogleSignInClient
import com.google.android.gms.auth.api.signin.GoogleSignInOptions
import com.sunasterisk.appchat.App
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

    private lateinit var googleSignInClient: GoogleSignInClient
    private val gso = GoogleSignInOptions.Builder(GoogleSignInOptions.DEFAULT_SIGN_IN)
        .requestIdToken(App.getContext().resources.getString(R.string.default_web_client_id))
        .requestEmail()
        .build()

    override fun initComponents() {
        binding.signInViewModel = viewModel
        binding.lifecycleOwner = this
        viewModel.setHomeNavigator(this)
        googleSignInClient = GoogleSignIn.getClient(requireActivity(), gso)
        loginButtonFacebook.fragment = this
    }

    override fun initListeners() {
        textViewSignUpRoute.setOnClickListener(
            Navigation.createNavigateOnClickListener(R.id.actionToSignUpFragment, null)
        )
    }

    override fun onActivityResult(requestCode: Int, resultCode: Int, data: Intent?) {
        viewModel.callbackFacebookManager.onActivityResult(requestCode, resultCode, data)
        super.onActivityResult(requestCode, resultCode, data)

        if (requestCode == RC_SIGN_IN) {
            val task = GoogleSignIn.getSignedInAccountFromIntent(data)
            viewModel.signInWithGoogle(task)
        }
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

    override fun onGoogleSignInClient() {
        val signInIntent = googleSignInClient.signInIntent
        startActivityForResult(signInIntent, RC_SIGN_IN)
    }

    companion object {
        const val RC_SIGN_IN = 100
    }
}

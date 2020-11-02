package com.sunasterisk.appchat.ui

import android.view.View
import androidx.navigation.findNavController
import androidx.navigation.ui.setupWithNavController
import com.sunasterisk.appchat.R
import com.sunasterisk.appchat.base.BaseActivity
import kotlinx.android.synthetic.main.activity_main.*

class MainActivity : BaseActivity() {

    override val layoutResource get() = R.layout.activity_main

    private val navController by lazy { findNavController(R.id.fragmentNavHost) }

    override fun initComponents() {
        setupBottomNavView()
    }

    override fun initListeners() {
        navController.addOnDestinationChangedListener { _, destination, _ ->
            if (destination.id == R.id.signInFragment ||
                destination.id == R.id.signUpFragment
            ) {
                bottomNavView.visibility = View.GONE
            } else {
                bottomNavView.visibility = View.VISIBLE
            }
        }
    }

    private fun setupBottomNavView() {
        bottomNavView?.setupWithNavController(navController)
    }
}

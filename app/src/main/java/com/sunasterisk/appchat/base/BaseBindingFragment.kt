package com.sunasterisk.appchat.base

import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.databinding.ViewDataBinding
import com.sunasterisk.appchat.utils.binding

abstract class BaseBindingFragment<T : ViewDataBinding> : BaseFragment() {

    protected lateinit var binding: T

    override fun onCreateView(
        inflater: LayoutInflater,
        container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {
        binding = container.binding(inflater, layoutResource)
        return binding.root
    }
}

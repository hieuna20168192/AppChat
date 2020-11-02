package com.sunasterisk.appchat.utils

import android.view.LayoutInflater
import android.view.ViewGroup
import androidx.annotation.LayoutRes
import androidx.databinding.DataBindingUtil
import androidx.databinding.ViewDataBinding

fun <T : ViewDataBinding> ViewGroup?.binding(
    inflater: LayoutInflater,
    @LayoutRes resId: Int,
): T = DataBindingUtil.inflate(
    inflater,
    resId,
    this,
    false,
)

package com.sunasterisk.appchat.base

import android.os.Bundle
import androidx.annotation.LayoutRes
import androidx.appcompat.app.AppCompatActivity
import androidx.databinding.DataBindingUtil
import androidx.databinding.ViewDataBinding

abstract class BaseActivity : AppCompatActivity(),
    ObservableComponent {

    @get: LayoutRes
    protected abstract val layoutResource: Int

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        initComponents()
        initListeners()
        onObserveLiveData()
    }

    protected inline fun <reified T : ViewDataBinding> binding(
        @LayoutRes resId: Int
    ): Lazy<T> = lazy {
        DataBindingUtil.setContentView<T>(this, resId)
    }

    protected abstract fun initComponents()

    protected abstract fun initListeners()
}

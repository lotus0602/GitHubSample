package com.n.githubsample.base

import android.os.Bundle
import androidx.appcompat.app.AppCompatActivity
import androidx.databinding.DataBindingUtil
import androidx.databinding.ViewDataBinding

abstract class BaseActivity<V : ViewDataBinding> : AppCompatActivity() {

    protected abstract val layoutResID: Int
    protected lateinit var binding: V

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        binding = createBinding(layoutResID)
    }

    open fun createBinding(layoutResID: Int): V =
        DataBindingUtil.setContentView<V>(this, layoutResID)
            .apply { lifecycleOwner = this@BaseActivity }
}
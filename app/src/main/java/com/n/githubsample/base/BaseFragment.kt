package com.n.githubsample.base

import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.databinding.DataBindingUtil
import androidx.databinding.ViewDataBinding
import androidx.fragment.app.Fragment

abstract class BaseFragment<V : ViewDataBinding> : Fragment() {

    protected abstract val layoutResID: Int
    protected lateinit var binding: V

    override fun onCreateView(
        inflater: LayoutInflater,
        container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? = createBinding(inflater, container, layoutResID)

    open fun createBinding(
        inflater: LayoutInflater,
        container: ViewGroup?,
        layoutResID: Int
    ): View {
        binding = DataBindingUtil.inflate(inflater, layoutResID, container, false)
        binding.lifecycleOwner = this
        return binding.root
    }
}
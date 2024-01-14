package com.n.githubsample.ui.home

import android.os.Bundle
import android.view.View
import com.n.githubsample.R
import com.n.githubsample.base.BaseFragment
import com.n.githubsample.databinding.FragmentHomeBinding
import com.n.githubsample.utils.addMenu

class HomeFragment : BaseFragment<FragmentHomeBinding>() {

    override val layoutResID: Int = R.layout.fragment_home
    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        addMenu(R.menu.nav_menu_home)
    }
}
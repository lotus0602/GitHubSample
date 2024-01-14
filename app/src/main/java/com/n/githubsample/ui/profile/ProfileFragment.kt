package com.n.githubsample.ui.profile

import android.os.Bundle
import android.util.Log
import android.view.View
import androidx.fragment.app.activityViewModels
import androidx.fragment.app.viewModels
import com.n.githubsample.R
import com.n.githubsample.base.BaseFragment
import com.n.githubsample.databinding.FragmentProfileBinding
import com.n.githubsample.ui.MainVM
import com.n.githubsample.ui.MyPopularRepoAdapter
import com.n.githubsample.utils.addMenu
import dagger.hilt.android.AndroidEntryPoint

@AndroidEntryPoint
class ProfileFragment : BaseFragment<FragmentProfileBinding>() {
    private val activityVM: MainVM by activityViewModels()
    private val profileVM: ProfileVM by viewModels()

    private lateinit var myPopularRepoAdapter: MyPopularRepoAdapter

    override val layoutResID: Int = R.layout.fragment_profile
    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        binding.viewModel = profileVM

        init()
        addMenu(R.menu.nav_menu_profile)
    }

    private fun init() {
        myPopularRepoAdapter = MyPopularRepoAdapter()
        binding.popularList.apply {
            adapter = myPopularRepoAdapter
        }

        profileVM.user.observe(viewLifecycleOwner) {
            Log.d("TAG", "init, $it")
        }

        profileVM.repoList.observe(viewLifecycleOwner) { list ->
            myPopularRepoAdapter.submitList(list)
            Log.d("TAG", "init: ${list.size}")
        }

        profileVM.fetchData()
    }
}
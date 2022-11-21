package com.n.githubsample.ui.home

import android.os.Bundle
import android.view.View
import androidx.fragment.app.activityViewModels
import androidx.navigation.fragment.findNavController
import com.n.githubsample.R
import com.n.githubsample.base.BaseFragment
import com.n.githubsample.databinding.FragmentHomeBinding
import com.n.githubsample.ui.MainVM
import kotlinx.coroutines.CoroutineScope
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.flow.first
import kotlinx.coroutines.launch

class HomeFragment : BaseFragment<FragmentHomeBinding>() {
    private val activityVM: MainVM by activityViewModels()

    override val layoutResID: Int = R.layout.fragment_home
    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        init()
    }

    private fun init() {
        CoroutineScope(Dispatchers.Main).launch {
            binding.progress.show()

            if (!activityVM.hasAccessToken.first())
                findNavController().navigate(R.id.action_homeFragment_to_loginFragment)

            binding.progress.hide()
        }
    }
}
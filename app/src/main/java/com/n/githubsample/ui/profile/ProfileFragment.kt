package com.n.githubsample.ui.profile

import android.os.Bundle
import android.util.Log
import android.view.View
import androidx.fragment.app.activityViewModels
import androidx.fragment.app.viewModels
import androidx.lifecycle.lifecycleScope
import androidx.navigation.fragment.findNavController
import com.n.githubsample.R
import com.n.githubsample.base.BaseFragment
import com.n.githubsample.databinding.FragmentProfileBinding
import com.n.githubsample.ui.MainVM
import dagger.hilt.android.AndroidEntryPoint
import kotlinx.coroutines.flow.first

@AndroidEntryPoint
class ProfileFragment : BaseFragment<FragmentProfileBinding>() {
    private val activityVM: MainVM by activityViewModels()
    private val profileVM: ProfileVM by viewModels()

    override val layoutResID: Int = R.layout.fragment_profile
    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        binding.viewModel = profileVM

        init()
    }

    private fun init() {
        lifecycleScope.launchWhenCreated {
            val hasToken = activityVM.hasAccessToken.first()

            if (!hasToken) findNavController().navigate(R.id.action_homeFragment_to_loginFragment)
            else profileVM.getUser(activityVM.accessToken.first())
        }

//        CoroutineScope(Dispatchers.Main).launch {
//            val hasToken = activityVM.hasAccessToken.first()
//
//            if (!hasToken) findNavController().navigate(R.id.action_homeFragment_to_loginFragment)
//            else profileVM.getUser(activityVM.accessToken.first())
//        }

        profileVM.user.observe(viewLifecycleOwner) {
            Log.d("TAG", "init, $it")
        }
    }
}
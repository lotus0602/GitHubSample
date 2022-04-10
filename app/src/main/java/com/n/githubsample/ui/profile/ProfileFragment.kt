package com.n.githubsample.ui.profile

import android.content.Intent
import android.os.Bundle
import android.view.View
import androidx.activity.result.contract.ActivityResultContracts
import androidx.appcompat.app.AppCompatActivity
import androidx.fragment.app.viewModels
import com.n.githubsample.R
import com.n.githubsample.auth.GitHubAuthActivity
import com.n.githubsample.base.BaseFragment
import com.n.githubsample.databinding.FragmentProfileBinding
import dagger.hilt.android.AndroidEntryPoint

@AndroidEntryPoint
class ProfileFragment : BaseFragment<FragmentProfileBinding>() {
    private val profileVM: ProfileVM by viewModels()

    override val layoutResID: Int = R.layout.fragment_profile
    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        binding.viewModel = profileVM

        binding.btnLogin.setOnClickListener {
            startAuth.launch(Intent(requireActivity(), GitHubAuthActivity::class.java))
        }
    }

    private val startAuth =
        registerForActivityResult(ActivityResultContracts.StartActivityForResult()) { result ->
            if (result.resultCode == AppCompatActivity.RESULT_OK) {
                if (result.data?.hasExtra("token") == true) {
                    val accessToken = result.data?.getStringExtra("token") ?: ""
                    profileVM.getUser(accessToken)
                }
            }
        }
}
package com.n.githubsample.ui.login

import android.content.Intent
import android.os.Bundle
import android.util.Log
import android.view.View
import androidx.activity.result.contract.ActivityResultContracts
import androidx.appcompat.app.AppCompatActivity
import androidx.fragment.app.viewModels
import androidx.navigation.fragment.findNavController
import com.n.githubsample.R
import com.n.githubsample.auth.GitHubAuthActivity
import com.n.githubsample.base.BaseFragment
import com.n.githubsample.databinding.FragmentLoginBinding
import dagger.hilt.android.AndroidEntryPoint

@AndroidEntryPoint
class LoginFragment : BaseFragment<FragmentLoginBinding>() {
    private val loginVM: LoginVM by viewModels()

    override val layoutResID: Int = R.layout.fragment_login
    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        binding.viewModel = loginVM

        initView()
    }

    private fun initView() {
        binding.btnLogin.setOnClickListener { startAuth.launch(Intent(requireActivity(), GitHubAuthActivity::class.java)) }
    }

    private val startAuth =
        registerForActivityResult(ActivityResultContracts.StartActivityForResult()) { result ->
            Log.d("TAG", "result: $result")
            if (result.resultCode == AppCompatActivity.RESULT_OK) {
                if (result.data?.hasExtra("token") == true) {
                    val accessToken = result.data?.getStringExtra("token") ?: ""
                    loginVM.saveAccessToken(accessToken)
                    findNavController().popBackStack(R.id.action_loginFragment_to_profileFragment, false)
                }
            }
        }
}
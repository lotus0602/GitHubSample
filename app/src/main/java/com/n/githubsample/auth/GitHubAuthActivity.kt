package com.n.githubsample.auth

import android.content.Intent
import android.os.Bundle
import android.util.Log
import androidx.activity.result.contract.ActivityResultContracts
import androidx.activity.viewModels
import androidx.core.net.toUri
import androidx.lifecycle.lifecycleScope
import com.n.githubsample.Config
import com.n.githubsample.R
import com.n.githubsample.base.BaseActivity
import com.n.githubsample.databinding.ActivityGithubAuthBinding
import com.n.githubsample.domain.Result
import dagger.hilt.android.AndroidEntryPoint
import kotlinx.coroutines.flow.collect
import kotlinx.coroutines.launch

@AndroidEntryPoint
class GitHubAuthActivity : BaseActivity<ActivityGithubAuthBinding>() {
    private val gitHubAuthVM: GitHubAuthVM by viewModels()

    override val layoutResID = R.layout.activity_github_auth
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        binding.viewModel = gitHubAuthVM

        binding.btnInputCode.setOnClickListener {
            gitHubAuthVM.verificationUri.value?.let { verifyUrl ->
                Log.d(TAG, "onCreate, verifyUrl: $verifyUrl")
                Intent(Intent.ACTION_VIEW, verifyUrl.toUri())
                    .also { authGitHub.launch(it) }
            }
        }

        lifecycleScope.launchWhenCreated {
            gitHubAuthVM.getDeviceCode(Config.Key.GITHUB_CLIENT_ID, scopeList.joinToString(" "))
        }
    }

    private val authGitHub =
        registerForActivityResult(ActivityResultContracts.StartActivityForResult()) { code ->
            Log.d(TAG, "resultCode: $code")
            lifecycleScope.launch {
                gitHubAuthVM.deviceCode.value?.let { deviceCode ->
                    gitHubAuthVM.getAccessToken(Config.Key.GITHUB_CLIENT_ID, deviceCode)
                        .collect { result ->
                            Log.d(TAG, "result: $result")
                            when (result) {
                                is Result.Success -> {
                                    val intent = Intent().apply { putExtra("token", result.data) }
                                    setResult(RESULT_OK, intent)
                                    finish()
                                }
                                is Result.Error -> Log.d(TAG, "getAccessToken, ${result.message}")
                                Result.Loading -> Unit
                            }
                        }
                }
            }
        }

    companion object {
        private val TAG = GitHubAuthActivity::class.java.simpleName

        // @see: https://docs.github.com/en/developers/apps/building-oauth-apps/scopes-for-oauth-apps#available-scopes
        val scopeList = arrayOf("repo", "user")
    }
}
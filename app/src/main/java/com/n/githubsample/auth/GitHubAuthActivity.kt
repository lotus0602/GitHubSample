package com.n.githubsample.auth

import android.content.Intent
import android.os.Bundle
import android.view.View
import androidx.activity.viewModels
import androidx.core.net.toUri
import androidx.lifecycle.Lifecycle
import androidx.lifecycle.flowWithLifecycle
import androidx.lifecycle.lifecycleScope
import com.n.githubsample.R
import com.n.githubsample.base.BaseActivity
import com.n.githubsample.databinding.ActivityGithubAuthBinding
import com.n.githubsample.utils.showToast
import dagger.hilt.android.AndroidEntryPoint
import kotlinx.coroutines.flow.catch
import kotlinx.coroutines.flow.launchIn
import kotlinx.coroutines.flow.onEach

@AndroidEntryPoint
class GitHubAuthActivity : BaseActivity<ActivityGithubAuthBinding>() {
    private val gitHubAuthVM: GitHubAuthVM by viewModels()

    override val layoutResID = R.layout.activity_github_auth
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        binding.viewModel = gitHubAuthVM

        initView()
        observeData()
    }

    private fun initView() {
        binding.btnInputCode.setOnClickListener {
            val url = gitHubAuthVM.getVerificationUri()

            if (url.isEmpty()) {
                showToast(R.string.error_auth_invalid_verification_url)
                return@setOnClickListener
            }

            val browsIntent = Intent.makeMainSelectorActivity(
                Intent.ACTION_MAIN,
                Intent.CATEGORY_APP_BROWSER
            ).apply { data = url.toUri() }

            try {
                val resolve = browsIntent.resolveActivity(packageManager)
                if (resolve != null) {
                    startActivity(browsIntent)
                } else {
                    showToast(R.string.error_no_activity_to_handle_intent)
                }
            } catch (e: Exception) {
                e.printStackTrace()
            }
        }

        binding.btnComplete.setOnClickListener {
            gitHubAuthVM.getAccessToken()
        }
    }

    private fun observeData() {
        gitHubAuthVM.event
            .flowWithLifecycle(lifecycle, Lifecycle.State.STARTED)
            .onEach { showToast(it) }
            .launchIn(lifecycleScope)

        gitHubAuthVM.deviceCodeState
            .flowWithLifecycle(lifecycle, Lifecycle.State.STARTED)
            .onEach { state ->
                when (state) {
                    GitHubAuthVM.DeviceCodeState.None -> {
                        binding.btnInputCode.visibility = View.GONE
                        binding.btnComplete.visibility = View.GONE
                    }

                    GitHubAuthVM.DeviceCodeState.Loading -> {}
                    GitHubAuthVM.DeviceCodeState.Error -> {}

                    is GitHubAuthVM.DeviceCodeState.Success -> {
                        binding.btnInputCode.visibility = View.VISIBLE
                        binding.btnComplete.visibility = View.VISIBLE
                    }
                }
            }.launchIn(lifecycleScope)

        gitHubAuthVM.accessTokenState
            .flowWithLifecycle(lifecycle, Lifecycle.State.STARTED)
            .catch { it.printStackTrace() }
            .onEach { state ->
                when (state) {
                    GitHubAuthVM.AccessTokenState.None -> {}
                    GitHubAuthVM.AccessTokenState.Loading -> {}
                    GitHubAuthVM.AccessTokenState.Error -> {}

                    is GitHubAuthVM.AccessTokenState.Success -> {
                        val intent = Intent().apply { putExtra("token", state.accessToken) }
                        setResult(RESULT_OK, intent)
                        finish()
                    }
                }
            }.launchIn(lifecycleScope)
    }

    companion object {
        private val TAG = GitHubAuthActivity::class.java.simpleName
    }
}
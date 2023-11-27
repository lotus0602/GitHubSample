package com.n.githubsample.auth

import android.content.Intent
import android.os.Bundle
import androidx.activity.ComponentActivity
import androidx.activity.compose.setContent
import androidx.activity.viewModels
import androidx.lifecycle.Lifecycle
import androidx.lifecycle.flowWithLifecycle
import androidx.lifecycle.lifecycleScope
import com.n.githubsample.utils.showToast
import dagger.hilt.android.AndroidEntryPoint
import kotlinx.coroutines.flow.catch
import kotlinx.coroutines.flow.launchIn
import kotlinx.coroutines.flow.onEach

@AndroidEntryPoint
class GitHubAuthActivity : ComponentActivity() {
    private val gitHubAuthVM: GitHubAuthVM by viewModels()

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)

        setContent {
            AuthScreen()
        }

        observeData()
    }

    private fun observeData() {
        gitHubAuthVM.event
            .flowWithLifecycle(lifecycle, Lifecycle.State.STARTED)
            .onEach { showToast(it) }
            .launchIn(lifecycleScope)

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
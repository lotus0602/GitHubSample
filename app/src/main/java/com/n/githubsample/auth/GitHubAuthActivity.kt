package com.n.githubsample.auth

import android.content.Intent
import android.os.Bundle
import android.util.Log
import androidx.activity.viewModels
import androidx.core.net.toUri
import androidx.lifecycle.distinctUntilChanged
import com.n.githubsample.R
import com.n.githubsample.base.BaseActivity
import com.n.githubsample.databinding.ActivityGithubAuthBinding
import dagger.hilt.android.AndroidEntryPoint

@AndroidEntryPoint
class GitHubAuthActivity : BaseActivity<ActivityGithubAuthBinding>() {
    private val gitHubAuthVM: GitHubAuthVM by viewModels()

    override val layoutResID = R.layout.activity_github_auth
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        binding.viewModel = gitHubAuthVM

        binding.btnInputCode.setOnClickListener {
            val url = gitHubAuthVM.verificationUri.value
                ?: return@setOnClickListener
            Log.d(TAG, "onCreate, url: $url")
            val browsIntent = Intent.makeMainSelectorActivity(
                Intent.ACTION_MAIN,
                Intent.CATEGORY_APP_BROWSER
            ).apply {
                data = url.toUri()
            }
            try {
                val resolve = browsIntent.resolveActivity(packageManager)
                if (resolve != null) {
                    Log.d(TAG, "onCreate: resolve: ${resolve.className}")
                    startActivity(browsIntent)
                } else {

                }
            } catch (e: Exception) {
                e.printStackTrace()
            }
        }
        binding.btnComplete.setOnClickListener {
            gitHubAuthVM.getAccessToken()
        }

        gitHubAuthVM.accessToken.distinctUntilChanged()
            .observe(this) { token ->
                val intent = Intent().apply { putExtra("token", token) }
                setResult(RESULT_OK, intent)
                finish()
            }

        gitHubAuthVM.getDeviceCode()
    }

    companion object {
        private val TAG = GitHubAuthActivity::class.java.simpleName
    }
}
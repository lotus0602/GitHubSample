package com.n.githubsample.ui.login

import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.compose.ui.platform.ComposeView
import androidx.compose.ui.platform.ViewCompositionStrategy
import androidx.fragment.app.Fragment
import androidx.hilt.navigation.fragment.hiltNavGraphViewModels
import androidx.lifecycle.compose.collectAsStateWithLifecycle
import com.n.githubsample.R
import com.n.githubsample.ui.login.compose.AuthenticationScreen

class AuthenticateFragment : Fragment() {
    private val signInVM: SignInVM by hiltNavGraphViewModels(R.id.nav_auth)

    override fun onCreateView(
        inflater: LayoutInflater,
        container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View = ComposeView(requireContext()).apply {
        setViewCompositionStrategy(ViewCompositionStrategy.DisposeOnViewTreeLifecycleDestroyed)
        setContent {
            val uiState = signInVM.accessTokenUiState.collectAsStateWithLifecycle()

            AuthenticationScreen(
                uiState = uiState.value,
                onClickComplete = signInVM::getAccessToken
            )
        }
    }
}
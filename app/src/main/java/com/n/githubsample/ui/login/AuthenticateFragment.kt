package com.n.githubsample.ui.login

import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.compose.ui.platform.ComposeView
import androidx.compose.ui.platform.ViewCompositionStrategy
import androidx.fragment.app.Fragment
import androidx.hilt.navigation.fragment.hiltNavGraphViewModels
import androidx.lifecycle.Lifecycle
import androidx.lifecycle.compose.collectAsStateWithLifecycle
import androidx.lifecycle.flowWithLifecycle
import androidx.lifecycle.lifecycleScope
import androidx.navigation.fragment.findNavController
import androidx.navigation.navOptions
import com.n.githubsample.R
import com.n.githubsample.ui.login.compose.AuthenticationScreen
import kotlinx.coroutines.flow.launchIn
import kotlinx.coroutines.flow.onEach

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

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        signInVM.accessTokenUiState
            .flowWithLifecycle(lifecycle, Lifecycle.State.STARTED)
            .onEach { state ->
                if (state is AccessTokenUiState.Success) {
                    signInVM.saveAccessToken(state.accessToken)
                    findNavController().navigate(
                        resId = R.id.homeFragment,
                        args = null,
                        navOptions = navOptions { popUpTo(R.id.nav_auth) }
                    )
                }
            }.launchIn(viewLifecycleOwner.lifecycleScope)
    }
}
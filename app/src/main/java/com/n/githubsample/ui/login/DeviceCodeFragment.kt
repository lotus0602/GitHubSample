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
import androidx.navigation.fragment.findNavController
import com.n.githubsample.R
import com.n.githubsample.ui.login.compose.DeviceCodeScreen
import com.n.githubsample.utils.IntentKit

class DeviceCodeFragment : Fragment() {
    private val signInVM: SignInVM by hiltNavGraphViewModels(R.id.nav_auth)

    override fun onCreateView(
        inflater: LayoutInflater,
        container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View = ComposeView(requireContext()).apply {
        setViewCompositionStrategy(ViewCompositionStrategy.DisposeOnViewTreeLifecycleDestroyed)
        setContent {
            val uiState = signInVM.deviceCodeUiState.collectAsStateWithLifecycle()

            DeviceCodeScreen(
                uiState = uiState.value,
                onClickVerification = { uri ->
                    IntentKit.browseIntent(context, uri)
                    findNavController().navigate(R.id.action_deviceCodeFragment_to_authenticateFragment)
                }
            )
        }
    }
}
package com.n.githubsample.ui.login

import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.compose.ui.platform.ComposeView
import androidx.compose.ui.platform.ViewCompositionStrategy
import androidx.fragment.app.Fragment
import androidx.hilt.navigation.fragment.hiltNavGraphViewModels
import androidx.navigation.fragment.findNavController
import com.n.githubsample.R
import com.n.githubsample.ui.login.compose.StartSignInScreen

class StartSignInFragment : Fragment() {
    private val signInVM: SignInVM by hiltNavGraphViewModels(R.id.nav_auth)

    override fun onCreateView(
        inflater: LayoutInflater,
        container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View = ComposeView(requireContext()).apply {
        setViewCompositionStrategy(ViewCompositionStrategy.DisposeOnViewTreeLifecycleDestroyed)
        setContent {
            StartSignInScreen(
                onClickSignIn = {
                    signInVM.getDeviceCode()
                    findNavController().navigate(R.id.action_startSignInFragment_to_deviceCodeFragment)
                })
        }
    }
}
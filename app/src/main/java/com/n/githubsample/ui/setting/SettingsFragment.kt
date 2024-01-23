package com.n.githubsample.ui.setting

import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.compose.ui.platform.ComposeView
import androidx.compose.ui.platform.ViewCompositionStrategy
import androidx.fragment.app.Fragment
import androidx.lifecycle.lifecycleScope
import androidx.navigation.findNavController
import androidx.navigation.navOptions
import com.n.githubsample.R
import com.n.githubsample.core.DataStoreManager
import com.n.githubsample.ui.setting.compose.SettingsScreen
import dagger.hilt.android.AndroidEntryPoint
import kotlinx.coroutines.launch
import javax.inject.Inject

@AndroidEntryPoint
class SettingsFragment : Fragment() {
    @Inject
    lateinit var dataStore: DataStoreManager

    override fun onCreateView(
        inflater: LayoutInflater,
        container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View = ComposeView(requireContext()).apply {
        setViewCompositionStrategy(ViewCompositionStrategy.DisposeOnViewTreeLifecycleDestroyed)
        setContent {
            SettingsScreen(
                onClickSignOut = {
                    lifecycleScope.launch {
                        dataStore.setAccessToken("")
                        findNavController().navigate(
                            resId = R.id.nav_auth,
                            args = null,
                            navOptions = navOptions { popUpTo(R.id.nav_main) }
                        )
                    }
                })
        }
    }
}
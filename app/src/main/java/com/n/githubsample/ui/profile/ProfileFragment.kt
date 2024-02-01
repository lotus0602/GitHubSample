package com.n.githubsample.ui.profile

import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.compose.ui.platform.ComposeView
import androidx.compose.ui.platform.ViewCompositionStrategy
import androidx.fragment.app.Fragment
import androidx.fragment.app.viewModels
import androidx.lifecycle.compose.collectAsStateWithLifecycle
import com.n.githubsample.R
import com.n.githubsample.ui.profile.compose.ProfileScreen
import com.n.githubsample.utils.addMenu
import dagger.hilt.android.AndroidEntryPoint

@AndroidEntryPoint
class ProfileFragment : Fragment() {
    private val profileVM: ProfileVM by viewModels()

    override fun onCreateView(
        inflater: LayoutInflater,
        container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View = ComposeView(requireContext()).apply {
        setViewCompositionStrategy(ViewCompositionStrategy.DisposeOnViewTreeLifecycleDestroyed)
        setContent {
            val user = profileVM.user.collectAsStateWithLifecycle()
            val repositories = profileVM.repoList.collectAsStateWithLifecycle()

            ProfileScreen(
                user = user.value,
                list = repositories.value
            )
        }
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        addMenu(R.menu.nav_menu_profile)

        profileVM.fetchData()
    }
}
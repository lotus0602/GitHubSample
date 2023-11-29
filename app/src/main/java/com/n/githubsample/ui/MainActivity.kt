package com.n.githubsample.ui

import android.os.Bundle
import android.view.MenuItem
import android.view.View
import androidx.activity.viewModels
import androidx.core.splashscreen.SplashScreen.Companion.installSplashScreen
import androidx.navigation.NavController
import androidx.navigation.fragment.NavHostFragment
import androidx.navigation.ui.onNavDestinationSelected
import androidx.navigation.ui.setupWithNavController
import com.n.githubsample.R
import com.n.githubsample.base.BaseActivity
import com.n.githubsample.databinding.ActivityMainBinding
import dagger.hilt.android.AndroidEntryPoint
import kotlinx.coroutines.CoroutineScope
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.flow.first
import kotlinx.coroutines.launch

@AndroidEntryPoint
class MainActivity : BaseActivity<ActivityMainBinding>() {
    private val mainVM: MainVM by viewModels()
    private lateinit var navController: NavController

    override val layoutResID = R.layout.activity_main
    override fun onCreate(savedInstanceState: Bundle?) {
        installSplashScreen()
        super.onCreate(savedInstanceState)

        init()
    }

    override fun onOptionsItemSelected(item: MenuItem): Boolean =
        item.onNavDestinationSelected(navController) || super.onOptionsItemSelected(item)

    private fun init() {
        binding.viewModel = mainVM

        val navHost = supportFragmentManager.findFragmentById(R.id.navHost) as NavHostFragment
        navController = navHost.navController
        val navGraph = navController.navInflater.inflate(R.navigation.nav_main)
        binding.navBottom.setupWithNavController(navController)

        CoroutineScope(Dispatchers.Main).launch {
            if (mainVM.hasAccessToken.first()) {
                binding.navBottom.visibility = View.VISIBLE
                navGraph.setStartDestination(R.id.homeFragment)
            } else {
                binding.navBottom.visibility = View.GONE
                navGraph.setStartDestination(R.id.loginFragment)
            }
            navController.graph = navGraph
        }
    }
}
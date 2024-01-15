package com.n.githubsample.ui

import android.os.Bundle
import android.view.MenuItem
import android.view.View
import androidx.activity.viewModels
import androidx.core.splashscreen.SplashScreen.Companion.installSplashScreen
import androidx.lifecycle.Lifecycle
import androidx.lifecycle.flowWithLifecycle
import androidx.lifecycle.lifecycleScope
import androidx.navigation.NavController
import androidx.navigation.fragment.NavHostFragment
import androidx.navigation.ui.AppBarConfiguration
import androidx.navigation.ui.navigateUp
import androidx.navigation.ui.onNavDestinationSelected
import androidx.navigation.ui.setupActionBarWithNavController
import androidx.navigation.ui.setupWithNavController
import com.n.githubsample.R
import com.n.githubsample.base.BaseActivity
import com.n.githubsample.databinding.ActivityMainBinding
import dagger.hilt.android.AndroidEntryPoint
import kotlinx.coroutines.flow.launchIn
import kotlinx.coroutines.flow.onEach

@AndroidEntryPoint
class MainActivity : BaseActivity<ActivityMainBinding>() {
    private val mainVM: MainVM by viewModels()
    private lateinit var navController: NavController
    private lateinit var appBarConfiguration: AppBarConfiguration

    override val layoutResID = R.layout.activity_main
    override fun onCreate(savedInstanceState: Bundle?) {
        val splashScreen = installSplashScreen()
        super.onCreate(savedInstanceState)

        splashScreen.setKeepOnScreenCondition { mainVM.keepSplash() }

        initView()
        observeData()

        mainVM.initialize()
    }

    override fun onOptionsItemSelected(item: MenuItem): Boolean =
        item.onNavDestinationSelected(navController) || super.onOptionsItemSelected(item)

    override fun onSupportNavigateUp(): Boolean =
        navController.navigateUp(appBarConfiguration) || super.onSupportNavigateUp()

    private fun initView() {
        val navHost = supportFragmentManager.findFragmentById(R.id.navHost) as NavHostFragment
        navController = navHost.navController
        navController.addOnDestinationChangedListener { _, destination, _ ->
            when (destination.id) {
                R.id.startSignInFragment,
                R.id.deviceCodeFragment,
                R.id.authenticateFragment -> binding.navBottom.visibility = View.GONE

                else -> binding.navBottom.visibility = View.VISIBLE
            }
        }
        appBarConfiguration = AppBarConfiguration(setOf(R.id.homeFragment, R.id.profileFragment))
        setupActionBarWithNavController(navController, appBarConfiguration)

        binding.navBottom.setupWithNavController(navController)
    }

    private fun observeData() {
        mainVM.hasAccessToken
            .flowWithLifecycle(lifecycle, Lifecycle.State.STARTED)
            .onEach { hasAccessToken -> initNavGraph(hasAccessToken) }
            .launchIn(lifecycleScope)
    }

    private fun initNavGraph(hasAccessToken: Boolean) {
        val navGraph = navController.navInflater.inflate(R.navigation.nav_main)
        if (hasAccessToken) {
            navController.graph = navGraph
        } else {
            navGraph.setStartDestination(R.id.nav_auth)
            navController.graph = navGraph
        }
    }
}
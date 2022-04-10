package com.n.githubsample.ui

import android.content.Intent
import android.os.Bundle
import androidx.activity.result.contract.ActivityResultContracts
import androidx.activity.viewModels
import androidx.navigation.NavController
import androidx.navigation.fragment.NavHostFragment
import androidx.recyclerview.widget.LinearLayoutManager
import androidx.recyclerview.widget.RecyclerView
import com.n.githubsample.R
import com.n.githubsample.auth.GitHubAuthActivity
import com.n.githubsample.base.BaseActivity
import com.n.githubsample.databinding.ActivityMainBinding
import dagger.hilt.android.AndroidEntryPoint

@AndroidEntryPoint
class MainActivity : BaseActivity<ActivityMainBinding>() {
    private val mainVM: MainVM by viewModels()
    private lateinit var navController: NavController

    private lateinit var adapter: RepoAdapter

    override val layoutResID = R.layout.activity_main
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)

        binding.viewModel = mainVM

        val navHost = supportFragmentManager.findFragmentById(R.id.navHost) as NavHostFragment
        navController = navHost.navController

//        adapter = RepoAdapter()
//        binding.list.apply {
//            adapter = this@MainActivity.adapter
//            layoutManager = LinearLayoutManager(this@MainActivity, RecyclerView.VERTICAL, false)
//        }
//
//        binding.btnLogin.setOnClickListener {
//            startAuth.launch(Intent(this, GitHubAuthActivity::class.java))
//        }

        mainVM.repoList.observe(this) { adapter.submitList(it) }
    }

    private val startAuth =
        registerForActivityResult(ActivityResultContracts.StartActivityForResult()) { result ->
            if (result.resultCode == RESULT_OK) {
                if (result.data?.hasExtra("token") == true) {
                    val accessToken = result.data?.getStringExtra("token") ?: ""
                    mainVM.getUser(accessToken)
                }
            }
        }
}
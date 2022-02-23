package com.n.githubsample.ui

import android.os.Bundle
import androidx.activity.viewModels
import androidx.lifecycle.lifecycleScope
import androidx.recyclerview.widget.LinearLayoutManager
import androidx.recyclerview.widget.RecyclerView
import com.n.githubsample.R
import com.n.githubsample.base.BaseActivity
import com.n.githubsample.databinding.ActivityMainBinding
import dagger.hilt.android.AndroidEntryPoint
import kotlinx.coroutines.launch

@AndroidEntryPoint
class MainActivity : BaseActivity<ActivityMainBinding>() {
    private val mainVM: MainVM by viewModels()
    private lateinit var adapter: RepoAdapter

    override val layoutResID = R.layout.activity_main
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)

        binding.viewModel = mainVM
        adapter = RepoAdapter()
        binding.list.apply {
            adapter = this@MainActivity.adapter
            layoutManager = LinearLayoutManager(this@MainActivity, RecyclerView.VERTICAL, false)
        }

        mainVM.repoList.observe(this) { adapter.submitList(it) }

        lifecycleScope.launch { mainVM.getRepo() }
    }
}
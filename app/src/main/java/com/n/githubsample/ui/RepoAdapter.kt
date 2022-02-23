package com.n.githubsample.ui

import android.view.LayoutInflater
import android.view.ViewGroup
import androidx.databinding.DataBindingUtil
import androidx.recyclerview.widget.DiffUtil
import com.n.githubsample.R
import com.n.githubsample.base.BaseListAdapter
import com.n.githubsample.databinding.ItemRepoBinding
import com.n.githubsample.domain.model.Repo

class RepoAdapter : BaseListAdapter<Repo, ItemRepoBinding>(diffUtil) {

    override fun createBinding(
        inflater: LayoutInflater,
        parent: ViewGroup,
        viewType: Int
    ): ItemRepoBinding = DataBindingUtil.inflate(inflater, R.layout.item_repo, parent, false)

    override fun onBind(binding: ItemRepoBinding, item: Repo, position: Int) {
        binding.item = item
    }

    companion object {
        val diffUtil = object : DiffUtil.ItemCallback<Repo>() {
            override fun areItemsTheSame(oldItem: Repo, newItem: Repo): Boolean =
                oldItem == newItem

            override fun areContentsTheSame(oldItem: Repo, newItem: Repo): Boolean =
                oldItem == newItem
        }
    }
}
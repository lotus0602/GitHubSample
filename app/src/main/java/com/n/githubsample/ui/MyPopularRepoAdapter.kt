package com.n.githubsample.ui

import android.util.Log
import android.view.LayoutInflater
import android.view.ViewGroup
import androidx.databinding.DataBindingUtil
import androidx.recyclerview.widget.DiffUtil
import com.n.githubsample.R
import com.n.githubsample.base.BaseListAdapter
import com.n.githubsample.databinding.ItemMyPopularRepoBinding
import com.n.githubsample.domain.model.MyPopularRepo

class MyPopularRepoAdapter : BaseListAdapter<MyPopularRepo, ItemMyPopularRepoBinding>(diffUtil) {

    override fun createBinding(
        inflater: LayoutInflater,
        parent: ViewGroup,
        viewType: Int
    ): ItemMyPopularRepoBinding =
        DataBindingUtil.inflate(inflater, R.layout.item_my_popular_repo, parent, false)

    override fun onBind(
        binding: ItemMyPopularRepoBinding,
        item: MyPopularRepo,
        position: Int
    ) {
        binding.item = item
    }

    companion object {
        val diffUtil = object : DiffUtil.ItemCallback<MyPopularRepo>() {
            override fun areItemsTheSame(oldItem: MyPopularRepo, newItem: MyPopularRepo): Boolean =
                (oldItem == newItem).also {
                    Log.d("TAG", "areItemsTheSame: $it")
                }

            override fun areContentsTheSame(
                oldItem: MyPopularRepo,
                newItem: MyPopularRepo
            ): Boolean =
                (oldItem == newItem).also {
                    Log.d("TAG", "areContentsTheSame: $it")
                }
        }
    }
}
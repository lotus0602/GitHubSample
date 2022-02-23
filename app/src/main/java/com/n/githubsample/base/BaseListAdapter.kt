package com.n.githubsample.base

import android.view.LayoutInflater
import android.view.ViewGroup
import androidx.databinding.ViewDataBinding
import androidx.recyclerview.widget.DiffUtil
import androidx.recyclerview.widget.ListAdapter

abstract class BaseListAdapter<Item, V : ViewDataBinding>(
    diffUtil: DiffUtil.ItemCallback<Item>
) : ListAdapter<Item, BaseViewHolder<V>>(diffUtil) {

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): BaseViewHolder<V> {
        val binding = createBinding(LayoutInflater.from(parent.context), parent, viewType)
        return BaseViewHolder(binding)
    }

    override fun onBindViewHolder(holder: BaseViewHolder<V>, position: Int) {
        onBind(holder.binding, getItem(position), position)
        holder.binding.root.setOnClickListener {
            clickItem(
                holder.binding,
                getItem(position),
                position
            )
        }
        holder.binding.executePendingBindings()
    }

    abstract fun createBinding(inflater: LayoutInflater, parent: ViewGroup, viewType: Int): V
    abstract fun onBind(binding: V, item: Item, position: Int)

    open fun clickItem(binding: V, item: Item, position: Int) {}
}
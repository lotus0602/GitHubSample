package com.n.githubsample.utils

import android.app.Activity
import android.widget.BaseAdapter
import androidx.fragment.app.Fragment
import androidx.recyclerview.widget.LinearLayoutManager
import androidx.recyclerview.widget.RecyclerView

fun Activity.createLayoutManager(
    orientation: Int,
    reverse: Boolean = false
): RecyclerView.LayoutManager =
    LinearLayoutManager(this, orientation, reverse)

fun Fragment.createLayoutManager(
    orientation: Int,
    reverse: Boolean = false
): RecyclerView.LayoutManager =
    LinearLayoutManager(requireContext(), orientation, reverse)

fun RecyclerView.create(clazz: Class<out RecyclerView.Adapter<*>>): RecyclerView.Adapter<*> {

    adapter = clazz.newInstance()

    return clazz.newInstance()
}
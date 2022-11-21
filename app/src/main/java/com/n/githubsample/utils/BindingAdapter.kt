package com.n.githubsample.utils

import android.widget.ImageView
import androidx.databinding.BindingAdapter
import com.bumptech.glide.Glide

object BindingAdapter {

    @JvmStatic
    @BindingAdapter(
        value = ["imageUrl"],
        requireAll = false
    )
    fun ImageView.bindImageUrl(
        imageUrl: String?
    ) {
        Glide.with(this)
            .load(imageUrl)
            .into(this)
    }
}
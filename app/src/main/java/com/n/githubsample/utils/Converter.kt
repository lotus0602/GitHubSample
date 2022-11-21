package com.n.githubsample.utils

import android.content.Context
import com.n.githubsample.R

object Converter {

    @JvmStatic
    fun toFollowInfo(context: Context, followers: Int, following: Int): String =
        "$followers ${context.getString(R.string.followers)} · $following ${context.getString(R.string.following)}"
}
package com.n.githubsample.utils

import android.content.res.ColorStateList
import android.graphics.drawable.Drawable
import android.graphics.drawable.GradientDrawable
import androidx.annotation.ColorInt

class DrawableFactory {

    companion object {
        @JvmStatic
        fun createRect(
            @ColorInt color: Int,
            radius: Float,
            size: Int
        ): Drawable = GradientDrawable().apply {
            setColor(ColorStateList.valueOf(color))
            cornerRadius = radius
            setSize(size, size)
        }

        @JvmStatic
        fun createMenuIcon(@ColorInt color: Int): Drawable =
            createRect(color = color, radius = 8f, size = 84)
    }
}
package com.n.githubsample.utils

import android.content.Context
import android.util.TypedValue

fun Context.dp2Px(dp: Float): Float =
    TypedValue.applyDimension(TypedValue.COMPLEX_UNIT_DIP, dp, resources.displayMetrics)
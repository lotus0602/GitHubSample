package com.n.githubsample.utils

import android.content.Context
import android.util.TypedValue
import android.widget.Toast
import androidx.annotation.StringRes

fun Context.dp2Px(dp: Float): Float =
    TypedValue.applyDimension(TypedValue.COMPLEX_UNIT_DIP, dp, resources.displayMetrics)

fun Context.showToast(
    @StringRes msgResID: Int,
    duration: Int = Toast.LENGTH_SHORT
) {
    Toast.makeText(this, msgResID, duration).show()
}

fun Context.showToast(
    msg: String,
    duration: Int = Toast.LENGTH_SHORT
) {
    Toast.makeText(this, msg, duration).show()
}
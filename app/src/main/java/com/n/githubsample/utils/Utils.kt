package com.n.githubsample.utils

import android.content.Context
import android.content.Intent
import android.util.TypedValue
import android.view.Menu
import android.view.MenuInflater
import android.view.MenuItem
import android.widget.Toast
import androidx.annotation.MenuRes
import androidx.annotation.StringRes
import androidx.core.net.toUri
import androidx.core.view.MenuHost
import androidx.core.view.MenuProvider
import androidx.fragment.app.Fragment
import com.n.githubsample.R

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

object IntentKit {
    fun browseIntent(context: Context, url: String) {
        if (url.isEmpty()) {
            context.showToast(R.string.error_auth_invalid_verification_url)
            return
        }

        val browsIntent = Intent.makeMainSelectorActivity(
            Intent.ACTION_MAIN,
            Intent.CATEGORY_APP_BROWSER
        ).apply { data = url.toUri() }

        try {
            val resolve = browsIntent.resolveActivity(context.packageManager)
            if (resolve != null) {
                context.startActivity(browsIntent)
            } else {
                context.showToast(R.string.error_no_activity_to_handle_intent)
            }
        } catch (e: Exception) {
            e.printStackTrace()
        }
    }
}

fun Fragment.addMenu(@MenuRes resourceID: Int) {
    val menuHost: MenuHost = requireActivity()
    menuHost.addMenuProvider(object : MenuProvider {
        override fun onCreateMenu(menu: Menu, menuInflater: MenuInflater) {
            menu.clear()
            menuInflater.inflate(resourceID, menu)
        }

        override fun onMenuItemSelected(menuItem: MenuItem): Boolean {
            return true
        }
    }, viewLifecycleOwner)
}
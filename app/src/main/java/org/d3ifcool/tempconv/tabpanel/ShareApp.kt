package org.d3ifcool.tempconv.tabpanel

import android.content.Context
import android.content.Intent
import org.d3ifcool.tempconv.R

fun shareApp(context: Context) {
    val appPackageName = context.packageName
    val shareText = "Check out this awesome app: https://play.google.com/store/apps/details?id=$appPackageName"

    val shareIntent = Intent().apply {
        action = Intent.ACTION_SEND
        putExtra(Intent.EXTRA_TEXT, shareText)
        type = "text/plain"
    }

    // Start the share activity
    context.startActivity(Intent.createChooser(shareIntent, context.getString(R.string.share_app)))
}

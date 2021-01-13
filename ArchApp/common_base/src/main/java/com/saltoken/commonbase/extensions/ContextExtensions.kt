package com.saltoken.commonbase.extensions

import android.annotation.SuppressLint
import android.content.Context
import android.content.Intent
import android.content.res.Configuration
import android.net.Uri


fun Context.checkAppInstalled(pkgName: String): Boolean {
    return try {
        val packageInfo = packageManager.getPackageInfo(pkgName, 0);
        packageInfo != null
    } catch (e: Throwable) {
        false
    }
}

fun Context.uninstallApp(pkgName: String) {
    val uri = Uri.parse("package:$pkgName")
    val intent = Intent(Intent.ACTION_DELETE, uri)
    startActivity(intent)
}

fun Context.isDarkMode(): Boolean {
    return resources
        .configuration.uiMode and Configuration.UI_MODE_NIGHT_MASK == Configuration.UI_MODE_NIGHT_YES
}


@SuppressLint("QueryPermissionsNeeded")
fun deviceCanHandleIntent(context: Context, intent: Intent): Boolean {
    return try {
        context.packageManager.queryIntentActivities(intent, 0).isNotEmpty()
    } catch (e: NullPointerException) {
        false
    }

}
 inline fun Context.openInBrowser(url: String,onNotHaveBrowser: ((String) -> Unit) = { }) {
    val uri = Uri.parse(url)
    val intent = Intent(Intent.ACTION_VIEW, uri)
    if (deviceCanHandleIntent(this, intent)) {
        this.startActivity(intent)
    } else {
        onNotHaveBrowser.invoke(url)
    }
}

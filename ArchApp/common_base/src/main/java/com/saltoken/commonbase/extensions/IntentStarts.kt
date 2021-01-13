package com.saltoken.commonbase.extensions

import android.app.Activity
import android.app.Service
import android.content.ComponentName
import android.content.Context
import android.content.Intent
import android.os.Bundle
import androidx.fragment.app.Fragment

inline fun <reified T : Activity> Fragment.startActivity(): Unit =
    this.startActivity(requireContext().getIntent<T>())

inline fun <reified T : Activity> Fragment.startActivity(flags: Int): Unit =
    this.startActivity(requireContext().getIntent<T>(flags))

inline fun <reified T : Activity> Fragment.startActivity(extras: Bundle): Unit =
    this.startActivity(requireContext().getIntent<T>(extras))

inline fun <reified T : Activity> Fragment.startActivity(flags: Int, extras: Bundle): Unit =
    this.startActivity(requireContext().getIntent<T>(flags, extras))

inline fun <reified T : Activity> Fragment.startActivityForResult(requestCode: Int): Unit =
    this.startActivityForResult(requireContext().getIntent<T>(), requestCode)

inline fun <reified T : Activity> Fragment.startActivityForResult(
    requestCode: Int,
    flags: Int
): Unit =
    this.startActivityForResult(requireContext().getIntent<T>(flags), requestCode)

inline fun <reified T : Activity> Fragment.startActivityForResult(
    extras: Bundle, requestCode: Int
): Unit =
    this.startActivityForResult(requireContext().getIntent<T>(extras), requestCode)

inline fun <reified T : Activity> Fragment.startActivityForResult(
    extras: Bundle, requestCode: Int, flags: Int
): Unit =
    this.startActivityForResult(requireContext().getIntent<T>(flags, extras), requestCode)

inline fun <reified T : Activity> Activity.startActivity(): Unit =
    this.startActivity(getIntent<T>())

inline fun <reified T : Activity> Activity.startActivity(flags: Int): Unit =
    this.startActivity(getIntent<T>(flags))

inline fun <reified T : Activity> Activity.startActivity(extras: Bundle): Unit =
    this.startActivity(getIntent<T>(extras))

inline fun <reified T : Activity> Activity.startActivity(flags: Int, extras: Bundle): Unit =
    this.startActivity(getIntent<T>(flags, extras))

inline fun <reified T : Activity> Activity.startActivityForResult(requestCode: Int): Unit =
    this.startActivityForResult(getIntent<T>(), requestCode)

inline fun <reified T : Activity> Activity.startActivityForResult(
    requestCode: Int,
    flags: Int
): Unit =
    this.startActivityForResult(getIntent<T>(flags), requestCode)

inline fun <reified T : Activity> Activity.startActivityForResult(
    extras: Bundle, requestCode: Int
): Unit =
    this.startActivityForResult(getIntent<T>(extras), requestCode)

inline fun <reified T : Activity> Activity.startActivityForResult(
    extras: Bundle, requestCode: Int, flags: Int
): Unit =
    this.startActivityForResult(getIntent<T>(flags, extras), requestCode)

inline fun <reified T : Activity> Service.startActivity(): Unit =
    this.startActivity(getIntent<T>(Intent.FLAG_ACTIVITY_NEW_TASK))

inline fun <reified T : Activity> Service.startActivity(flags: Int): Unit =
    this.startActivity(getIntent<T>(flags))

inline fun <reified T : Activity> Service.startActivity(extras: Bundle): Unit =
    this.startActivity(getIntent<T>(Intent.FLAG_ACTIVITY_NEW_TASK, extras))

inline fun <reified T : Activity> Service.startActivity(extras: Bundle, flags: Int): Unit =
    this.startActivity(getIntent<T>(flags, extras))

inline fun <reified T : Service> Context.startService(): ComponentName? =
    this.startService(getIntent<T>())

inline fun <reified T : Service> Context.startService(flags: Int): ComponentName? =
    this.startService(getIntent<T>(flags))

inline fun <reified T : Service> Context.startService(extras: Bundle): ComponentName? =
    this.startService(getIntent<T>(extras))

inline fun <reified T : Service> Context.startService(
    extras: Bundle,
    flags: Int
): ComponentName? = this.startService(getIntent<T>(flags, extras))


inline fun <reified T : Context> Context.getIntent(): Intent =
    Intent(this, T::class.java)

inline fun <reified T : Context> Context.getIntent(flags: Int): Intent {
    val intent = getIntent<T>()
    intent.flags = flags
    return intent
}

inline fun <reified T : Context> Context.getIntent(extras: Bundle): Intent =
    getIntent<T>(0, extras)

inline fun <reified T : Context> Context.getIntent(flags: Int, extras: Bundle): Intent {
    val intent = getIntent<T>(flags)
    intent.putExtras(extras)
    return intent
}
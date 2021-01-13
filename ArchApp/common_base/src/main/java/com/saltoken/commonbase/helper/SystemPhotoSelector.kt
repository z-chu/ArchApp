package com.saltoken.commonbase.helper

import android.Manifest
import android.annotation.TargetApi
import android.app.Activity
import android.content.ContentUris
import android.content.Context
import android.content.Intent
import android.content.pm.PackageManager
import android.net.Uri
import android.os.Build
import android.provider.DocumentsContract
import android.provider.MediaStore
import androidx.core.app.ActivityCompat
import androidx.core.content.ContextCompat
import androidx.fragment.app.Fragment
import androidx.fragment.app.FragmentActivity


class SystemPhotoSelector {

    private val requestCode: Int
    private val permissionRequestCode: Int
    private val fragmentActivity: FragmentActivity?
    private val fragment: Fragment?

    var lastSelectedFilePath: String? = null
        private set

    var onSelectedListener: ((filePath: String) -> Unit)? = null
    var onPermissionDeniedListener: ((permission: String) -> Unit)? = null
    var onErrorListener: ((Throwable) -> Unit)? = null

    constructor(
        activity: FragmentActivity,
        requestCode: Int,
        permissionRequestCode: Int?=null,
        onPermissionDenied: ((permission: String) -> Unit)? = null,
        onError: ((Throwable) -> Unit)? = null,
        onSelected: ((filePath: String) -> Unit)? = null
    ) {
        this.requestCode = requestCode
        this.permissionRequestCode = permissionRequestCode ?: PERMISSIONS_REQUEST_CODE
        this.fragmentActivity = activity
        this.fragment = null
        this.onSelectedListener = onSelected
        this.onPermissionDeniedListener = onPermissionDenied
        this.onErrorListener = onError

    }

    constructor(
        fragment: Fragment,
        requestCode: Int,
        permissionRequestCode: Int?=null,
        onPermissionDenied: ((permission: String) -> Unit)? = null,
        onError: ((Throwable) -> Unit)? = null,
        onSelected: ((filePath: String) -> Unit)? = null
    ) {
        this.requestCode = requestCode
        this.permissionRequestCode = permissionRequestCode ?: PERMISSIONS_REQUEST_CODE
        this.fragmentActivity = null
        this.fragment = fragment
        this.onSelectedListener = onSelected
        this.onPermissionDeniedListener = onPermissionDenied
        this.onErrorListener = onError
    }

    private fun requireActivity(): FragmentActivity {
        return fragmentActivity ?: fragment?.requireActivity()
        ?: throw IllegalStateException("Must set up fragmentActivity or fragment.")
    }

    private fun requireContext(): Context {
        return fragmentActivity ?: fragment?.requireContext()
        ?: throw IllegalStateException("Must set up fragmentActivity or fragment.")
    }


    fun selectFromGallery() {
        val activity = requireActivity()
        if (ContextCompat.checkSelfPermission(
                activity,
                Manifest.permission.READ_EXTERNAL_STORAGE
            ) != PackageManager.PERMISSION_GRANTED
        ) {
            ActivityCompat.requestPermissions(
                activity,
                arrayOf(Manifest.permission.READ_EXTERNAL_STORAGE),
                permissionRequestCode
            )
        } else {
            val intent = newIntentOpenGallery()
            if (fragmentActivity != null) {
                fragmentActivity.startActivityForResult(intent, requestCode)
            } else {
                fragment?.startActivityForResult(intent, requestCode)
            }
        }

    }


    fun dispatchRequestPermissionsResult(
        requestCode: Int,
        permissions: Array<out String>,
        grantResults: IntArray
    ) {
        if (requestCode == permissionRequestCode) {
            if (permissions.isNotEmpty() && permissions[0] == Manifest.permission.READ_EXTERNAL_STORAGE &&
                grantResults.isNotEmpty() && grantResults[0] == PackageManager.PERMISSION_GRANTED
            ) {
                val intent = newIntentOpenGallery()
                if (fragmentActivity != null) {
                    fragmentActivity.startActivityForResult(intent, this.requestCode)
                } else {
                    fragment?.startActivityForResult(intent, this.requestCode)
                }
            } else {
                onPermissionDeniedListener?.invoke(Manifest.permission.READ_EXTERNAL_STORAGE)
            }
        }

    }

    fun dispatchActivityResult(requestCode: Int, resultCode: Int, data: Intent?) {
        if (requestCode == this.requestCode && resultCode == Activity.RESULT_OK) {
            data?.data?.let {
                if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.KITKAT) {
                    handleImageOnKitKat(it)
                } else {
                    handleImageBeforeKitKat(it)
                }
            }
        }

    }

    @TargetApi(19)
    private fun handleImageOnKitKat(uri: Uri) {
        var imagePath: String? = null
        if (DocumentsContract.isDocumentUri(requireContext(), uri)) {
            // 如果是document类型的Uri，则通过document id处理
            val docId = DocumentsContract.getDocumentId(uri)
            if ("com.android.providers.media.documents" == uri.authority) {
                val id = docId.split(":".toRegex()).toTypedArray()[1]
                // 解析出数字格式的id
                val selection = MediaStore.Images.Media._ID + "=" + id
                imagePath = getImagePath(MediaStore.Images.Media.EXTERNAL_CONTENT_URI, selection)
            } else if ("com.android.providers.downloads.documents" == uri.authority) {
                val contentUri: Uri = ContentUris.withAppendedId(
                    Uri.parse("content: //downloads/public_downloads"),
                    java.lang.Long.valueOf(docId)
                )
                imagePath = getImagePath(contentUri, null)
            }
        } else if ("content".equals(uri.scheme, ignoreCase = true)) {
            // 如果是content类型的Uri，则使用普通方式处理
            imagePath = getImagePath(uri, null)
        } else if ("file".equals(uri.scheme, ignoreCase = true)) {
            // 如果是file类型的Uri，直接获取图片路径即可
            imagePath = uri.path
        }
        if (imagePath != null) {
            this.lastSelectedFilePath = imagePath
            onSelectedListener?.invoke(imagePath)
        } else {
            onErrorListener?.invoke(NullPointerException("Image file path not found"))
        }
    }

    private fun handleImageBeforeKitKat(uri: Uri) {
        val imagePath = getImagePath(uri, null)
        if (imagePath != null) {
            this.lastSelectedFilePath = imagePath
            onSelectedListener?.invoke(imagePath)
        } else {
            onErrorListener?.invoke(NullPointerException("Image file path not found"))
        }
    }


    private fun getImagePath(uri: Uri, selection: String?): String? {
        requireContext()
            .contentResolver
            .query(uri, null, selection, null, null)
            ?.use {
                if (it.moveToFirst()) {
                    return it.getString(it.getColumnIndex(MediaStore.Images.Media.DATA))
                }
            }
        return null
    }


    companion object {

        private const val PERMISSIONS_REQUEST_CODE = 43

        fun newIntentOpenGallery(): Intent {
            val intent: Intent
            if (Build.VERSION.SDK_INT < Build.VERSION_CODES.KITKAT) {
                intent = Intent(Intent.ACTION_GET_CONTENT)
                intent.type = "image/*"
            } else {
                intent = Intent(Intent.ACTION_PICK, MediaStore.Images.Media.EXTERNAL_CONTENT_URI)
            }
            return intent
        }
    }


}
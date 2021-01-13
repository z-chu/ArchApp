package com.saltoken.commonbase.extensions

import android.content.Context
import android.content.Intent
import android.net.Uri
import android.os.Build
import android.provider.MediaStore
import androidx.core.content.FileProvider
import java.io.File


fun newIntentTakePhoto(authority: String, context: Context, outputFile: File): Intent {
    val intent = Intent(MediaStore.ACTION_IMAGE_CAPTURE)
    if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.N) {
        intent.putExtra(
            MediaStore.EXTRA_OUTPUT,
            FileProvider
                .getUriForFile(context, authority, outputFile)
        )
    } else {
        intent.putExtra(MediaStore.EXTRA_OUTPUT, Uri.fromFile(outputFile))
    }
    return intent
}

fun newIntentOpenAlbum(): Intent {
    val intent: Intent
    if (android.os.Build.VERSION.SDK_INT < Build.VERSION_CODES.KITKAT) {
        intent = Intent(Intent.ACTION_GET_CONTENT)
        intent.type = "image/*"
    } else {
        intent = Intent(Intent.ACTION_PICK, android.provider.MediaStore.Images.Media.EXTERNAL_CONTENT_URI)
    }
    return intent
}



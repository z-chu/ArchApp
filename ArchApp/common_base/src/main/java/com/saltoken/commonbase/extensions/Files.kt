package com.saltoken.commonbase.extensions

import android.content.Context
import java.io.File
import java.util.*

fun generatePhotoFile(context: Context): File? {
    val externalCacheDir = context.externalCacheDir ?: return null
    val fileDir = File("${externalCacheDir.path}/photo")
    if (!fileDir.exists() && !fileDir.mkdirs()) {
        return null
    }
    val imagePath =
        externalCacheDir.path + "/photo" + File.separator + System.currentTimeMillis() + "Photo.jpg"
    return File(imagePath)
}


fun File.checkSize(size: Int, unit: String = "K"): Boolean {
    val len = length()
    val fileSize = when {
        unit.toUpperCase(Locale.ENGLISH) == "B" -> {
            len.toDouble()
        }
        unit.toUpperCase(Locale.ENGLISH) == "K" -> {
            len.toDouble() / 1024
        }
        unit.toUpperCase(Locale.ENGLISH) == "M" -> {
            len.toDouble() / 1048576
        }
        unit.toUpperCase(Locale.ENGLISH) == "G" -> {
            len.toDouble() / 1073741824
        }
        else -> {
            len.toDouble()
        }
    }
    return fileSize < size
}
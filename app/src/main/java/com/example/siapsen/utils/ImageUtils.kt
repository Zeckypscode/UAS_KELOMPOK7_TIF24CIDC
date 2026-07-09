package com.example.siapsen.utils

import android.content.Context
import android.net.Uri
import androidx.core.content.FileProvider
import java.io.File
import java.text.SimpleDateFormat
import java.util.Date
import java.util.Locale

/**
 * Utility untuk fitur "Kamera": membuat file gambar sementara di cache
 * lalu membungkusnya dengan FileProvider agar dapat dipakai sebagai
 * tujuan hasil foto dari Intent ACTION_IMAGE_CAPTURE.
 */
object ImageUtils {

    fun createImageFile(context: Context): File {
        val timeStamp = SimpleDateFormat("yyyyMMdd_HHmmss", Locale.getDefault()).format(Date())
        val imagesDir = File(context.cacheDir, "images").apply { mkdirs() }
        return File(imagesDir, "SIAPSEN_${timeStamp}.jpg")
    }

    fun getUriForFile(context: Context, file: File): Uri {
        return FileProvider.getUriForFile(
            context,
            "${context.packageName}.fileprovider",
            file
        )
    }
}

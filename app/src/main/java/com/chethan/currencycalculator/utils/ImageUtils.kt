package com.example.tipjar.utils

import android.content.Context
import android.graphics.Bitmap
import android.graphics.BitmapFactory
import android.net.Uri
import android.os.Environment
import androidx.core.content.FileProvider
import java.io.File
import java.text.SimpleDateFormat
import java.util.*

object ImageUtils {

    fun readBitmap(filePath: String): Bitmap? {
        val imgFile = File(filePath)
        if (imgFile.exists())
            return BitmapFactory.decodeFile(imgFile.absolutePath)
        return null
    }

    fun createImageFile(context: Context): ImageDetails {
        // Create an image file name
        val timeStamp: String = SimpleDateFormat("yyyyMMdd_HHmmss", Locale.US).format(Date())
        val storageDir: File? = context.getExternalFilesDir(Environment.DIRECTORY_PICTURES)
        val file = File.createTempFile(
            "JPEG_${timeStamp}_", /* prefix */
            ".jpg", /* suffix */
            storageDir /* directory */
        )

        val uri = FileProvider.getUriForFile(
            context,
            context.applicationContext.packageName + "",
            file
        )

        return ImageDetails(file.absolutePath, uri)
    }
}

data class ImageDetails(val imageAbsolutePath: String, val uri: Uri)

package capps.foodapp.domain.helper

import android.content.Context
import android.net.Uri
import android.provider.MediaStore
import androidx.core.content.FileProvider
import java.io.File

fun getImageFilePath(context: Context, uri: Uri): String? {
    var filePath: String? = null

    // Checking if the URI uses the "content" scheme
    if (uri.scheme == "content") {
        val projection = arrayOf(MediaStore.Images.Media.DATA)
        context.contentResolver.query(uri, projection, null, null, null)?.use { cursor ->
            if (cursor.moveToFirst()) {
                val columnIndex = cursor.getColumnIndexOrThrow(MediaStore.Images.Media.DATA)
                filePath = cursor.getString(columnIndex)
            }
        }
    }

    // Or maybe if the URI uses the "file" scheme
    else if (uri.scheme == "file") {
        filePath = uri.path
    }

    return filePath
}

fun createImageFile(context: Context): Uri {

    //For Preview to be Built well, Uri.Empty is returned.
    try {
        Class.forName("androidx.compose.ui.tooling.preview.Preview")
        return Uri.EMPTY
    } catch (e: ClassNotFoundException) {
        //Ignore since it is not a preview
    }


    val file = File(context.cacheDir, "captured_image_${System.currentTimeMillis()}.jpg")
    return FileProvider.getUriForFile(context, "${context.packageName}.provider", file)
}
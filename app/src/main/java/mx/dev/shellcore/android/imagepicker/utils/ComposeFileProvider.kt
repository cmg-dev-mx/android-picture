package mx.dev.shellcore.android.imagepicker.utils

import android.content.Context
import android.net.Uri
import androidx.core.content.FileProvider
import mx.dev.shellcore.android.imagepicker.R
import java.io.File

class ComposeFileProvider : FileProvider(R.xml.paths) {

    companion object {
        fun getImageUri(context: Context): Uri {
            val directory = File(context.cacheDir, "images")
            directory.mkdirs()

            val file = File.createTempFile("selected_image_", ".jpg", directory)

            val authority = context.packageName + ".fileprovider"

            return getUriForFile(
                context,
                authority,
                file
            )
        }
    }
}
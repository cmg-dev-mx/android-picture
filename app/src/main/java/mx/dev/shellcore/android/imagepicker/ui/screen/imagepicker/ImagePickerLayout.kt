package mx.dev.shellcore.android.imagepicker.ui.screen.imagepicker

import android.net.Uri
import androidx.activity.compose.rememberLauncherForActivityResult
import androidx.activity.result.contract.ActivityResultContracts
import androidx.compose.foundation.background
import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.padding
import androidx.compose.material3.Button
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.remember
import androidx.compose.runtime.setValue
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.platform.LocalContext
import androidx.compose.ui.tooling.preview.Preview
import androidx.compose.ui.unit.dp
import coil.compose.AsyncImage
import mx.dev.shellcore.android.imagepicker.ui.theme.ImagePickerTheme
import mx.dev.shellcore.android.imagepicker.utils.ComposeFileProvider

@Preview
@Composable
private fun ImagePickerLayoutPreview() {
    ImagePickerTheme {
        ImagePickerLayout()
    }
}

@Composable
fun ImagePickerLayout() {

    val context = LocalContext.current

    var hasImage by remember { mutableStateOf(false) }
    var imageUri by remember { mutableStateOf<Uri?>(null) }

    val imagePicker =
        rememberLauncherForActivityResult(
            contract = ActivityResultContracts.GetContent()
        ) { uri ->
            hasImage = uri != null
            imageUri = uri
        }

    val cameraLauncher = rememberLauncherForActivityResult(
        contract = ActivityResultContracts.TakePicture(),
    ) { success ->
        hasImage = success
    }

    Column(
        modifier = Modifier
            .fillMaxSize()
            .background(MaterialTheme.colorScheme.surface)
            .padding(16.dp),
        verticalArrangement = Arrangement.spacedBy(8.dp),
        horizontalAlignment = Alignment.CenterHorizontally
    ) {
        Button(onClick = {
            imagePicker.launch("image/*")
        }) {
            Text(text = "Select Image")
        }
        Button(onClick = {
            val uri = ComposeFileProvider.getImageUri(context)
            imageUri = uri
            cameraLauncher.launch(uri)
        }) {
            Text(text = "Take Picture")
        }

        if (hasImage && imageUri != null) {
            AsyncImage(
                modifier = Modifier.fillMaxWidth(),
                model = imageUri,
                contentDescription = "Selected Image"
            )
        }
    }
}
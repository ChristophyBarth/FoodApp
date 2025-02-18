package capps.foodapp.presentation.ui.components

import android.net.Uri
import android.util.Log
import androidx.activity.compose.rememberLauncherForActivityResult
import androidx.activity.result.contract.ActivityResultContracts
import androidx.annotation.DrawableRes
import androidx.compose.foundation.Image
import androidx.compose.foundation.border
import androidx.compose.foundation.clickable
import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.size
import androidx.compose.foundation.lazy.LazyRow
import androidx.compose.foundation.lazy.items
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.material3.Icon
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.remember
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.draw.clip
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.layout.ContentScale
import androidx.compose.ui.platform.LocalContext
import androidx.compose.ui.res.painterResource
import androidx.compose.ui.tooling.preview.Preview
import androidx.compose.ui.unit.dp
import capps.foodapp.R
import capps.foodapp.domain.helper.createImageFile
import capps.foodapp.domain.helper.getImageFilePath
import capps.foodapp.presentation.ui.theme.FoodAppTheme
import capps.foodapp.presentation.ui.theme.Neutral400
import capps.foodapp.presentation.ui.theme.TextBlackPrimary
import coil.compose.rememberAsyncImagePainter
import java.io.File

@Composable
fun ImagePickerScreen(
    modifier: Modifier = Modifier,
    onImageSelected: (String, Uri) -> Unit,
    onImageRemoved: (String, Uri) -> Unit,
    images: Map<String, Uri>
) {
    val context = LocalContext.current
    val capturedImageUri = remember {
        mutableStateOf(createImageFile(context))
    }

    val takePictureLauncher =
        rememberLauncherForActivityResult(ActivityResultContracts.TakePicture()) { success ->
            if (success) {

                val filePath = getImageFilePath(context, capturedImageUri.value)
                if (filePath != null) {
                    onImageSelected.invoke(filePath, capturedImageUri.value)
                }
            }
        }

    val pickImageLauncher =
        rememberLauncherForActivityResult(ActivityResultContracts.GetContent()) { uri ->
            if (uri != null) {

                val filePath = getImageFilePath(context, uri)
                if (filePath != null) {

                    val file = File(filePath)
                    Log.d(
                        "ImagePickerComponent",
                        "File: ${file.name}, Exists: ${file.exists()}, Path: $filePath"
                    )

                    onImageSelected.invoke(filePath, uri)
                }
            }
        }

    Column(
        modifier = Modifier.fillMaxWidth(),
        horizontalAlignment = Alignment.CenterHorizontally,
        verticalArrangement = Arrangement.Center
    ) {
        Row(
            horizontalArrangement = Arrangement.spacedBy(8.dp),
        ) {
            Option(
                text = "Take photo",
                icon = R.drawable.camera,
                modifier = Modifier.weight(1f),
                onClick = {
                    capturedImageUri.value = createImageFile(context)
                    takePictureLauncher.launch(capturedImageUri.value)
                })
            Option(
                text = "Upload",
                icon = R.drawable.upload,
                modifier = Modifier.weight(1f),
                onClick = {
                    pickImageLauncher.launch("image/*")
                })
        }

        LazyRow(
            horizontalArrangement = Arrangement.spacedBy(8.dp), modifier = Modifier
                .fillMaxWidth()
                .padding(top = 8.dp)
        ) {
            items(images.values.toList()) { imageUri ->
                Box {
                    Image(
                        painter = rememberAsyncImagePainter(imageUri),
                        contentDescription = "Selected Image",
                        modifier = Modifier
                            .clip(RoundedCornerShape(8.dp))
                            .size(50.dp), contentScale = ContentScale.Crop
                    )
                    Icon(
                        painterResource(R.drawable.remove_item),
                        tint = Color.Unspecified,
                        contentDescription = "Remove Item",
                        modifier = Modifier
                            .align(Alignment.BottomEnd)
                            .padding(4.dp)
                            .clickable {
                                val filePath = getImageFilePath(context, imageUri)

                                if (filePath != null) {
                                    if (File(filePath).exists()) {
                                        File(filePath).delete()
                                    }

                                    onImageRemoved.invoke(filePath, imageUri)
                                }
                            }
                    )
                }
            }
        }
    }
}

@Composable
fun Option(
    modifier: Modifier = Modifier,
    text: String,
    @DrawableRes icon: Int,
    onClick: () -> Unit
) {
    Column(
        horizontalAlignment = Alignment.CenterHorizontally,
        verticalArrangement = Arrangement.Center,
        modifier = modifier
            .height(90.dp)
            .clip(RoundedCornerShape(4.dp))
            .border(1.dp, color = Neutral400)
            .clickable { onClick() }
    ) {
        Icon(
            painterResource(icon),
            tint = Color(0xFF344054),
            modifier = Modifier.size(32.dp),
            contentDescription = "Camera"
        )
        Text(text, style = MaterialTheme.typography.bodyMedium.copy(color = TextBlackPrimary))
    }
}

@Preview(showBackground = true)
@Composable
fun ImagePickerScreenPreview() {
    FoodAppTheme {
//        ImagePickerScreen()
    }
}

package capps.foodapp.presentation.ui.components

import androidx.compose.runtime.Composable
import androidx.compose.ui.Modifier
import androidx.compose.ui.layout.ContentScale
import androidx.compose.ui.tooling.preview.Preview
import coil.compose.AsyncImage

@Composable
fun NetworkImage(imageUrl: String, contentScale: ContentScale, modifier: Modifier = Modifier) {
    AsyncImage(
        model = imageUrl,
        contentDescription = "Image",
        modifier = modifier,
        contentScale = contentScale
    )
}

@Preview(showBackground = true)
@Composable
fun PreviewImage() {
    NetworkImage(
        contentScale = ContentScale.FillWidth, imageUrl = "https://i.imgur.com/6kAfJmS.jpeg"
    )
}
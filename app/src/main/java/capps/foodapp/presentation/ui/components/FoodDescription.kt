package capps.foodapp.presentation.ui.components

import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.ui.Modifier
import capps.foodapp.presentation.ui.theme.TextBlackPrimary

@Composable
fun FoodDescription(modifier: Modifier = Modifier, desc: String) {
    Text(
        desc,
        style = MaterialTheme.typography.bodySmall.copy(color = TextBlackPrimary),
        modifier = modifier
    )
}
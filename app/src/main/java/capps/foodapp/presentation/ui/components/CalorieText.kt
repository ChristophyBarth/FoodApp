package capps.foodapp.presentation.ui.components

import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.size
import androidx.compose.material3.Icon
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.res.painterResource
import androidx.compose.ui.unit.dp
import capps.foodapp.R
import capps.foodapp.presentation.ui.theme.TextBlackSecondary

@Composable
fun CalorieText(modifier: Modifier = Modifier, calories: Int) {
    Row(verticalAlignment = Alignment.CenterVertically) {
        Icon(
            painterResource(R.drawable.fire),
            contentDescription = null,
            tint = Color.Unspecified,
            modifier = Modifier.size(16.dp)
        )
        Text(
            "$calories Calories",
            style = MaterialTheme.typography.bodySmall.copy(color = TextBlackSecondary),
            modifier = Modifier.padding(start = 4.dp)
        )
    }
}
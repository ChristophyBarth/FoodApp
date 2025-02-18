package capps.foodapp.presentation.ui.components

import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.material3.Button
import androidx.compose.material3.ButtonDefaults
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.tooling.preview.Preview
import androidx.compose.ui.unit.dp
import capps.foodapp.presentation.ui.theme.FoodAppTheme
import capps.foodapp.presentation.ui.theme.Neutral600
import capps.foodapp.presentation.ui.theme.Primary600
import capps.foodapp.presentation.ui.theme.bodyMediumBold
import capps.foodapp.presentation.ui.theme.bodyMediumMedium

@Composable
fun CustomButton(
    modifier: Modifier = Modifier,
    label: String,
    enabled: Boolean = true,
    onClick: () -> Unit = {}
) {
    Button(
        enabled = enabled,
        onClick = onClick,
        modifier = modifier
            .fillMaxWidth()
            .height(52.dp),
        shape = RoundedCornerShape(4.dp),
        colors = ButtonDefaults.buttonColors(
            containerColor = Primary600,
            disabledContainerColor = Color(0xFFE7F0FF),
            contentColor = Color.White,
            disabledContentColor = Neutral600,
        )
    ) {
        val buttonTextStyle =
            if (enabled) MaterialTheme.typography.bodyMediumBold else MaterialTheme.typography.bodyMediumMedium
        Text(label, style = buttonTextStyle)
    }
}

@Preview(showBackground = true)
@Composable
fun CustomButtonPreview() {
    FoodAppTheme {
        CustomButton(label = "Click Me", enabled = true, onClick = {})
    }
}
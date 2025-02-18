package capps.foodapp.presentation.ui.components

import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.text.KeyboardActions
import androidx.compose.foundation.text.KeyboardOptions
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.OutlinedTextField
import androidx.compose.material3.Text
import androidx.compose.material3.TextFieldDefaults
import androidx.compose.runtime.Composable
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.text.input.KeyboardType
import androidx.compose.ui.tooling.preview.Preview
import androidx.compose.ui.unit.Dp
import androidx.compose.ui.unit.dp
import capps.foodapp.presentation.ui.theme.FoodAppTheme
import capps.foodapp.presentation.ui.theme.Neutral600
import capps.foodapp.presentation.ui.theme.bodySmallMedium

@Composable
fun CustomTextField(
    modifier: Modifier = Modifier,
    title: String,
    value: String,
    onValueChange: (String) -> Unit,
    hint: String,
    height: Dp = 50.dp,
    keyboardType: KeyboardType = KeyboardType.Text,
    maxLines : Int = 1,
    singleLine : Boolean = true,
) {
    Column(modifier = modifier.padding(top = 16.dp)) {
        Text(
            title,
            style = MaterialTheme.typography.bodySmallMedium,
            modifier = modifier.padding(start = 5.dp, bottom = 4.dp)
        )
        OutlinedTextField(value = value,
            textStyle = MaterialTheme.typography.bodyMedium,
            modifier = modifier
                .fillMaxWidth()
                .height(height),
            onValueChange = onValueChange,
            keyboardOptions = KeyboardOptions.Default.copy(keyboardType = keyboardType),
            colors = TextFieldDefaults.colors(
                focusedContainerColor = Color.Transparent,
                unfocusedContainerColor = Color.Transparent,

                focusedIndicatorColor = Neutral600,
                unfocusedIndicatorColor = Neutral600,
            ),
            maxLines = maxLines,
            singleLine = singleLine,
            placeholder = {
                Text(hint, style = MaterialTheme.typography.bodyMedium.copy(color = Neutral600))
            })
    }
}

@Preview(showBackground = true)
@Composable
fun CustomTextFieldPreview() {
    FoodAppTheme {
        CustomTextField(
            title = "Title",
            hint = "Enter something here",
            value = "",
            onValueChange = {})
    }
}
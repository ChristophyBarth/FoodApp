package capps.foodapp.presentation.ui.components

import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.text.KeyboardActions
import androidx.compose.foundation.text.KeyboardOptions
import androidx.compose.material3.DropdownMenuItem
import androidx.compose.material3.ExperimentalMaterial3Api
import androidx.compose.material3.ExposedDropdownMenuBox
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.MenuAnchorType
import androidx.compose.material3.OutlinedTextField
import androidx.compose.material3.Text
import androidx.compose.material3.TextFieldDefaults
import androidx.compose.runtime.Composable
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.remember
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.text.input.KeyboardType
import androidx.compose.ui.unit.Dp
import androidx.compose.ui.unit.dp
import capps.foodapp.presentation.ui.theme.Neutral300
import capps.foodapp.presentation.ui.theme.Neutral600
import capps.foodapp.presentation.ui.theme.bodySmallMedium

@OptIn(ExperimentalMaterial3Api::class)
@Composable
fun AutoCompleteTextField(
    modifier: Modifier = Modifier,
    suggestions: List<String> = emptyList(),
    label: String = "Select",
    hint: String,
    maxLines: Int = 1,
    keyboardType: KeyboardType = KeyboardType.Text,
    height: Dp = 50.dp,
    singleLine: Boolean = true,
    onItemSelected: (String) -> Unit = { _: String -> },
    keyboardActions: KeyboardActions = KeyboardActions.Default

) {
    val text = remember { mutableStateOf("") }
    val expanded = remember { mutableStateOf(false) }

    val filteredSuggestions = suggestions.filter { it.contains(text.value, ignoreCase = true) }

    ExposedDropdownMenuBox(
        expanded = expanded.value,
        onExpandedChange = { expanded.value = it }
    ) {
        Column(modifier = modifier.padding(top = 16.dp)) {
            Text(
                label,
                style = MaterialTheme.typography.bodySmallMedium,
                modifier = modifier.padding(start = 5.dp, bottom = 4.dp)
            )
            OutlinedTextField(
                value = text.value,
                textStyle = MaterialTheme.typography.bodyMedium,
                modifier = modifier
                    .fillMaxWidth()
                    .height(height)
                    .menuAnchor(MenuAnchorType.PrimaryEditable),
                onValueChange = {
                    text.value = it
                    expanded.value = it.isNotEmpty()
                },
                keyboardOptions = KeyboardOptions.Default.copy(keyboardType = keyboardType),
                colors = TextFieldDefaults.colors(
                    focusedContainerColor = Color.Transparent,
                    unfocusedContainerColor = Color.Transparent,

                    focusedIndicatorColor = Neutral600,
                    unfocusedIndicatorColor = Neutral600,
                ),
                maxLines = maxLines,
                singleLine = singleLine,
                keyboardActions = keyboardActions,
                placeholder = {
                    Text(hint, style = MaterialTheme.typography.bodyMedium.copy(color = Neutral600))
                })
        }

        ExposedDropdownMenu(
            expanded = expanded.value,
            onDismissRequest = { expanded.value = false },
            containerColor = Neutral300
        ) {
            filteredSuggestions.forEach { suggestion ->
                DropdownMenuItem(
                    text = { Text(suggestion, style = MaterialTheme.typography.bodyMedium) },
                    onClick = {
                        onItemSelected.invoke(suggestion)
                        text.value = ""
                        expanded.value = false
                    }
                )
            }
        }
    }
}

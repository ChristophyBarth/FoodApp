package capps.foodapp.presentation.ui.components

import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.padding
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.filled.ArrowDropDown
import androidx.compose.material3.DropdownMenuItem
import androidx.compose.material3.ExperimentalMaterial3Api
import androidx.compose.material3.ExposedDropdownMenuBox
import androidx.compose.material3.Icon
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
import androidx.compose.ui.unit.dp
import capps.foodapp.presentation.ui.theme.Neutral600
import capps.foodapp.presentation.ui.theme.bodySmallMedium

@OptIn(ExperimentalMaterial3Api::class)
@Composable
fun CustomDropdown(
    modifier: Modifier = Modifier,
    title: String,
    hint: String,
    options: List<String>,
    selectedOption: String,
    onValueChange: (String) -> Unit
) {
    val expanded = remember { mutableStateOf(false) }

    Column(
        modifier = modifier
            .fillMaxWidth()
            .padding(top = 16.dp)
    ) {
        Text(
            title,
            style = MaterialTheme.typography.bodySmallMedium,
            modifier = modifier.padding(start = 5.dp, bottom = 4.dp)
        )
        ExposedDropdownMenuBox(expanded = expanded.value,
            onExpandedChange = { expanded.value = !expanded.value }) {

            OutlinedTextField(value = selectedOption,
                textStyle = MaterialTheme.typography.bodyMedium,
                onValueChange = {},
                readOnly = true,
                trailingIcon = {
                    Icon(Icons.Filled.ArrowDropDown, contentDescription = "Dropdown Icon")
                },
                colors = TextFieldDefaults.colors(
                    focusedContainerColor = Color.Transparent,
                    unfocusedContainerColor = Color.Transparent,

                    focusedIndicatorColor = Neutral600,
                    unfocusedIndicatorColor = Neutral600,
                ),
                modifier = Modifier
                    .fillMaxWidth()
                    .height(50.dp)
                    .menuAnchor(MenuAnchorType.PrimaryEditable, true),
                placeholder = {
                    Text(
                        hint, style = MaterialTheme.typography.bodyMedium.copy(color = Neutral600)
                    )
                })

            ExposedDropdownMenu(expanded = expanded.value,
                onDismissRequest = { expanded.value = false }) {
                options.forEach { option ->
                    DropdownMenuItem(text = {
                        Text(
                            option,
                            style = MaterialTheme.typography.bodyMedium
                        )
                    },
                        onClick = {
                            expanded.value = false
                            onValueChange(option)
                        })
                }
            }
        }
    }
}
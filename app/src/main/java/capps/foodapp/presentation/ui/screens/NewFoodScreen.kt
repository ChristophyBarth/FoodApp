package capps.foodapp.presentation.ui.screens

import androidx.compose.foundation.background
import androidx.compose.foundation.clickable
import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.PaddingValues
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.Spacer
import androidx.compose.foundation.layout.calculateEndPadding
import androidx.compose.foundation.layout.calculateStartPadding
import androidx.compose.foundation.layout.consumeWindowInsets
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.imePadding
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.size
import androidx.compose.foundation.layout.wrapContentHeight
import androidx.compose.foundation.lazy.LazyRow
import androidx.compose.foundation.lazy.items
import androidx.compose.foundation.rememberScrollState
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.foundation.verticalScroll
import androidx.compose.material3.ExperimentalMaterial3Api
import androidx.compose.material3.Icon
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.Scaffold
import androidx.compose.material3.SnackbarDuration
import androidx.compose.material3.SnackbarHost
import androidx.compose.material3.SnackbarHostState
import androidx.compose.material3.Text
import androidx.compose.material3.TopAppBar
import androidx.compose.material3.TopAppBarDefaults
import androidx.compose.runtime.Composable
import androidx.compose.runtime.LaunchedEffect
import androidx.compose.runtime.collectAsState
import androidx.compose.runtime.getValue
import androidx.compose.runtime.remember
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.draw.clip
import androidx.compose.ui.draw.drawBehind
import androidx.compose.ui.geometry.Offset
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.graphics.StrokeCap
import androidx.compose.ui.res.painterResource
import androidx.compose.ui.text.input.KeyboardType
import androidx.compose.ui.tooling.preview.Preview
import androidx.compose.ui.unit.LayoutDirection
import androidx.compose.ui.unit.dp
import androidx.hilt.navigation.compose.hiltViewModel
import androidx.navigation.NavHostController
import androidx.navigation.compose.rememberNavController
import capps.foodapp.R
import capps.foodapp.domain.models.Tag
import capps.foodapp.presentation.ui.components.AutoCompleteTextField
import capps.foodapp.presentation.ui.components.CustomButton
import capps.foodapp.presentation.ui.components.CustomDropdown
import capps.foodapp.presentation.ui.components.CustomIconButton
import capps.foodapp.presentation.ui.components.CustomTextField
import capps.foodapp.presentation.ui.components.ImagePickerScreen
import capps.foodapp.presentation.ui.theme.FoodAppTheme
import capps.foodapp.presentation.ui.theme.Neutral300
import capps.foodapp.presentation.viewmodels.NewFoodVM

@Composable
fun NewFoodScreen(
    newFoodVM: NewFoodVM = hiltViewModel(),
    bottomNavController: NavHostController,
) {
    val message by remember { newFoodVM.message }.collectAsState()
    val snackbarHostState = remember { SnackbarHostState() }

    LaunchedEffect(message) {
        message?.let {
            snackbarHostState.showSnackbar(it, duration = SnackbarDuration.Short)
            newFoodVM.resetMessage()
        }
    }

    Scaffold(topBar = { NewFoodTopBar(bottomNavController) },
        snackbarHost = { SnackbarHost(snackbarHostState) }) { contentPadding ->
        NewFoodContent(contentPadding, newFoodVM)
    }
}

@OptIn(ExperimentalMaterial3Api::class)
@Composable
fun NewFoodTopBar(bottomNavController: NavHostController) {
    Box(modifier = Modifier.drawBehind {
        val borderWidth = 1.dp.toPx()
        drawLine(
            color = Color(0xFFE4E7EC),
            start = Offset(0f, size.height),
            end = Offset(size.width, size.height),
            strokeWidth = borderWidth,
            cap = StrokeCap.Square
        )
    }) {
        TopAppBar(colors = TopAppBarDefaults.topAppBarColors(containerColor = Color.White),
            title = { Text("Add new food", style = MaterialTheme.typography.titleMedium) },
            navigationIcon = {
                CustomIconButton(
                    modifier = Modifier.padding(horizontal = 8.dp), resId = R.drawable.back_button
                ) {
                    bottomNavController.popBackStack()
                }
            })
    }
}

@Composable
fun NewFoodContent(contentPadding: PaddingValues, newFoodVM: NewFoodVM) {
    val foodCategories = remember { newFoodVM.categories }.collectAsState().value.map { it.name }
    val nameText by remember { newFoodVM.nameText }.collectAsState()
    val descText by remember { newFoodVM.descText }.collectAsState()
    val caloriesText by remember { newFoodVM.caloriesText }.collectAsState()
    val tags by remember { newFoodVM.tags }.collectAsState()

    val selectedCategory by remember { newFoodVM.selectedCategory }.collectAsState()
    val selectedCategoryName = selectedCategory?.name ?: ""

    val selectedTags by remember { newFoodVM.selectedTags }.collectAsState()
    val imageMap by remember { newFoodVM.imageMap }.collectAsState()

    val isFormValid by remember { newFoodVM.isFormValid }.collectAsState()

    val scrollState = rememberScrollState()

    Column(
        modifier = Modifier
            .wrapContentHeight()
            .verticalScroll(scrollState)
            .padding(
                start = contentPadding.calculateStartPadding(LayoutDirection.Ltr),
                end = contentPadding.calculateEndPadding(LayoutDirection.Ltr),
                top = contentPadding.calculateTopPadding()
            )
            .consumeWindowInsets(contentPadding)
            .background(Color.White)
            .padding(horizontal = 16.dp)
            .imePadding()
    ) {
        ImagePickerScreen(
            modifier = Modifier.padding(top = 16.dp),
            onImageSelected = newFoodVM::newImageSelected,
            onImageRemoved = newFoodVM::imageRemoved,
            images = imageMap
        )

        NewFoodForm(
            nameText, descText, caloriesText, selectedCategoryName, foodCategories, tags, newFoodVM
        )

        TagList(selectedTags, newFoodVM::removeTagClicked)

        Spacer(modifier = Modifier.weight(1f))

        CustomButton(
            label = "Add food",
            enabled = isFormValid,
            onClick = newFoodVM::createFood,
            modifier = Modifier
                .fillMaxWidth()
                .padding(top = 26.dp, bottom = 10.dp)
        )
    }
}


@Composable
fun NewFoodForm(
    name: String,
    description: String,
    calories: String,
    selectedCategory: String,
    categories: List<String>,
    tags: List<Tag>,
    newFoodVM: NewFoodVM
) {
    CustomTextField(
        title = "Name",
        hint = "Enter food name",
        value = name,
        onValueChange = newFoodVM::nameTextChanged
    )

    CustomTextField(
        title = "Description",
        hint = "Enter food description",
        height = 104.dp,
        value = description,
        onValueChange = newFoodVM::descTextChanged,
        singleLine = false,
        maxLines = Int.MAX_VALUE
    )

    CustomDropdown(
        title = "Category",
        hint = "Select category",
        options = categories,
        selectedOption = selectedCategory,
        onValueChange = newFoodVM::categorySelected
    )

    CustomTextField(
        title = "Calories",
        hint = "Enter number of calories",
        keyboardType = KeyboardType.Number,
        value = calories,
        onValueChange = newFoodVM::caloriesTextTextChanged
    )

    AutoCompleteTextField(suggestions = tags.map { it.name },
        label = "Tag",
        hint = "Add a tag",
        onItemSelected = { item ->
            newFoodVM.addTag(item)
        })

    Text(
        "Press enter once you've typed a tag.",
        style = MaterialTheme.typography.bodySmall,
        modifier = Modifier.padding(start = 5.dp, top = 4.dp, bottom = 4.dp)
    )
}

@Composable
fun TagList(selectedTags: List<String>, onRemoveTag: (String) -> Unit) {
    LazyRow(horizontalArrangement = Arrangement.spacedBy(4.dp)) {
        items(selectedTags) { selectedTag ->
            TagItem(selectedTag, onRemoveTag)
        }
    }
}

@Composable
fun TagItem(tag: String, onRemoveTag: (String) -> Unit) {
    Row(
        modifier = Modifier
            .clip(RoundedCornerShape(2.dp))
            .background(Neutral300)
            .padding(8.dp),
        verticalAlignment = Alignment.CenterVertically,
        horizontalArrangement = Arrangement.Center
    ) {
        Text(
            tag, style = MaterialTheme.typography.bodySmall, modifier = Modifier.padding(end = 2.dp)
        )
        Icon(painterResource(R.drawable.remove_tag),
            contentDescription = "Remove Tag",
            modifier = Modifier
                .size(9.dp)
                .clickable { onRemoveTag(tag) })
    }
}


@Preview(showBackground = true)
@Composable
fun NewFoodPreview() {
    FoodAppTheme {
        NewFoodScreen(
            bottomNavController = rememberNavController(),
        )
    }
}
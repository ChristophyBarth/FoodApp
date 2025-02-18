package capps.foodapp.presentation.ui.screens

import androidx.compose.foundation.Image
import androidx.compose.foundation.background
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.PaddingValues
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.Spacer
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.pager.HorizontalPager
import androidx.compose.foundation.pager.rememberPagerState
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
import androidx.compose.runtime.rememberCoroutineScope
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.draw.clip
import androidx.compose.ui.draw.drawBehind
import androidx.compose.ui.geometry.Offset
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.graphics.StrokeCap
import androidx.compose.ui.layout.ContentScale
import androidx.compose.ui.res.painterResource
import androidx.compose.ui.tooling.preview.Preview
import androidx.compose.ui.unit.dp
import androidx.hilt.navigation.compose.hiltViewModel
import androidx.navigation.NavHostController
import androidx.navigation.compose.rememberNavController
import capps.foodapp.R
import capps.foodapp.domain.models.Category
import capps.foodapp.domain.models.Food
import capps.foodapp.presentation.ui.components.CalorieText
import capps.foodapp.presentation.ui.components.CustomButton
import capps.foodapp.presentation.ui.components.CustomIconButton
import capps.foodapp.presentation.ui.components.FoodDescription
import capps.foodapp.presentation.ui.components.Tags
import capps.foodapp.presentation.ui.theme.FoodAppTheme
import capps.foodapp.presentation.ui.theme.Neutral100
import capps.foodapp.presentation.ui.theme.Primary600
import capps.foodapp.presentation.ui.theme.TextBlackSecondary
import capps.foodapp.presentation.ui.theme.bodySmallMedium
import capps.foodapp.presentation.viewmodels.FoodDetailVM
import coil.compose.rememberAsyncImagePainter
import kotlinx.coroutines.delay
import kotlinx.coroutines.launch

@Composable
fun FoodDetailsScreen(
    food: Food, foodDetailVM: FoodDetailVM = hiltViewModel(), appNavController: NavHostController
) {
    LaunchedEffect(key1 = Unit) {
        foodDetailVM.loadFood(food)
    }

    val message by remember { foodDetailVM.message }.collectAsState()
    val snackbarHostState = remember { SnackbarHostState() }

    LaunchedEffect(message) {
        message?.let {
            snackbarHostState.showSnackbar(it, duration = SnackbarDuration.Short)
            foodDetailVM.resetMessage()
        }
    }

    Scaffold(topBar = {
        FoodDetailsTopBar(
            appNavController = appNavController,
            foodDetailVM = foodDetailVM,
            food = food,
        )
    }, snackbarHost = { SnackbarHost(snackbarHostState) }) { contentPadding ->
        Box(modifier = Modifier.fillMaxSize()
            .background(color = Color.White)) {
            FoodDetailsContent(contentPadding, foodDetailVM)
        }
    }
}

@OptIn(ExperimentalMaterial3Api::class)
@Composable
fun FoodDetailsTopBar(appNavController: NavHostController, foodDetailVM: FoodDetailVM, food: Food) {
    val isFavourite by remember { foodDetailVM.addedToFavourites }.collectAsState()
    val heartIcon = if (isFavourite) R.drawable.heart_selected else R.drawable.heart_unselected

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
            title = { Text("", style = MaterialTheme.typography.titleMedium) },
            navigationIcon = {
                CustomIconButton(
                    modifier = Modifier.padding(horizontal = 8.dp), resId = R.drawable.back_button
                ) {
                    appNavController.popBackStack()
                }
            },
            actions = {
                CustomIconButton(
                    modifier = Modifier.padding(horizontal = 8.dp), resId = heartIcon
                ) {
                    foodDetailVM.toggleFavourite(food)
                }
                CustomIconButton(
                    modifier = Modifier.padding(horizontal = 8.dp), resId = R.drawable.edit
                ) {}
            })
    }
}

@Composable
fun FoodDetailsContent(contentPadding: PaddingValues, foodDetailVM: FoodDetailVM) {
    val food by remember { foodDetailVM.food }.collectAsState()

    if (food != null) {
        Column {
            if (food!!.foodImages.isNotEmpty()) {
                ImageSlider(food!!.foodImages.map { it.imageUrl })
            }
            Column(
                modifier = Modifier
                    .fillMaxSize()
                    .verticalScroll(rememberScrollState())
                    .padding(16.dp)
            ) {
                Text(
                    food!!.name,
                    style = MaterialTheme.typography.titleMedium,
                    modifier = Modifier.padding(bottom = 8.dp)
                )
                if (food!!.foodTags.isNotEmpty()) {
                    Tags(tags = food!!.foodTags, modifier = Modifier.padding(bottom = 16.dp))
                }
                FoodDescription(desc = food!!.description)
                Box(
                    modifier = Modifier.padding(vertical = 8.dp)
                ) {
                    Column(
                        modifier = Modifier
                            .fillMaxWidth()
                            .clip(RoundedCornerShape(4.dp))
                            .background(color = Neutral100)
                            .padding(vertical = 12.dp, horizontal = 16.dp)
                    ) {
                        Text(
                            "Nutrition",
                            style = MaterialTheme.typography.bodySmall.copy(color = TextBlackSecondary),
                            modifier = Modifier.padding(bottom = 2.dp)
                        )
                        CalorieText(calories = food!!.calories)
                    }
                }
                Text(
                    "Notes",
                    style = MaterialTheme.typography.bodySmall.copy(color = TextBlackSecondary),
                    modifier = Modifier.padding(bottom = 2.dp)
                )
                Row {
                    Icon(
                        painterResource(R.drawable.note),
                        contentDescription = null,
                        tint = Color.Unspecified,
                        modifier = Modifier.padding(
                            end = 2.dp
                        )
                    )
                    Text(
                        "Add notes",
                        style = MaterialTheme.typography.bodySmall.copy(color = Primary600),
                    )
                }
                Spacer(modifier = Modifier.weight(1f))

                CustomButton(
                    label = "Remove from collection",
                    modifier = Modifier
                        .fillMaxWidth()
                        .padding(top = 26.dp, bottom = 10.dp)
                )
            }
        }
    }
}

@Composable
fun ImageSlider(imageUrls: List<String> = emptyList()) {
    val pagerState = rememberPagerState(initialPage = 0, pageCount = { imageUrls.size })
    val scope = rememberCoroutineScope()

    LaunchedEffect(pagerState) {
        while (true) {
            delay(3000)
            val nextPage = (pagerState.currentPage + 1) % imageUrls.size
            scope.launch { pagerState.animateScrollToPage(nextPage) }
        }
    }

    Box(modifier = Modifier.fillMaxWidth()) {
        HorizontalPager(
            state = pagerState, modifier = Modifier
                .fillMaxWidth()
                .height(284.dp)
        ) { page ->
            Image(
                painter = rememberAsyncImagePainter(imageUrls[page]),
                contentDescription = "Slid-able Image",
                modifier = Modifier.fillMaxSize(),
                contentScale = ContentScale.FillWidth
            )
        }

        Box(
            modifier = Modifier
                .align(Alignment.BottomEnd)
                .padding(16.dp)
                .clip(RoundedCornerShape(4.dp))
                .background(Color.White)
        ) {
            Text(
                modifier = Modifier.padding(4.dp),
                text = "${pagerState.currentPage + 1}/${imageUrls.size}",
                style = MaterialTheme.typography.bodySmallMedium
            )
        }
    }
}


@Preview(showBackground = true)
@Composable
fun FoodDetailsScreenPreview() {
    FoodAppTheme {
        FoodDetailsScreen(
            food = Food(
                calories = 320,
                category = Category(description = "Test", id = 4, name = "Fast Food"),
                description = "Nice Food",
                id = 23,
                foodImages = emptyList(),
                name = "Pizza",
                foodTags = emptyList(),
                categoryId = 4,
            ),
            appNavController = rememberNavController(),
        )
    }
}
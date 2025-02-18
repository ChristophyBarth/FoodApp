package capps.foodapp.presentation.ui.screens

import androidx.compose.foundation.Image
import androidx.compose.foundation.background
import androidx.compose.foundation.clickable
import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.PaddingValues
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.Spacer
import androidx.compose.foundation.layout.WindowInsets
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.size
import androidx.compose.foundation.layout.statusBars
import androidx.compose.foundation.layout.width
import androidx.compose.foundation.layout.windowInsetsPadding
import androidx.compose.foundation.lazy.LazyColumn
import androidx.compose.foundation.lazy.LazyRow
import androidx.compose.foundation.lazy.items
import androidx.compose.foundation.lazy.rememberLazyListState
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.material.ExperimentalMaterialApi
import androidx.compose.material.pullrefresh.PullRefreshIndicator
import androidx.compose.material.pullrefresh.pullRefresh
import androidx.compose.material.pullrefresh.rememberPullRefreshState
import androidx.compose.material3.Icon
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.Scaffold
import androidx.compose.material3.SnackbarDuration
import androidx.compose.material3.SnackbarHost
import androidx.compose.material3.SnackbarHostState
import androidx.compose.material3.Text
import androidx.compose.material3.TextField
import androidx.compose.material3.TextFieldDefaults
import androidx.compose.runtime.Composable
import androidx.compose.runtime.LaunchedEffect
import androidx.compose.runtime.collectAsState
import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.remember
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
import capps.foodapp.domain.helper.navigateToDetails
import capps.foodapp.domain.models.Food
import capps.foodapp.presentation.ui.components.CalorieText
import capps.foodapp.presentation.ui.components.CustomIconButton
import capps.foodapp.presentation.ui.components.FoodDescription
import capps.foodapp.presentation.ui.components.NetworkImage
import capps.foodapp.presentation.ui.components.Tags
import capps.foodapp.presentation.ui.theme.FoodAppTheme
import capps.foodapp.presentation.ui.theme.Neutral100
import capps.foodapp.presentation.ui.theme.Neutral200
import capps.foodapp.presentation.ui.theme.Neutral300
import capps.foodapp.presentation.ui.theme.Primary600
import capps.foodapp.presentation.ui.theme.TextBlackPrimary
import capps.foodapp.presentation.ui.theme.TextBlackSecondary
import capps.foodapp.presentation.ui.theme.TextWhiteDisabled
import capps.foodapp.presentation.ui.theme.bodyMediumBold
import capps.foodapp.presentation.viewmodels.HomeVM

@Composable
fun HomeScreen(
    homeVM: HomeVM = hiltViewModel(),
    appNavController: NavHostController
) {
    val message by remember { homeVM.message }.collectAsState()
    val snackbarHostState = remember { SnackbarHostState() }

    LaunchedEffect(message) {
        message?.let {
            snackbarHostState.showSnackbar(it, duration = SnackbarDuration.Short)
            homeVM.resetMessage()
        }
    }

    Scaffold(snackbarHost = { SnackbarHost(snackbarHostState) }) { contentPadding ->
        HomeContent(contentPadding, modifier = Modifier, homeVM, appNavController)
    }
}

@Composable
fun HomeContent(
    contentPadding: PaddingValues,
    modifier: Modifier = Modifier,
    homeVM: HomeVM,
    appNavController: NavHostController
) {
    Box(
        modifier = modifier
            .windowInsetsPadding(WindowInsets.statusBars)
            .fillMaxSize()
            .background(color = Color.White)
    ) {
        Column {
            Column(
                modifier = Modifier
                    .background(color = Color.White)
                    .padding(top = 16.dp, bottom = 8.dp)
            ) {
                Row(
                    horizontalArrangement = Arrangement.SpaceBetween,
                    verticalAlignment = Alignment.CenterVertically,
                    modifier = Modifier
                        .fillMaxWidth()
                        .padding(bottom = 4.dp, start = 16.dp, end = 16.dp)
                ) {
                    Image(
                        painterResource(R.drawable.user_img),
                        contentDescription = "User Profile Picture",
                        modifier = Modifier
                            .size(42.dp)
                            .clip(RoundedCornerShape(21.dp))
                            .background(color = Color(0xFFE1F0FF))
                    )
                    CustomIconButton(modifier = modifier, R.drawable.notification, onClick = {

                    })
                }
                Text(
                    "Hey there, Lucy!",
                    style = MaterialTheme.typography.bodyLarge,
                    modifier = modifier.padding(horizontal = 16.dp)
                )
                Text(
                    "Are you excited to create a tasty dish today?",
                    style = MaterialTheme.typography.bodyMedium.copy(color = TextWhiteDisabled),
                    modifier = modifier.padding(horizontal = 16.dp)
                )
            }

            ScrollContainer(homeVM = homeVM, appNavController = appNavController)
        }
    }
}

@Composable
fun SearchBar(modifier: Modifier = Modifier) {
    val query = remember { mutableStateOf("") }

    Box(modifier = modifier.padding(top = 2.dp, bottom = 10.dp)) {
        Box(
            modifier = modifier
                .fillMaxWidth()
                .clip(RoundedCornerShape(4.dp))
                .background(color = Neutral300)
        ) {
            TextField(modifier = Modifier.fillMaxWidth(),
                colors = TextFieldDefaults.colors(
                    focusedContainerColor = Color.Transparent,
                    unfocusedContainerColor = Color.Transparent,

                    focusedIndicatorColor = Color.Transparent,
                    unfocusedIndicatorColor = Color.Transparent,
                    disabledIndicatorColor = Color.Transparent,
                    errorIndicatorColor = Color.Transparent,

                    cursorColor = Primary600
                ),
                value = query.value,
                onValueChange = { newText -> query.value = newText },
                textStyle = MaterialTheme.typography.bodyMedium,
                placeholder = {
                    Text(
                        "Search foods...",
                        style = MaterialTheme.typography.bodyMedium.copy(color = TextWhiteDisabled)
                    )
                },
                singleLine = true,
                leadingIcon = {
                    Icon(
                        painterResource(R.drawable.search),
                        contentDescription = null,
                        tint = Color.Unspecified,
                        modifier = modifier.size(16.dp)
                    )
                })
        }
    }
}

@Composable
fun FoodCategories(modifier: Modifier = Modifier, homeVM: HomeVM) {
    val categories by remember { homeVM.categories }.collectAsState(initial = emptyList())
    val selectedCategory by remember { homeVM.selectedCategory }.collectAsState(initial = null)

    val lazyRowState = rememberLazyListState()

    LazyRow(
        state = lazyRowState,
        horizontalArrangement = Arrangement.spacedBy(10.dp),
        modifier = modifier.padding(bottom = 20.dp)
    ) {

        item {
            Spacer(modifier = modifier.width(8.dp))
        }
        items(categories) { category ->
            val selected = category == selectedCategory
            val textStyle =
                if (selected) MaterialTheme.typography.bodyMediumBold.copy(color = Color.White) else MaterialTheme.typography.bodyMedium.copy(
                    color = TextBlackSecondary
                )
            val bgColor = if (selected) Primary600 else Neutral100

            Text(category.name, style = textStyle, modifier = modifier
                .clip(
                    RoundedCornerShape(4.dp)
                )
                .clickable { homeVM.selectCategory(category) }
                .background(color = bgColor)
                .padding(vertical = 15.dp, horizontal = 20.dp))

        }
    }
}

@OptIn(ExperimentalMaterialApi::class)
@Composable
fun ScrollContainer(
    modifier: Modifier = Modifier, homeVM: HomeVM, appNavController: NavHostController
) {
    val loading by remember { homeVM.loading }.collectAsState()
    val refreshing by remember { homeVM.refreshing }.collectAsState()
    val selectedCategory by remember { homeVM.selectedCategory }.collectAsState(initial = null)
    val selectedCategoryFoods by remember { homeVM.selectedCategoryFoods }.collectAsState(initial = emptyList())
    val favFoodList by remember { homeVM.favFoodList }.collectAsState(initial = emptyList())

    val pullRefreshState =
        rememberPullRefreshState(refreshing = refreshing, onRefresh = { homeVM.refresh() })
    val lazyColumnState = rememberLazyListState()

    Box(
        modifier = Modifier.pullRefresh(
            state = pullRefreshState
        )
    ) {
        LazyColumn(state = lazyColumnState) {
            item {
                Box(modifier = modifier.padding(horizontal = 16.dp)) {
                    SearchBar()
                }
            }
            item {
                FoodCategories(homeVM = homeVM)
            }
            item {
                Spacer(
                    modifier = modifier
                        .fillMaxWidth()
                        .height(8.dp)
                        .background(color = Neutral200)
                )
            }
            item {
                if (selectedCategory != null) {
                    val name = if (selectedCategory!!.name.contains(
                            "food", ignoreCase = true
                        )
                    ) selectedCategory!!.name else "${selectedCategory!!.name} Foods"

                    Text(
                        name,
                        style = MaterialTheme.typography.titleMedium,
                        modifier = modifier.padding(16.dp)
                    )
                }
            }
            if (loading) {
                item {
                    Box(
                        modifier = modifier
                            .fillMaxWidth()
                            .padding(20.dp),
                        contentAlignment = Alignment.Center
                    ) {
                        Text(
                            "Loading...", style = MaterialTheme.typography.bodyMedium.copy(
                                color = TextBlackSecondary
                            )
                        )
                    }
                }
            }

            if (selectedCategoryFoods.isEmpty() && !loading) {
                item {
                    Box(
                        modifier = modifier
                            .fillMaxWidth()
                            .padding(20.dp),
                        contentAlignment = Alignment.Center
                    ) {
                        Text(
                            "Sorry, we do not have any food in this category at the moment.",
                            style = MaterialTheme.typography.bodyMedium.copy(
                                color = TextBlackSecondary
                            )
                        )
                    }
                }
            }

            items(selectedCategoryFoods) { food ->
                val isFavourite = favFoodList.contains(food)

                FoodItem(
                    food = food,
                    isFavourite = isFavourite,
                    homeVM = homeVM,
                    appNavController = appNavController
                )
            }
        }

        PullRefreshIndicator(
            refreshing = refreshing,
            state = pullRefreshState,
            modifier = Modifier.align(Alignment.TopCenter)
        )
    }
}

@Composable
fun FoodItem(
    modifier: Modifier = Modifier,
    food: Food,
    isFavourite: Boolean,
    homeVM: HomeVM,
    appNavController: NavHostController
) {
    val heartIcon = if (isFavourite) R.drawable.heart_selected else R.drawable.heart_unselected

    Column(modifier = modifier
        .fillMaxWidth()
        .padding(bottom = 16.dp, start = 16.dp, end = 16.dp)
        .clickable {
            navigateToDetails(appNavController, food)
        }) {
        NetworkImage(
            imageUrl = food.foodImages.random().imageUrl,
            modifier = Modifier
                .fillMaxWidth()
                .height(137.dp)
                .clip(RoundedCornerShape(topStart = 4.dp, topEnd = 4.dp)),
            contentScale = ContentScale.FillWidth
        )
        Column(modifier = Modifier
            .clip(RoundedCornerShape(bottomStart = 4.dp, bottomEnd = 4.dp))
            .drawBehind {
                val borderWidth = 1.dp.toPx()
                val borderColor = Color(0xFFE4E7EC)

                drawLine(
                    color = borderColor,
                    start = Offset(0f, 0f),
                    end = Offset(0f, size.height),
                    strokeWidth = borderWidth,
                    cap = StrokeCap.Square
                )

                drawLine(
                    color = borderColor,
                    start = Offset(size.width, 0f),
                    end = Offset(size.width, size.height),
                    strokeWidth = borderWidth,
                    cap = StrokeCap.Square
                )

                drawLine(
                    color = borderColor,
                    start = Offset(0f, size.height),
                    end = Offset(size.width, size.height),
                    strokeWidth = borderWidth,
                    cap = StrokeCap.Square
                )
            }
            .padding(horizontal = 16.dp)) {
            Row(
                horizontalArrangement = Arrangement.SpaceBetween,
                verticalAlignment = Alignment.CenterVertically,
                modifier = Modifier
                    .fillMaxWidth()
                    .padding(top = 12.dp)
            ) {
                Text(
                    food.name,
                    style = MaterialTheme.typography.bodyLarge.copy(color = TextBlackPrimary),
                )
                Icon(painterResource(heartIcon),
                    tint = Color.Unspecified,
                    contentDescription = null,
                    modifier = Modifier
                        .clip(RoundedCornerShape(10.dp))
                        .clickable {
                            homeVM.toggleFavourite(food)
                        }
                        .size(18.dp))
            }
            CalorieText(calories = food.calories)
            FoodDescription(desc = food.description, modifier = Modifier.padding(vertical = 8.dp))
            if (food.foodTags.isNotEmpty()) {
                Tags(tags = food.foodTags, modifier = Modifier.padding(bottom = 12.dp))
            }
        }
    }
}

@Preview(showBackground = true)
@Composable
fun GreetingPreview() {
    FoodAppTheme {
        HomeScreen(appNavController = rememberNavController())
    }
}
package capps.foodapp.presentation.ui.navigation

import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.WindowInsets
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.size
import androidx.compose.material3.Icon
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.NavigationBar
import androidx.compose.material3.NavigationBarItem
import androidx.compose.material3.NavigationBarItemDefaults
import androidx.compose.material3.Scaffold
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.runtime.getValue
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.draw.shadow
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.res.painterResource
import androidx.compose.ui.tooling.preview.Preview
import androidx.compose.ui.unit.dp
import androidx.navigation.NavController
import androidx.navigation.NavGraph.Companion.findStartDestination
import androidx.navigation.NavHostController
import androidx.navigation.compose.NavHost
import androidx.navigation.compose.composable
import androidx.navigation.compose.currentBackStackEntryAsState
import androidx.navigation.compose.rememberNavController
import capps.foodapp.R
import capps.foodapp.presentation.ui.screens.HomeScreen
import capps.foodapp.presentation.ui.screens.NewFoodScreen
import capps.foodapp.presentation.ui.theme.FoodAppTheme
import capps.foodapp.presentation.ui.theme.Primary600
import capps.foodapp.presentation.ui.theme.TextWhiteDisabled
import capps.foodapp.presentation.ui.theme.bodySmallSemiBold

@Composable
fun BottomNavigation(modifier: Modifier = Modifier, appNavController: NavHostController) {
    val bottomNavController = rememberNavController()
    val topLevelRoutes = listOf(
        TopLevelRoute(
            Routes.HOME, icon = R.drawable.home
        ),
        TopLevelRoute(
            Routes.ADD_FOOD, icon = R.drawable.add
        ),
    )

    Scaffold(
        contentWindowInsets = WindowInsets(0),
        bottomBar = {
            BottomNavigationBar(
                navController = bottomNavController, topLevelRoutes = topLevelRoutes
            )
        },
    ) { innerPadding ->
        NavHost(
            navController = bottomNavController,
            startDestination = Routes.HOME,
            Modifier.padding(innerPadding)
        ) {
            composable(Routes.HOME) { HomeScreen(appNavController = appNavController) }
            composable(Routes.ADD_FOOD) {
                NewFoodScreen(
                    bottomNavController = bottomNavController,
                )
            }
        }
    }
}

@Composable
fun BottomNavigationBar(
    modifier: Modifier = Modifier,
    navController: NavController,
    topLevelRoutes: List<TopLevelRoute> = listOf()
) {
    Box(
        modifier = modifier
            .shadow(
                elevation = 8.dp,
            )
            .fillMaxWidth()
    ) {
        NavigationBar(containerColor = Color.White, modifier = modifier) {
            val navBackStackEntry by navController.currentBackStackEntryAsState()
            val currentDestination = navBackStackEntry?.destination
            topLevelRoutes.forEach { topLevelRoute ->
                val isSelected = currentDestination?.route == topLevelRoute.name
                val labelTextStyle =
                    if (isSelected) MaterialTheme.typography.bodySmallSemiBold else MaterialTheme.typography.bodySmall

                NavigationBarItem(icon = {
                    Column(horizontalAlignment = Alignment.CenterHorizontally) {
                        Icon(
                            painterResource(id = topLevelRoute.icon),
                            contentDescription = topLevelRoute.name,
                            modifier = Modifier.size(24.dp),
                        )
                    }
                }, label = {
                    Text(topLevelRoute.name, style = labelTextStyle)
                }, selected = isSelected, onClick = {
                    navController.navigate(topLevelRoute.name) {
                        popUpTo(navController.graph.findStartDestination().id) {
                            saveState = true
                        }

                        launchSingleTop = true
                        restoreState = true
                    }
                }, colors = NavigationBarItemDefaults.colors(
                    indicatorColor = Color.Transparent,
                    selectedIconColor = Primary600,
                    unselectedIconColor = TextWhiteDisabled,
                    selectedTextColor = Primary600,
                    unselectedTextColor = TextWhiteDisabled
                )
                )
            }
        }
    }
}

@Preview(showBackground = true)
@Composable
fun GreetingPreview() {
    FoodAppTheme {
        BottomNavigation(appNavController = rememberNavController())
    }
}
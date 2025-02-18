import androidx.compose.runtime.Composable
import androidx.compose.ui.tooling.preview.Preview
import androidx.navigation.compose.NavHost
import androidx.navigation.compose.composable
import androidx.navigation.compose.rememberNavController
import capps.foodapp.domain.models.Food
import capps.foodapp.presentation.ui.navigation.BottomNavigation
import capps.foodapp.presentation.ui.navigation.Routes
import capps.foodapp.presentation.ui.screens.FoodDetailsScreen
import capps.foodapp.presentation.ui.theme.FoodAppTheme
import com.google.gson.Gson

@Composable
fun AppNavigation() {
    val appNavController = rememberNavController()

    NavHost(navController = appNavController, startDestination = Routes.BOTTOM_NAV) {
        composable(Routes.BOTTOM_NAV) { BottomNavigation(appNavController = appNavController) }

        composable("${Routes.FOOD_DETAILS}/{food}") { backStackEntry ->
            val foodJson = backStackEntry.arguments?.getString("food")
            val food = Gson().fromJson(foodJson, Food::class.java)
            FoodDetailsScreen(food = food, appNavController = appNavController)
        }
    }
}

@Preview(showBackground = true)
@Composable
fun GreetingPreview() {
    FoodAppTheme {
        AppNavigation()
    }
}
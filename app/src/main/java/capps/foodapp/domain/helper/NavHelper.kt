package capps.foodapp.domain.helper

import android.net.Uri
import androidx.navigation.NavHostController
import capps.foodapp.domain.models.Food
import capps.foodapp.presentation.ui.navigation.Routes
import com.google.gson.Gson

fun navigateToDetails(appNavController: NavHostController, selectedFood: Food) {
    val foodJson = Uri.encode(Gson().toJson(selectedFood))
    appNavController.navigate("${Routes.FOOD_DETAILS}/$foodJson")
}
package capps.foodapp.domain.models

import capps.foodapp.data.models.FoodImage

data class Food(
    val calories: Int,
    val category: Category,
    val categoryId: Int,
    val description: String,
    val foodImages: List<FoodImage>,
    val foodTags: List<String>,
    val id: Int,
    val name: String,
)
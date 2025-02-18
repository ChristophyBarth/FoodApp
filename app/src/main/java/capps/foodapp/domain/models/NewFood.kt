package capps.foodapp.domain.models

data class NewFood(
    val calories: Int,
    val categoryId: Int,
    val description: String,
    val foodImages: List<String>,
    val foodTags: List<Int>,
    val name: String
)

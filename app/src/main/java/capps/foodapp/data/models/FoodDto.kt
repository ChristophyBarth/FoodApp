package capps.foodapp.data.models

import com.google.gson.annotations.SerializedName

data class FoodDto(
    val calories: Int,
    @SerializedName("category") val categoryDto: CategoryDto,
    @SerializedName("category_id") val categoryId: Int,
    @SerializedName("created_at") val createdAt: String?,
    val description: String,
    val foodImages: List<FoodImage>,
    val foodTags: List<String>,
    val id: Int,
    val name: String,
    @SerializedName("updated_at") val updatedAt: String?
)
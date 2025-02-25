package capps.foodapp.data.models

import com.google.gson.annotations.SerializedName

data class CategoryDto(
    @SerializedName("created_at") val createdAt: String?,
    val description: String,
    val id: Int,
    val name: String,
    @SerializedName("updated_at") val updatedAt: String?
)
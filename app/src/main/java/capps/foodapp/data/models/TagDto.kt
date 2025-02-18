package capps.foodapp.data.models

import com.google.gson.annotations.SerializedName

data class TagDto(
    val id: Int,
    val name: String,
    @SerializedName("created_at") val createdAt: String?,
    @SerializedName("updated_at") val updatedAt: String?
)
package capps.foodapp.data.mappers

import capps.foodapp.data.models.FoodDto
import capps.foodapp.domain.models.Food


fun FoodDto.toDomain(): Food {
    return Food(
        calories = this.calories,
        category = this.categoryDto.toDomain(),
        categoryId = this.categoryId,
        description = this.description,
        foodImages = this.foodImages,
        foodTags = this.foodTags,
        id = this.id,
        name = this.name,
    )
}

fun Food.toDto(): FoodDto {
    return FoodDto(
        calories = this.calories,
        categoryDto = this.category.toDto(),
        categoryId = this.categoryId,
        createdAt = null,
        description = this.description,
        foodImages = this.foodImages,
        foodTags = this.foodTags,
        id = this.id,
        name = this.name,
        updatedAt = null
    )
}

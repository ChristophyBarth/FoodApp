package capps.foodapp.data.mappers

import capps.foodapp.data.models.CategoryDto
import capps.foodapp.domain.models.Category

fun CategoryDto.toDomain(): Category {
    return Category(
        description = this.description,
        id = this.id,
        name = this.name,
    )
}

fun Category.toDto(): CategoryDto {
    return CategoryDto(
        createdAt = null,
        description = this.description,
        id = this.id,
        name = this.name,
        updatedAt = null
    )
}

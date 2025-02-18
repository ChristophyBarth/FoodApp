package capps.foodapp.data.mappers

import capps.foodapp.data.models.TagDto
import capps.foodapp.domain.models.Tag

fun TagDto.toDomain(): Tag {
    return Tag(
        name = this.name,
        id = this.id
    )
}

fun Tag.toDomain(): TagDto {
    return TagDto(
        name = this.name,
        id = this.id,
        createdAt = null,
        updatedAt = null,
    )
}
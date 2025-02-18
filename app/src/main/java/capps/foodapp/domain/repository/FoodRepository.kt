package capps.foodapp.domain.repository

import capps.foodapp.data.models.ResponseResult
import capps.foodapp.domain.models.Category
import capps.foodapp.domain.models.Food
import capps.foodapp.domain.models.NewFood
import capps.foodapp.domain.models.Tag

interface FoodRepository {
    suspend fun getFoodById(id: String): ResponseResult<Food>
    suspend fun getFoods(): ResponseResult<List<Food>>
    suspend fun getCategories(): ResponseResult<List<Category>>
    suspend fun getTags(): ResponseResult<List<Tag>>
    suspend fun createFood(newFood: NewFood): ResponseResult<Food>
}
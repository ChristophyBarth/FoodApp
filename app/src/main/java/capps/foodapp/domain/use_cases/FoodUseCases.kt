package capps.foodapp.domain.use_cases

import capps.foodapp.data.models.ResponseResult
import capps.foodapp.domain.models.Category
import capps.foodapp.domain.models.Food
import capps.foodapp.domain.models.NewFood
import capps.foodapp.domain.models.Tag
import capps.foodapp.domain.repository.FoodRepository
import javax.inject.Inject

class FoodUseCases @Inject constructor(private val repository: FoodRepository) {
    suspend fun getFoods(): ResponseResult<List<Food>> = repository.getFoods()

    suspend fun getFoodById(id: String): ResponseResult<Food> = repository.getFoodById(id)

    suspend fun getCategories(): ResponseResult<List<Category>> = repository.getCategories()

    suspend fun getTags(): ResponseResult<List<Tag>> = repository.getTags()

    suspend fun createFood(newFood: NewFood): ResponseResult<Food> = repository.createFood(newFood)
}
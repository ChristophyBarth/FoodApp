package capps.foodapp.data.repository

import android.util.Log
import capps.foodapp.data.mappers.toDomain
import capps.foodapp.data.models.ResponseResult
import capps.foodapp.data.remote.ApiService
import capps.foodapp.domain.models.Category
import capps.foodapp.domain.models.Food
import capps.foodapp.domain.models.NewFood
import capps.foodapp.domain.models.Tag
import capps.foodapp.domain.repository.FoodRepository
import okhttp3.MediaType.Companion.toMediaTypeOrNull
import okhttp3.MultipartBody
import okhttp3.RequestBody.Companion.asRequestBody
import okhttp3.RequestBody.Companion.toRequestBody
import java.io.File
import java.net.SocketTimeoutException
import java.net.UnknownHostException
import javax.inject.Inject

class FoodRepositoryImpl @Inject constructor(
    private val apiService: ApiService
) : FoodRepository {

    companion object {
        const val TAG = "FoodRepositoryImpl"
    }

    override suspend fun getFoodById(id: String): ResponseResult<Food> {

        return try {
            val response = apiService.getFoodById(id)
            if (response.status == "success") {
                ResponseResult.success(response.data.toDomain())
            } else {
                Log.w(TAG, "getFoodById Failure: ${response.message}")
                ResponseResult.failure(Exception(response.message))
            }
        } catch (e: Exception) {
            val errorMessage = "getFoodById Exception: ${e.message}"

            when (e) {
                is UnknownHostException -> {
                    Log.e(TAG, "$errorMessage - No internet connection")
                    ResponseResult.failure(Exception("No internet connection - Please check and refresh"))
                }

                is SocketTimeoutException -> {
                    Log.e(TAG, "$errorMessage - Request timed out")
                    ResponseResult.failure(Exception("Request timed out - Please refresh"))
                }

                else -> {
                    Log.e(TAG, "$errorMessage - Unexpected error")
                    ResponseResult.failure(Exception("Unexpected error: ${e.message}"))
                }
            }

            Log.e(TAG, errorMessage)
            ResponseResult.failure(e)
        }
    }

    override suspend fun getFoods(): ResponseResult<List<Food>> {
        return try {
            val response = apiService.getFoods()
            if (response.status == "success") {
                ResponseResult.success(response.data.map { it.toDomain() })
            } else {
                Log.w(TAG, "getFoods Failure: ${response.message}")
                ResponseResult.failure(Exception(response.message))
            }
        } catch (e: Exception) {
            val errorMessage = "getFoods Exception: ${e.message}"

            when (e) {
                is UnknownHostException -> {
                    Log.e(TAG, "$errorMessage - No internet connection")
                    ResponseResult.failure(Exception("No internet connection - Please check and refresh"))
                }

                is SocketTimeoutException -> {
                    Log.e(TAG, "$errorMessage - Request timed out")
                    ResponseResult.failure(Exception("Request timed out - Please refresh"))
                }

                else -> {
                    Log.e(TAG, "$errorMessage - Unexpected error")
                    ResponseResult.failure(Exception("Unexpected error: ${e.message}"))
                }
            }
        }
    }

    override suspend fun getCategories(): ResponseResult<List<Category>> {
        return try {
            val response = apiService.getCategories()
            if (response.status == "success") {
                ResponseResult.success(response.data.map { it.toDomain() })
            } else {
                Log.w(TAG, "getCategories Exception: ${response.message}")
                ResponseResult.failure(Exception(response.message))
            }
        } catch (e: Exception) {
            val errorMessage = "getCategories Exception: ${e.message}"

            when (e) {
                is UnknownHostException -> {
                    Log.e(TAG, "$errorMessage - No internet connection")
                    ResponseResult.failure(Exception("No internet connection - Please check and refresh"))
                }

                is SocketTimeoutException -> {
                    Log.e(TAG, "$errorMessage - Request timed out")
                    ResponseResult.failure(Exception("Request timed out - Please refresh"))
                }

                else -> {
                    Log.e(TAG, "$errorMessage - Unexpected error")
                    ResponseResult.failure(Exception("Unexpected error: ${e.message}"))
                }
            }
        }
    }

    override suspend fun getTags(): ResponseResult<List<Tag>> {
        return try {
            val response = apiService.getTags()
            if (response.status == "success") {
                ResponseResult.success(response.data.map { it.toDomain() })
            } else {
                Log.w(TAG, "getTags Exception: ${response.message}")
                ResponseResult.failure(Exception(response.message))
            }
        } catch (e: Exception) {
            val errorMessage = "getTags Exception: ${e.message}"

            when (e) {
                is UnknownHostException -> {
                    Log.e(TAG, "$errorMessage - No internet connection")
                    ResponseResult.failure(Exception("No internet connection - Please check and refresh"))
                }

                is SocketTimeoutException -> {
                    Log.e(TAG, "$errorMessage - Request timed out")
                    ResponseResult.failure(Exception("Request timed out - Please refresh"))
                }

                else -> {
                    Log.e(TAG, "$errorMessage - Unexpected error")
                    ResponseResult.failure(Exception("Unexpected error: ${e.message}"))
                }
            }
        }
    }

    override suspend fun createFood(newFood: NewFood): ResponseResult<Food> {
        return try {
            val nameRequestBody = newFood.name.toRequestBody()
            val descriptionRequestBody = newFood.description.toRequestBody()
            val categoryIdRequestBody = newFood.categoryId.toString().toRequestBody()
            val caloriesRequestBody = newFood.calories.toString().toRequestBody()

            val tagParts = newFood.foodTags.mapIndexed { index, tag ->
                MultipartBody.Part.createFormData("tags[$index]", tag.toString())
            }

            val images = newFood.foodImages.mapIndexed { index, filePath ->
                val file = File(filePath)
                val requestFile = file.asRequestBody("image/*".toMediaTypeOrNull())
                MultipartBody.Part.createFormData("images[$index]", file.name, requestFile)
            }

            val response = apiService.createFood(
                name = nameRequestBody,
                description = descriptionRequestBody,
                categoryId = categoryIdRequestBody,
                calories = caloriesRequestBody,
                tags = tagParts,
                images = images
            )

            if (response.status == "success") {
                ResponseResult.success(response.data.toDomain())
            } else {
                Log.w(TAG, "createFood Failure: ${response.message}")
                ResponseResult.failure(Exception(response.message))
            }
        } catch (e: Exception) {
            val errorMessage = "createFood Exception: ${e.message}"

            when (e) {
                is UnknownHostException -> {
                    Log.e(TAG, "$errorMessage - No internet connection")
                    ResponseResult.failure(Exception("No internet connection - Please check and refresh"))
                }

                is SocketTimeoutException -> {
                    Log.e(TAG, "$errorMessage - Request timed out")
                    ResponseResult.failure(Exception("Request timed out - Please refresh"))
                }

                else -> {
                    Log.e(TAG, "$errorMessage - Unexpected error")
                    ResponseResult.failure(Exception("Unexpected error: ${e.message}"))
                }
            }
        }
    }
}
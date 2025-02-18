package capps.foodapp.data.remote

import capps.foodapp.data.models.CategoryDto
import capps.foodapp.data.models.FoodDto
import capps.foodapp.data.models.Response
import capps.foodapp.data.models.TagDto
import okhttp3.MultipartBody
import okhttp3.RequestBody
import retrofit2.http.GET
import retrofit2.http.Multipart
import retrofit2.http.POST
import retrofit2.http.Part
import retrofit2.http.Path

interface ApiService {
    @GET("foods/{id}")
    suspend fun getFoodById(@Path("id") id: String): Response<FoodDto>

    @GET("foods")
    suspend fun getFoods(): Response<List<FoodDto>>

    @GET("categories")
    suspend fun getCategories(): Response<List<CategoryDto>>

    @GET("tags")
    suspend fun getTags(): Response<List<TagDto>>

    @Multipart
    @POST("foods")
    suspend fun createFood(
        @Part("name") name: RequestBody,
        @Part("description") description: RequestBody,
        @Part("category_id") categoryId: RequestBody,
        @Part("calories") calories: RequestBody,
        @Part tags: List<MultipartBody.Part>,
        @Part images: List<MultipartBody.Part>,
    ): Response<FoodDto>
}

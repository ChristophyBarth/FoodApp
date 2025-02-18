package capps.foodapp.di

import capps.foodapp.data.remote.ApiService
import capps.foodapp.data.repository.FoodRepositoryImpl
import capps.foodapp.domain.repository.FoodRepository
import dagger.Module
import dagger.Provides
import dagger.hilt.InstallIn
import dagger.hilt.components.SingletonComponent
import retrofit2.Retrofit
import retrofit2.converter.gson.GsonConverterFactory
import javax.inject.Singleton

@Module
@InstallIn(SingletonComponent::class)
object AppModule {
    @Provides
    @Singleton
    fun provideApiService(): ApiService {
        return Retrofit.Builder()
            .baseUrl("https://assessment.vgtechdemo.com/api/")
            .addConverterFactory(GsonConverterFactory.create())
            .build()
            .create(ApiService::class.java)
    }

    @Provides
    @Singleton
    fun provideFoodRepository(apiService: ApiService): FoodRepository {
        return FoodRepositoryImpl(apiService)
    }
}
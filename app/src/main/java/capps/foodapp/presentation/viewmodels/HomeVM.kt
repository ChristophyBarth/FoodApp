package capps.foodapp.presentation.viewmodels

import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import capps.foodapp.domain.models.Category
import capps.foodapp.domain.models.Food
import capps.foodapp.domain.use_cases.FoodUseCases
import dagger.hilt.android.lifecycle.HiltViewModel
import kotlinx.coroutines.async
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.flow.StateFlow
import kotlinx.coroutines.flow.asStateFlow
import kotlinx.coroutines.launch
import javax.inject.Inject

@HiltViewModel
class HomeVM @Inject constructor(private val foodUseCases: FoodUseCases) : ViewModel() {
    private val _selectedCategory = MutableStateFlow<Category?>(null)
    val selectedCategory: StateFlow<Category?> = _selectedCategory.asStateFlow()

    private val _selectedCategoryFoods = MutableStateFlow<List<Food>>(emptyList())
    val selectedCategoryFoods: StateFlow<List<Food>> = _selectedCategoryFoods.asStateFlow()

    private val _categories = MutableStateFlow<List<Category>>(emptyList())
    val categories: StateFlow<List<Category>> = _categories.asStateFlow()

    private val _foodList = MutableStateFlow<List<Food>>(emptyList())

    private val _message = MutableStateFlow<String?>(null)
    val message: StateFlow<String?> = _message.asStateFlow()

    private val _favFoodList = MutableStateFlow<List<Food>>(emptyList())
    val favFoodList: StateFlow<List<Food>> = _favFoodList.asStateFlow()

    private val _loading = MutableStateFlow(true)
    val loading: StateFlow<Boolean> = _loading.asStateFlow()

    private val _refreshing = MutableStateFlow(false)
    val refreshing: StateFlow<Boolean> = _refreshing.asStateFlow()

    init {
        fetchAll()
    }

    fun refresh() {
        _refreshing.value = true
        fetchAll()
    }

    private fun fetchAll() {
        viewModelScope.launch {
            val categoriesDeferred = async { fetchCategories() }
            val foodsDeferred = async { fetchFoods() }

            categoriesDeferred.await()
            foodsDeferred.await()

            //Setting the first category as selected
            if (categories.value.isNotEmpty()) {
                selectCategory(categories.value.first())
            }

            _loading.value = false
            _refreshing.value = false
        }
    }

    private suspend fun fetchFoods() {
        val result = foodUseCases.getFoods()
        result.fold(onSuccess = {
            _foodList.value = it
        }, onFailure = {
            _message.value = it.message
        })
    }

    private suspend fun fetchCategories() {
        val result = foodUseCases.getCategories()
        result.fold(onSuccess = {
            _categories.value = it
        }, onFailure = {
            _message.value = it.message
        })

    }

    fun selectCategory(category: Category) {
        _selectedCategory.value = category

        _selectedCategoryFoods.value = _foodList.value.filter { it.category == category }
    }

    fun toggleFavourite(food: Food) {
        if (_favFoodList.value.contains(food)) _favFoodList.value -= food
        else _favFoodList.value += food
    }

    fun resetMessage() {
        _message.value = null
    }
}
package capps.foodapp.presentation.viewmodels

import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import capps.foodapp.domain.models.Food
import capps.foodapp.domain.use_cases.FoodUseCases
import dagger.hilt.android.lifecycle.HiltViewModel
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.flow.StateFlow
import kotlinx.coroutines.flow.asStateFlow
import kotlinx.coroutines.launch
import javax.inject.Inject

@HiltViewModel
class FoodDetailVM @Inject constructor(private val foodUseCases: FoodUseCases) : ViewModel() {
    private val _food = MutableStateFlow<Food?>(null)
    val food: StateFlow<Food?> = _food.asStateFlow()

    private val _addedToFavourites = MutableStateFlow(false)
    val addedToFavourites = _addedToFavourites.asStateFlow()

    private val _message = MutableStateFlow<String?>(null)
    val message: StateFlow<String?> = _message.asStateFlow()


    private fun fetch(id: String) {
        viewModelScope.launch {
            val result = foodUseCases.getFoodById(id)
            result.fold(onSuccess = {
                if (_food.value != it) {
                    _food.value = it
                    _message.value = "Details about this food have been updated"
                }
            }, onFailure = {
                _message.value = it.message
            })
        }
    }

    fun loadFood(food: Food) {
        _food.value = food
        fetch(food.id.toString())
    }

    fun toggleFavourite(food: Food) {
        _addedToFavourites.value = !_addedToFavourites.value
    }

    fun resetMessage() {
        _message.value = null
    }
}
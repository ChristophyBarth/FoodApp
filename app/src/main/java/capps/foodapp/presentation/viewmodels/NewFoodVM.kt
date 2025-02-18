package capps.foodapp.presentation.viewmodels

import android.net.Uri
import android.util.Log
import androidx.core.text.isDigitsOnly
import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import capps.foodapp.domain.models.Category
import capps.foodapp.domain.models.NewFood
import capps.foodapp.domain.models.Tag
import capps.foodapp.domain.use_cases.FoodUseCases
import dagger.hilt.android.lifecycle.HiltViewModel
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.flow.StateFlow
import kotlinx.coroutines.flow.asStateFlow
import kotlinx.coroutines.launch
import javax.inject.Inject

@HiltViewModel
class NewFoodVM @Inject constructor(private val foodUseCases: FoodUseCases) : ViewModel() {
    private val _categories = MutableStateFlow<List<Category>>(emptyList())
    val categories: StateFlow<List<Category>> = _categories.asStateFlow()

    private val _allTags = MutableStateFlow<List<Tag>>(emptyList())

    private val _tags = MutableStateFlow<List<Tag>>(emptyList())
    val tags: StateFlow<List<Tag>> = _tags.asStateFlow()

    private val _imagesMap = MutableStateFlow<Map<String, Uri>>(mapOf())
    val imageMap = _imagesMap.asStateFlow()

    private val _message = MutableStateFlow<String?>(null)
    val message: StateFlow<String?> = _message.asStateFlow()

    private val _nameText = MutableStateFlow("")
    val nameText: StateFlow<String> = _nameText.asStateFlow()

    private val _descText = MutableStateFlow("")
    val descText: StateFlow<String> = _descText.asStateFlow()

    private val _selectedCategory = MutableStateFlow<Category?>(null)
    val selectedCategory: StateFlow<Category?> = _selectedCategory.asStateFlow()

    private val _caloriesText = MutableStateFlow("")
    val caloriesText: StateFlow<String> = _caloriesText.asStateFlow()

    private val _selectedTags = MutableStateFlow<List<String>>(emptyList())
    val selectedTags: StateFlow<List<String>> = _selectedTags.asStateFlow()

    private val _isFormValid = MutableStateFlow(false)
    val isFormValid = _isFormValid.asStateFlow()

    init {
        viewModelScope.launch {
            fetchCategories()
            fetchTags()
        }
    }

    private fun checkIfFormIsValid(): Boolean {
        val isValid = _imagesMap.value.isNotEmpty() &&
                nameText.value.isNotEmpty() &&
                descText.value.isNotEmpty() &&
                _selectedCategory.value != null &&
                caloriesText.value.isNotEmpty() &&
                caloriesText.value.isDigitsOnly() &&
                selectedTags.value.isNotEmpty()

        _isFormValid.value = isValid

        return isValid
    }

    fun newImageSelected(path: String, uri: Uri) {
        _imagesMap.value = _imagesMap.value.toMutableMap().apply { put(path, uri) }
        checkIfFormIsValid()
    }

    fun imageRemoved(path: String, uri: Uri) {
        _imagesMap.value = _imagesMap.value.toMutableMap().apply { remove(path) }
        checkIfFormIsValid()
    }

    fun nameTextChanged(newValue: String) {
        _nameText.value = newValue
        checkIfFormIsValid()
    }


    fun descTextChanged(newValue: String) {
        _descText.value = newValue
        checkIfFormIsValid()
    }

    fun categorySelected(newValue: String) {
        _selectedCategory.value = _categories.value.find { it.name == newValue }
        checkIfFormIsValid()
    }

    fun caloriesTextTextChanged(newValue: String) {
        _caloriesText.value = newValue
        checkIfFormIsValid()
    }

    fun addTag(tag: String) {
        _selectedTags.value += tag
        _tags.value -= _allTags.value.find { it.name == tag }!!
        checkIfFormIsValid()
    }

    fun removeTagClicked(tag: String) {
        _selectedTags.value -= tag
        _tags.value += _allTags.value.find { it.name == tag }!!
        checkIfFormIsValid()
    }

    private suspend fun fetchTags() {
        val result = foodUseCases.getTags()
        result.fold(onSuccess = {
            _tags.value = it
            _allTags.value = it
        }, onFailure = {
            Log.d("NewFoodVM", "fetchTags: ${it.message}")
            _message.value = it.message
        })

    }

    private suspend fun fetchCategories() {
        val result = foodUseCases.getCategories()
        result.fold(onSuccess = {
            _categories.value = it
        }, onFailure = {
            Log.d("NewFoodVM", "fetchCategories: ${it.message}")
            _message.value = it.message
        })

    }

    fun createFood() {
        val food = NewFood(
            calories = caloriesText.value.toInt(),
            categoryId = _selectedCategory.value!!.id,
            description = descText.value,
            foodImages = _imagesMap.value.keys.toList(),
            foodTags = selectedTags.value.map { selectedTag -> _allTags.value.find { tag -> tag.name == selectedTag }!!.id },
            name = nameText.value
        )
        viewModelScope.launch {
            val result = foodUseCases.createFood(food)
            result.fold(onSuccess = {
                _message.value = "Food created successfully"

                _nameText.value = ""
                _descText.value = ""
                _selectedCategory.value = null
                _caloriesText.value = ""
                _selectedTags.value = emptyList()
                _imagesMap.value = emptyMap()
            }, onFailure = {
                _message.value = it.message
            })
        }
    }

    fun resetMessage() {
        _message.value = null
    }
}
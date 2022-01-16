package com.example.kitchenhelper.presentation.search.viewModel

import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.example.kitchenhelper.presentation.search.mappers.toPresentation
import com.example.kitchenhelper.presentation.search.model.Recipe
import com.example.kitchenhelper.presentation.search.model.RequestParams
import com.example.kitchenhelper.presentation.search.repository.SearchRepository
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.flow.*
import kotlinx.coroutines.launch
import javax.inject.Inject

class SearchRecipeViewModel @Inject constructor(private val searchRepository: SearchRepository) :
    ViewModel() {

    val recipes = MutableStateFlow<List<Recipe>>(emptyList())
    val emptyStateFlow = MutableSharedFlow<Boolean>()
    val error = MutableSharedFlow<Throwable>()

    val requestParams = RequestParams("", null, null)

    fun searchRecipes() {
        viewModelScope.launch(Dispatchers.IO) {
            flow {
                with(requestParams) {
                    emit(
                        searchRepository.getSearchRecipes(
                            query,
                            equipment,
                            maxReadyTime,
                            calories?.first,
                            calories?.second
                        )
                    )
                }
            }
                .map { list -> list.map { it.toPresentation() } }
                .catch { error.emit(it) }
                .collect {
                    if (it.isNotEmpty()) {
                        recipes.emit(it)
                    } else {
                        emptyStateFlow.emit(value = true)
                    }
                }

        }
    }

    fun setEquipment(equipment: String) {
        requestParams.equipment = equipment
    }

    fun setMaxReadyTime(time: Int) {
        requestParams.maxReadyTime = time
    }

    fun setCalories(calories: Pair<Int,Int>){
        requestParams.calories = calories
    }
}
package com.example.kitchenhelper.presentation.recipeInfo.viewModel

import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.example.kitchenhelper.presentation.recipeInfo.repository.RecipeInfoRepository
import com.example.kitchenhelper.presentation.search.mappers.toPresentation
import com.example.kitchenhelper.presentation.search.model.Recipe
import kotlinx.coroutines.flow.MutableSharedFlow
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.flow.catch
import kotlinx.coroutines.flow.map
import kotlinx.coroutines.launch
import javax.inject.Inject

class RecipeInfoViewModel @Inject constructor(private val repository: RecipeInfoRepository) :
    ViewModel() {

    val recipeFlow = MutableStateFlow<Recipe?>(null)
    val errorFlow = MutableSharedFlow<Throwable>()

    fun getRecipe(id: Int) {
        viewModelScope.launch {
            repository.getRecipe(id)
                .map { it.toPresentation() }
                .catch { errorFlow.emit(it) }
                .collect { recipeFlow.emit(it) }
        }
    }
}
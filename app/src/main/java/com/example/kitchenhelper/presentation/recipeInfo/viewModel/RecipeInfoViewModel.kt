package com.example.kitchenhelper.presentation.recipeInfo.viewModel

import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.example.kitchenhelper.presentation.recipeInfo.mappers.toPresentation
import com.example.kitchenhelper.presentation.recipeInfo.model.RecipeInfo
import com.example.kitchenhelper.presentation.recipeInfo.repository.RecipeInfoRepository
import kotlinx.coroutines.flow.MutableSharedFlow
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.flow.catch
import kotlinx.coroutines.flow.map
import kotlinx.coroutines.launch
import java.util.regex.Pattern
import javax.inject.Inject

class RecipeInfoViewModel @Inject constructor(private val repository: RecipeInfoRepository) :
    ViewModel() {

    companion object {
        private const val CLEAR_TEXT_PATTERN = "<[a-z]+>|</[a-z]+>"
    }

    val recipeFlow = MutableStateFlow<RecipeInfo?>(null)
    val errorFlow = MutableSharedFlow<Throwable>()

    private val pattern = Pattern.compile(CLEAR_TEXT_PATTERN)
    private val buffer = StringBuffer()

    fun getRecipe(id: Int) {
        viewModelScope.launch {
            repository.getRecipe(id)
                .map { it.toPresentation(buffer, pattern) }
                .catch { errorFlow.emit(it) }
                .collect { recipeFlow.emit(it) }
        }
    }
}
package com.example.kitchenhelper.presentation.random.viewModel

import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.example.kitchenhelper.presentation.random.mappers.toPresentation
import com.example.kitchenhelper.presentation.random.model.RandomRecipe
import com.example.kitchenhelper.presentation.random.repository.RandomRepository
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.flow.MutableSharedFlow
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.flow.catch
import kotlinx.coroutines.flow.map
import kotlinx.coroutines.launch
import java.util.regex.Pattern
import javax.inject.Inject

class RandomViewModel @Inject constructor(private val repository: RandomRepository) : ViewModel() {

    companion object {
        private const val CLEAR_TEXT_PATTERN = "<[a-z]+>|</[a-z]+>"
    }

    val randomRecipesFlow = MutableStateFlow<RandomRecipe?>(null)
    val notLoadingFlow = MutableSharedFlow<Boolean>()
    val errorFlow = MutableSharedFlow<Throwable>()
    var transitionId: Int? = null

    private val pattern = Pattern.compile(CLEAR_TEXT_PATTERN)
    private val buffer = StringBuffer()
    private var isLoading = false

    init {
        getRecipes()
    }

    fun getRecipes() {
        if (!isLoading) {
            isLoading = true
            viewModelScope.launch(Dispatchers.IO) {
                repository.getRandomRecipes()
                    .map { list ->
                        list[0].toPresentation(pattern, buffer)
                    }
                    .catch {
                        errorFlow.emit(it)
                        setEndLoading()
                    }
                    .collect {
                        randomRecipesFlow.emit(it)
                        setEndLoading()
                    }
            }
        } else {
            viewModelScope.launch(Dispatchers.IO) {
                notLoadingFlow.emit(false)
            }
        }
    }

    private suspend fun setEndLoading() {
        isLoading = false
        notLoadingFlow.emit(isLoading)
    }

    fun transitionCompleted(transitionId: Int) {
        this.transitionId = transitionId
    }
}
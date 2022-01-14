package com.example.kitchenhelper.presentation.random.viewModel

import androidx.lifecycle.ViewModel
import androidx.lifecycle.ViewModelProvider
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
import javax.inject.Provider

class RandomViewModel(private val repository: RandomRepository) : ViewModel() {

    companion object {
        private const val CLEAR_TEXT_PATTERN = "<[a-z]+>|</[a-z]+>"
    }

    val randomRecipesFlow = MutableStateFlow<RandomRecipe?>(null)
    val notLoadingFlow = MutableSharedFlow<Boolean>()
    val errorFlow = MutableSharedFlow<Throwable>()

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
                notLoadingFlow.emit(!isLoading)
            }
        }
    }

    private suspend fun setEndLoading() {
        isLoading = false
        notLoadingFlow.emit(isLoading)
    }


    class Factory @Inject constructor(private val repository: Provider<RandomRepository>) :
        ViewModelProvider.Factory {
        @Suppress("UNCHECKED_CAST")
        override fun <T : ViewModel> create(modelClass: Class<T>): T {
            return RandomViewModel(repository.get()) as T
        }
    }
}
package com.example.kitchenhelper.presentation.search.viewModel

import androidx.lifecycle.ViewModel
import androidx.lifecycle.ViewModelProvider
import androidx.lifecycle.viewModelScope
import com.example.kitchenhelper.presentation.search.model.Recipe
import com.example.kitchenhelper.presentation.search.model.RequestParams
import com.example.kitchenhelper.presentation.search.repository.SearchRepository
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.flow.MutableSharedFlow
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.flow.catch
import kotlinx.coroutines.flow.flow
import kotlinx.coroutines.launch
import javax.inject.Inject
import javax.inject.Provider

class SearchRecipeViewModel(private val searchRepository: SearchRepository) : ViewModel() {

    val recipes = MutableStateFlow<List<Recipe>>(emptyList())
    val error = MutableSharedFlow<Throwable>()

    val requestParams = RequestParams(null, null, null)

    fun searchRecipes(query: String) {
        viewModelScope.launch(Dispatchers.IO) {
            flow {
                emit(searchRepository.getSearchRecipes(query))
            }
                .catch {
                    error.emit(it)
                }
                .collect {
                }

        }
    }

    fun setQuery(query: String) {
        requestParams.query = query
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

    class Factory @Inject constructor(private val searchRepository: Provider<SearchRepository>) :
        ViewModelProvider.Factory {
        @Suppress("UNCHECKED_CAST")
        override fun <T : ViewModel> create(modelClass: Class<T>): T {
            return SearchRecipeViewModel(searchRepository.get()) as T
        }
    }
}
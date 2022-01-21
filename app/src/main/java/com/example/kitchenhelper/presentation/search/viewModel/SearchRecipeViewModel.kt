package com.example.kitchenhelper.presentation.search.viewModel

import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import androidx.paging.PagingData
import androidx.paging.cachedIn
import androidx.paging.map
import com.example.kitchenhelper.presentation.search.mappers.toPresentation
import com.example.kitchenhelper.presentation.search.model.Recipe
import com.example.kitchenhelper.presentation.search.model.RequestParams
import com.example.kitchenhelper.presentation.search.repository.SearchRepository
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.flow.MutableSharedFlow
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.flow.catch
import kotlinx.coroutines.flow.map
import kotlinx.coroutines.launch
import javax.inject.Inject

class SearchRecipeViewModel @Inject constructor(
    private val searchRepository: SearchRepository,
    private val requestParams: RequestParams
) :
    ViewModel() {

    val recipesFlow = MutableStateFlow<PagingData<Recipe>?>(null)
    val emptyStateFlow = MutableSharedFlow<Boolean>()
    val error = MutableSharedFlow<Throwable>()
    private var isLoading = false

    fun searchRecipes() {
        if (!isLoading) {
            isLoading = true
            viewModelScope.launch(Dispatchers.IO) {
                searchRepository.getSearchRecipes(requestParams)
                    .cachedIn(viewModelScope)
                    .map { pagingData ->
                        pagingData.map { it.toPresentation() }
                    }
                    .catch { handleError(it) }
                    .collect { handleResult(it) }

            }
        }
    }

    private suspend fun handleResult(recipes: PagingData<Recipe>) {
        recipesFlow.emit(recipes)
        isLoading = false
    }

    private suspend fun handleError(throwable: Throwable) {
        error.emit(throwable)
        isLoading = false
    }

    fun setEquipment(equipment: String?) {
        requestParams.equipment = equipment
    }

    fun setMaxReadyTime(time: Int?) {
        requestParams.maxReadyTime = time
    }

    fun setCalories(calories: Pair<Int, Int>) {
        requestParams.calories = calories
    }

    fun setQuery(query: String) {
        requestParams.query = query
    }

    fun getQuery() = requestParams.query
}
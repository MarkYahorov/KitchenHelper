package com.example.kitchenhelper.presentation.search.repository

import com.example.kitchenhelper.core.data.api.RecipeService
import com.example.kitchenhelper.core.data.entities.RecipeDto
import kotlinx.coroutines.flow.Flow
import kotlinx.coroutines.flow.catch
import kotlinx.coroutines.flow.flow
import javax.inject.Inject

class SearchSearchRepositoryImpl @Inject constructor(private val recipeService: RecipeService) :
    SearchRepository {
    override suspend fun getSearchRecipes(query: String) =
        recipeService.getSearchRecipes(query).result
}
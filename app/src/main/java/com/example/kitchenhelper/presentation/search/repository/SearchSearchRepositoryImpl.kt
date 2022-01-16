package com.example.kitchenhelper.presentation.search.repository

import com.example.kitchenhelper.core.data.api.RecipeService
import javax.inject.Inject

class SearchSearchRepositoryImpl @Inject constructor(private val recipeService: RecipeService) :
    SearchRepository {
    override suspend fun getSearchRecipes(
        query: String,
        equipment: String?,
        maxReadyTime: Int?,
        minCalories: Int?,
        maxCalories: Int?
    ) = recipeService.getSearchRecipes(
        query = query,
        equipment = equipment,
        cookingTime = maxReadyTime,
        minCalories = minCalories,
        maxCalories = maxCalories
    ).result

}
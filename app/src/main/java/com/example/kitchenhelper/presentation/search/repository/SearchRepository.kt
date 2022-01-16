package com.example.kitchenhelper.presentation.search.repository

import com.example.kitchenhelper.core.data.entities.RecipeDto

interface SearchRepository {

    suspend fun getSearchRecipes(
        query: String,
        equipment: String? = null,
        maxReadyTime: Int? = null,
        minCalories: Int? = null,
        maxCalories: Int? = null
    ): List<RecipeDto>
}
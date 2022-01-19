package com.example.kitchenhelper.presentation.recipeInfo.repository

import com.example.kitchenhelper.core.data.entities.RecipeDto
import kotlinx.coroutines.flow.Flow

interface RecipeInfoRepository {
    fun getRecipe(id: Int): Flow<RecipeDto>
}
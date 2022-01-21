package com.example.kitchenhelper.presentation.recipeInfo.repository

import com.example.kitchenhelper.core.data.entities.RecipeInfoDto
import kotlinx.coroutines.flow.Flow

interface RecipeInfoRepository {
    fun getRecipe(id: Int): Flow<RecipeInfoDto>
}
package com.example.kitchenhelper.presentation.random.repository

import com.example.kitchenhelper.core.data.entities.RandomRecipeDto
import com.example.kitchenhelper.core.data.entities.RecipeDto
import kotlinx.coroutines.flow.Flow

interface RandomRepository {

    suspend fun getRandomRecipes(): Flow<RandomRecipeDto>
}
package com.example.kitchenhelper.presentation.search.repository

import com.example.kitchenhelper.core.data.entities.RecipeDto
import kotlinx.coroutines.flow.Flow

interface SearchRepository {

    suspend fun getSearchRecipes(query: String): List<RecipeDto>
}
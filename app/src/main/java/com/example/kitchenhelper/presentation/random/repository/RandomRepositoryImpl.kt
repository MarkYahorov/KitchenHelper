package com.example.kitchenhelper.presentation.random.repository

import com.example.kitchenhelper.core.data.api.RecipeService
import kotlinx.coroutines.flow.flow
import javax.inject.Inject

class RandomRepositoryImpl @Inject constructor(private val recipeApi: RecipeService) :
    RandomRepository {
    override suspend fun getRandomRecipes() = flow {
        emit(recipeApi.getRandomRecipes())
    }
}
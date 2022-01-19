package com.example.kitchenhelper.presentation.recipeInfo.repository

import com.example.kitchenhelper.core.data.api.RecipeService
import kotlinx.coroutines.flow.flow
import javax.inject.Inject

class RecipeInfoRepositoryImpl @Inject constructor(private val service: RecipeService) :
    RecipeInfoRepository {
    override fun getRecipe(id: Int) = flow {
        emit(service.getInfoAboutRecipe(id))
    }
}
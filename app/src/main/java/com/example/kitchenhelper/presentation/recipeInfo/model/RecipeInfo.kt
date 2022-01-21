package com.example.kitchenhelper.presentation.recipeInfo.model

import com.example.kitchenhelper.presentation.random.model.Ingredient

data class RecipeInfo(
    val id: Int,
    val title: String,
    val readyTime: Int,
    val image: String,
    val servings: Int,
    val instructions: String,
    val ingredients: List<Ingredient>
)
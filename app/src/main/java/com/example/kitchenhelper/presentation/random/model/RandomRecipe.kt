package com.example.kitchenhelper.presentation.random.model

data class RandomRecipe(
    val id: Int,
    val title: String,
    val readyTime: Int,
    val image: String,
    val servings: Int,
    val instructions: String,
    val ingredients: List<Ingredients>
)
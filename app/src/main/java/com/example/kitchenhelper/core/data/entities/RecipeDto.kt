package com.example.kitchenhelper.core.data.entities

data class RecipeDto(
    val id: Int,
    val title: String,
    val calories: Int,
    val carbs: String,
    val fat: String,
    val image: String,
    val protein: String
)

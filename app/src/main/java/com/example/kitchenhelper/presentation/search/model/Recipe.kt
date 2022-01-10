package com.example.kitchenhelper.presentation.search.model

data class Recipe(
    val id: Int,
    val title: String,
    val calories: Int,
    val carbs: String,
    val fat: String,
    val image: String,
    val protein: String
)

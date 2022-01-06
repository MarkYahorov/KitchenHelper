package com.example.kitchenhelper.core.data.entities

data class SearchResponse(
    val offset: Int,
    val number: Int,
    val result: List<RecipeDto>
)

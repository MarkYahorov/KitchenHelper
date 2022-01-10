package com.example.kitchenhelper.core.data.entities

import com.google.gson.annotations.SerializedName

data class RandomRecipeDto(
    @SerializedName("id")
    val id: Int?,
    @SerializedName("title")
    val title: String?,
    @SerializedName("readyInMinutes")
    val readyTime: Int?,
    @SerializedName("image")
    val image: String?,
    @SerializedName("servings")
    val servings: Int?,
    @SerializedName("instructions")
    val instructions: String?,
    @SerializedName("extendedIngredients")
    val ingredients: List<IngredientsDto>?
)
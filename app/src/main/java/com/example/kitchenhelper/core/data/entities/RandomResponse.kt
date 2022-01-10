package com.example.kitchenhelper.core.data.entities

import com.google.gson.annotations.SerializedName

data class RandomResponse(
    @SerializedName("recipes")
    val recipes: List<RandomRecipeDto>
)

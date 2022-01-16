package com.example.kitchenhelper.core.data.entities

import com.google.gson.annotations.SerializedName

data class SearchResponse(
    @SerializedName("offset")
    val offset: Number,
    @SerializedName("number")
    val number: Number,
    @SerializedName("results")
    val result: List<RecipeDto>
)

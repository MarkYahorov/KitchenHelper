package com.example.kitchenhelper.core.data.entities

import com.google.gson.annotations.SerializedName

data class IngredientsDto(
    @SerializedName("id")
    val id: Int?,
    @SerializedName("originalString")
    val originalString: String?,
)

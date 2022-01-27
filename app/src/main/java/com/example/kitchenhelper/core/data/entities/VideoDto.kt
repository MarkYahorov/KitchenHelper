package com.example.kitchenhelper.core.data.entities

import com.google.gson.annotations.SerializedName

data class VideoDto(
    @SerializedName("title")
    val title: String,
    @SerializedName("youTubeId")
    val youTubeId: String,
    @SerializedName("thumbnail")
    val image: String,
    @SerializedName("rating")
    val rating: Double
)

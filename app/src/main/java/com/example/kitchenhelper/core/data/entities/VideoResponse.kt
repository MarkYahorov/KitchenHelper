package com.example.kitchenhelper.core.data.entities

import com.google.gson.annotations.SerializedName

data class VideoResponse(
    @SerializedName("totalResults")
    val totalResults: Int,
    @SerializedName("videos")
    val videos: List<VideoDto>
)

package com.example.kitchenhelper.presentation.searchVideo.mappers

import com.example.kitchenhelper.core.data.entities.VideoDto
import com.example.kitchenhelper.presentation.searchVideo.models.Video

fun VideoDto.toPresentation() = Video(title, youTubeId, image, rating)
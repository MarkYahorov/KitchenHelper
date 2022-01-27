package com.example.kitchenhelper.presentation.searchVideo.repository

import androidx.paging.PagingData
import com.example.kitchenhelper.core.data.entities.VideoDto
import kotlinx.coroutines.flow.Flow

interface SearchVideoRepository {

    fun searchVideo(): Flow<PagingData<VideoDto>>
}
package com.example.kitchenhelper.presentation.searchVideo.repository

import androidx.paging.Pager
import androidx.paging.PagingConfig
import com.example.kitchenhelper.presentation.searchVideo.paging.SearchVideoPagingSource
import javax.inject.Inject

class SearchVideoRepositoryImpl
@Inject constructor(private val pagingSource: SearchVideoPagingSource) : SearchVideoRepository {
    override fun searchVideo() = Pager(
        config = PagingConfig(
            pageSize = 10,
            enablePlaceholders = false
        ),
        pagingSourceFactory = {
            pagingSource
        }
    ).flow
}
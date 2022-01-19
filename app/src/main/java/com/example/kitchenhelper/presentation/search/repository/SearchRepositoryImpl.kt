package com.example.kitchenhelper.presentation.search.repository

import androidx.paging.Pager
import androidx.paging.PagingConfig
import com.example.kitchenhelper.presentation.search.model.RequestParams
import com.example.kitchenhelper.presentation.search.paging.SearchPagingSource
import javax.inject.Inject

class SearchRepositoryImpl @Inject constructor(
    private val pagingSource: SearchPagingSource
) : SearchRepository {

    override suspend fun getSearchRecipes(
        requestParams: RequestParams
    ) = Pager(
        config = PagingConfig(
            pageSize = 10,
            enablePlaceholders = false
        ),
        pagingSourceFactory = {
            pagingSource
        }
    ).flow
}
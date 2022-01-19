package com.example.kitchenhelper.presentation.search.repository

import androidx.paging.PagingData
import com.example.kitchenhelper.core.data.entities.RecipeDto
import com.example.kitchenhelper.presentation.search.model.RequestParams
import kotlinx.coroutines.flow.Flow

interface SearchRepository {

    suspend fun getSearchRecipes(
        requestParams: RequestParams
    ): Flow<PagingData<RecipeDto>>
}
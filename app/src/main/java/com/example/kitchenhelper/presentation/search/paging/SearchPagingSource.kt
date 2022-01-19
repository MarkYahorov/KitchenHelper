package com.example.kitchenhelper.presentation.search.paging

import androidx.paging.PagingSource
import androidx.paging.PagingState
import com.example.kitchenhelper.core.data.api.RecipeService
import com.example.kitchenhelper.core.data.entities.RecipeDto
import com.example.kitchenhelper.presentation.search.model.RequestParams

class SearchPagingSource constructor(
    private val recipeService: RecipeService,
    private val requestParams: RequestParams
) : PagingSource<Int, RecipeDto>() {

    companion object {
        private const val START_PAGE = 1
    }

    override fun getRefreshKey(state: PagingState<Int, RecipeDto>): Int? {
        return state.anchorPosition?.let { anchorPosition ->
            state.closestPageToPosition(anchorPosition)?.prevKey?.plus(1)
                ?: state.closestPageToPosition(anchorPosition)?.nextKey?.minus(1)
        }
    }

    override suspend fun load(params: LoadParams<Int>): LoadResult<Int, RecipeDto> {
        val pageIndex = params.key ?: START_PAGE
        return try {
            val response = with(requestParams) {
                recipeService.getSearchRecipes(
                    query = query,
                    page = pageIndex,
                    maxCalories = calories?.second,
                    minCalories = calories?.first,
                    equipment = equipment,
                    cookingTime = maxReadyTime
                )
            }
            val recipes = response.result
            val nextKey = if (recipes.isEmpty()) {
                null
            } else {
                pageIndex + 1
            }
            LoadResult.Page(
                data = recipes,
                prevKey = if (pageIndex == START_PAGE) null else pageIndex,
                nextKey = nextKey
            )
        } catch (e: Exception) {
            return LoadResult.Error(e)
        }
    }
}
package com.example.kitchenhelper.presentation.searchVideo.paging

import androidx.paging.PagingSource
import androidx.paging.PagingState
import com.example.kitchenhelper.core.data.api.RecipeService
import com.example.kitchenhelper.core.data.entities.VideoDto
import com.example.kitchenhelper.presentation.searchVideo.models.VideoRequestParams
import javax.inject.Inject

class SearchVideoPagingSource @Inject constructor(
    private val service: RecipeService,
    private val request: VideoRequestParams
) : PagingSource<Int, VideoDto>() {

    companion object {
        private const val START_PAGE = 1
    }

    override fun getRefreshKey(state: PagingState<Int, VideoDto>): Int? {
        return state.anchorPosition?.let { anchorPosition ->
            state.closestPageToPosition(anchorPosition)?.prevKey?.plus(1)
                ?: state.closestPageToPosition(anchorPosition)?.nextKey?.minus(1)
        }
    }

    override suspend fun load(params: LoadParams<Int>): LoadResult<Int, VideoDto> {
        val pageIndex = params.key ?: START_PAGE
        return try {
            val response = with(request) {
                service.searchVideos(
                    query = query,
                    page = pageIndex
                )
            }
            val recipes = response.videos
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
package com.example.kitchenhelper.presentation.searchVideo.di

import com.example.kitchenhelper.core.data.api.RecipeService
import com.example.kitchenhelper.core.di.ScreenScope
import com.example.kitchenhelper.presentation.searchVideo.models.VideoRequestParams
import com.example.kitchenhelper.presentation.searchVideo.paging.SearchVideoPagingSource
import com.example.kitchenhelper.presentation.searchVideo.repository.SearchVideoRepository
import com.example.kitchenhelper.presentation.searchVideo.repository.SearchVideoRepositoryImpl
import dagger.Binds
import dagger.Module
import dagger.Provides

@Module
interface SearchVideoModule {

    companion object {
        private const val EMPTY_STRING = ""

        @Provides
        @ScreenScope
        fun provideVideoRequest() = VideoRequestParams(EMPTY_STRING)

        @Provides
        @ScreenScope
        fun provideDataSource(params: VideoRequestParams, service: RecipeService) =
            SearchVideoPagingSource(service, params)
    }

    @Binds
    @ScreenScope
    fun bindsRepository(repository: SearchVideoRepositoryImpl): SearchVideoRepository
}

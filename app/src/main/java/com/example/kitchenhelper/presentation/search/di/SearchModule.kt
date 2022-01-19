package com.example.kitchenhelper.presentation.search.di

import com.example.kitchenhelper.core.data.api.RecipeService
import com.example.kitchenhelper.core.di.ScreenScope
import com.example.kitchenhelper.presentation.search.model.RequestParams
import com.example.kitchenhelper.presentation.search.paging.SearchPagingSource
import com.example.kitchenhelper.presentation.search.repository.SearchRepository
import com.example.kitchenhelper.presentation.search.repository.SearchRepositoryImpl
import dagger.Binds
import dagger.Module
import dagger.Provides

@Module
interface SearchModule {

    companion object {
        private const val EMPTY_STRING = ""

        @Provides
        @ScreenScope
        fun provideRequestParams() = RequestParams(EMPTY_STRING)

        @Provides
        @ScreenScope
        fun providePagingSource(params: RequestParams, service: RecipeService) =
            SearchPagingSource(service, params)
    }

    @Binds
    @ScreenScope
    fun bindsRepository(repositoryImpl: SearchRepositoryImpl): SearchRepository
}
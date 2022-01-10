package com.example.kitchenhelper.presentation.search.di

import com.example.kitchenhelper.core.di.ScreenScope
import com.example.kitchenhelper.presentation.search.repository.SearchRepository
import com.example.kitchenhelper.presentation.search.repository.SearchSearchRepositoryImpl
import dagger.Binds
import dagger.Module

@Module
interface SearchModule {

    @Binds
    @ScreenScope
    fun bindsRepository(repositoryImpl: SearchSearchRepositoryImpl): SearchRepository
}
package com.example.kitchenhelper.presentation.random.di

import com.example.kitchenhelper.core.di.ScreenScope
import com.example.kitchenhelper.presentation.random.repository.RandomRepository
import com.example.kitchenhelper.presentation.random.repository.RandomRepositoryImpl
import dagger.Binds
import dagger.Module

@Module
interface RandomModule {

    @Binds
    @ScreenScope
    fun bindsRepository(repositoryImpl: RandomRepositoryImpl): RandomRepository
}
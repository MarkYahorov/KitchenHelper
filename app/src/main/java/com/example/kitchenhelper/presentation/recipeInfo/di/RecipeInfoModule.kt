package com.example.kitchenhelper.presentation.recipeInfo.di

import com.example.kitchenhelper.core.di.ScreenScope
import com.example.kitchenhelper.presentation.recipeInfo.repository.RecipeInfoRepository
import com.example.kitchenhelper.presentation.recipeInfo.repository.RecipeInfoRepositoryImpl
import dagger.Binds
import dagger.Module

@Module
interface RecipeInfoModule {

    @Binds
    @ScreenScope
    fun bindsRepository(repositoryImpl: RecipeInfoRepositoryImpl): RecipeInfoRepository
}

package com.example.kitchenhelper.core.di

import com.example.kitchenhelper.core.data.api.RecipeService
import dagger.Component

@Component(modules = [AppModule::class])
@AppScope
interface AppComponent {

    @Component.Factory
    interface Factory {
        fun create(): AppComponent
    }

    fun getApiService(): RecipeService
}
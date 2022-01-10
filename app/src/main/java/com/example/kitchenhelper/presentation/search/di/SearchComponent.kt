package com.example.kitchenhelper.presentation.search.di

import com.example.kitchenhelper.SearchRecipeFragment
import com.example.kitchenhelper.core.di.AppComponent
import com.example.kitchenhelper.core.di.ScreenScope
import dagger.Component

@Component(dependencies = [AppComponent::class], modules = [SearchModule::class])
@ScreenScope
interface SearchComponent {

    companion object {
        fun create(appComponent: AppComponent, fragment: SearchRecipeFragment) {
            DaggerSearchComponent.factory().create(appComponent).inject(fragment)
        }
    }

    @Component.Factory
    interface Factory {
        fun create(appComponent: AppComponent): SearchComponent
    }

    fun inject(fragment: SearchRecipeFragment)
}
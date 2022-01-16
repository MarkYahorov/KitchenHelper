package com.example.kitchenhelper.presentation.search.di

import com.example.kitchenhelper.presentation.search.screen.SearchRecipeFragment
import com.example.kitchenhelper.core.di.AppComponent
import com.example.kitchenhelper.core.di.ScreenScope
import com.example.kitchenhelper.presentation.search.viewModel.SearchRecipeViewModel
import dagger.Component

@Component(dependencies = [AppComponent::class], modules = [SearchModule::class])
@ScreenScope
interface SearchComponent {

    companion object {
        private var component: SearchComponent? = null
        fun create(appComponent: AppComponent, fragment: SearchRecipeFragment): SearchComponent {
            return component ?: DaggerSearchComponent.factory().create(appComponent).also {
                component = it
                it.inject(fragment)
            }
        }
    }

    @Component.Factory
    interface Factory {
        fun create(appComponent: AppComponent): SearchComponent
    }

    fun inject(fragment: SearchRecipeFragment)
    val searchViewModel: SearchRecipeViewModel
}
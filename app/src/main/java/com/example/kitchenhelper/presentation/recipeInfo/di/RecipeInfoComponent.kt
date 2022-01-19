package com.example.kitchenhelper.presentation.recipeInfo.di

import com.example.kitchenhelper.core.di.AppComponent
import com.example.kitchenhelper.core.di.ScreenScope
import com.example.kitchenhelper.presentation.recipeInfo.screen.RecipeInformationScreen
import com.example.kitchenhelper.presentation.recipeInfo.viewModel.RecipeInfoViewModel
import dagger.Component

@Component(dependencies = [AppComponent::class], modules = [RecipeInfoModule::class])
@ScreenScope
interface RecipeInfoComponent {

    companion object {
        private var component: RecipeInfoComponent? = null
        fun create(
            appComponent: AppComponent,
            fragment: RecipeInformationScreen
        ): RecipeInfoComponent {
            return component ?: DaggerRecipeInfoComponent.factory().create(appComponent).also {
                component = it
                component?.inject(fragment)
            }
        }

        fun clear() {
            component = null
        }
    }

    @Component.Factory
    interface Factory {
        fun create(appComponent: AppComponent): RecipeInfoComponent
    }

    fun inject(fragment: RecipeInformationScreen)
    val recipeInfoViewModel: RecipeInfoViewModel
}
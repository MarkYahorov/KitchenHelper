package com.example.kitchenhelper.presentation.random.di

import com.example.kitchenhelper.RandomFragment
import com.example.kitchenhelper.core.di.AppComponent
import com.example.kitchenhelper.core.di.ScreenScope
import com.example.kitchenhelper.presentation.random.viewModel.RandomViewModel
import dagger.Component

@Component(dependencies = [AppComponent::class], modules = [RandomModule::class])
@ScreenScope
interface RandomComponent {

    companion object {
        private var component: RandomComponent? = null

        fun createComponent(appComponent: AppComponent, fragment: RandomFragment): RandomComponent {
            return component ?: DaggerRandomComponent.factory().create(appComponent).also {
                component = it
                component?.inject(fragment)
            }
        }

        fun clearComponent() {
            component = null
        }
    }

    @Component.Factory
    interface Factory {
        fun create(appComponent: AppComponent): RandomComponent
    }

    fun inject(fragment: RandomFragment)
    val randomViewModel: RandomViewModel
}
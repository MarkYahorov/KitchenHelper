package com.example.kitchenhelper.presentation.random.di

import com.example.kitchenhelper.RandomFragment
import com.example.kitchenhelper.core.di.AppComponent
import com.example.kitchenhelper.core.di.ScreenScope
import dagger.Component

@Component(dependencies = [AppComponent::class], modules = [RandomModule::class])
@ScreenScope
interface RandomComponent {

    companion object {
        fun create(appComponent: AppComponent, fragment: RandomFragment) {
            DaggerRandomComponent.factory().create(appComponent).inject(fragment)
        }
    }

    @Component.Factory
    interface Factory {
        fun create(appComponent: AppComponent): RandomComponent
    }

    fun inject(fragment: RandomFragment)
}
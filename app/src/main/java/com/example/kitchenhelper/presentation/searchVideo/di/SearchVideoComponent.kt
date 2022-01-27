package com.example.kitchenhelper.presentation.searchVideo.di

import com.example.kitchenhelper.core.di.AppComponent
import com.example.kitchenhelper.core.di.ScreenScope
import com.example.kitchenhelper.presentation.searchVideo.screen.SearchVideoFragment
import com.example.kitchenhelper.presentation.searchVideo.viewModel.SearchVideoViewModel
import dagger.Component

@Component(dependencies = [AppComponent::class], modules = [SearchVideoModule::class])
@ScreenScope
interface SearchVideoComponent {

    companion object {
        private var component: SearchVideoComponent? = null

        fun create(
            appComponent: AppComponent,
            fragment: SearchVideoFragment
        ): SearchVideoComponent {
            return component ?: DaggerSearchVideoComponent.factory().create(appComponent).also {
                component = it
                it.inject(fragment)
            }
        }

        fun clear() {
            component = null
        }
    }

    @Component.Factory
    interface Factory {
        fun create(appComponent: AppComponent): SearchVideoComponent
    }

    fun inject(fragment: SearchVideoFragment)
    val searchVideoViewModel: SearchVideoViewModel
}
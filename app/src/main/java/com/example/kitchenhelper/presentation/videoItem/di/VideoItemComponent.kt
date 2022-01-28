package com.example.kitchenhelper.presentation.videoItem.di

import com.example.kitchenhelper.VideoInfoFragment
import com.example.kitchenhelper.core.di.AppComponent
import com.example.kitchenhelper.core.di.ScreenScope
import com.example.kitchenhelper.presentation.videoItem.viewModel.VideoInfoViewModel
import dagger.Component

@Component(dependencies = [AppComponent::class])
@ScreenScope
interface VideoItemComponent {

    companion object {
        private var component: VideoItemComponent? = null

        fun create(appComponent: AppComponent, fragment: VideoInfoFragment): VideoItemComponent {
            return component ?: DaggerVideoItemComponent.factory().create(appComponent).also {
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
        fun create(appComponent: AppComponent): VideoItemComponent
    }

    fun inject(fragment: VideoInfoFragment)

    val videoItemViewModel: VideoInfoViewModel
}
package com.example.kitchenhelper

import android.app.Application
import com.example.kitchenhelper.core.di.AppComponent
import com.example.kitchenhelper.core.di.DaggerAppComponent

class App: Application() {

    companion object {
        lateinit var appComponent: AppComponent
    }

    override fun onCreate() {
        super.onCreate()

        appComponent = DaggerAppComponent.factory().create()
    }
}
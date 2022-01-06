package com.example.kitchenhelper.core.di

import com.example.kitchenhelper.core.data.api.RecipeService
import dagger.Module
import dagger.Provides
import retrofit2.Retrofit
import retrofit2.converter.gson.GsonConverterFactory

const val BASE_URL = "https://api.spoonacular.com/"

@Module
class AppModule {

    @Provides
    @AppScope
    fun provideRetrofit(): Retrofit {
        return Retrofit.Builder()
            .baseUrl(BASE_URL)
            .addConverterFactory(GsonConverterFactory.create())
            .build()
    }

    @Provides
    @AppScope
    fun provideApiService(retrofit: Retrofit): RecipeService = retrofit.create(RecipeService::class.java)
}
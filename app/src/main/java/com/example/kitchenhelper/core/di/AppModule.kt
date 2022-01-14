package com.example.kitchenhelper.core.di

import com.example.kitchenhelper.core.data.api.RecipeService
import dagger.Module
import dagger.Provides
import okhttp3.OkHttpClient
import retrofit2.Retrofit
import retrofit2.converter.gson.GsonConverterFactory

@Module
class AppModule {

    companion object {
        private const val BASE_URL = "https://api.spoonacular.com/"
        private const val API_KEY = "11ebd11f6ced49c2976ca04567cd15ec"
        private const val API_KEY_NAME = "apiKey"
    }

    @Provides
    @AppScope
    fun provideOkHttp(): OkHttpClient {
        return OkHttpClient.Builder()
            .addInterceptor { chain ->
                val request = chain.request()
                val newUrl =
                    request.url().newBuilder().addQueryParameter(API_KEY_NAME, API_KEY).build()
                val newRequest = request.newBuilder().url(newUrl).build()
                chain.proceed(newRequest)
            }
            .build()
    }

    @Provides
    @AppScope
    fun provideRetrofit(okHttpClient: OkHttpClient): Retrofit {
        return Retrofit.Builder()
            .baseUrl(BASE_URL)
            .client(okHttpClient)
            .addConverterFactory(GsonConverterFactory.create())
            .build()
    }

    @Provides
    @AppScope
    fun provideApiService(retrofit: Retrofit): RecipeService = retrofit.create(RecipeService::class.java)
}
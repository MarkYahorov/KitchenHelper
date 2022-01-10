package com.example.kitchenhelper.core.data.api

import com.example.kitchenhelper.core.data.entities.RandomRecipeDto
import com.example.kitchenhelper.core.data.entities.RandomResponse
import com.example.kitchenhelper.core.data.entities.SearchResponse
import retrofit2.http.GET
import retrofit2.http.Path
import retrofit2.http.Query

interface RecipeService {

    @GET("recipes/complexSearch")
    suspend fun getSearchRecipes(@Query("query") query: String): SearchResponse

    @GET
    suspend fun getInfoAboutRecipe(@Path("id") id: Int)

    @GET("recipes/random")
    suspend fun getRandomRecipes(): RandomResponse
}
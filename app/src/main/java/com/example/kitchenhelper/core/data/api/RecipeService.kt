package com.example.kitchenhelper.core.data.api

import com.example.kitchenhelper.core.data.entities.RandomResponse
import com.example.kitchenhelper.core.data.entities.SearchResponse
import retrofit2.http.GET
import retrofit2.http.Path
import retrofit2.http.Query

interface RecipeService {
    @GET("recipes/complexSearch")
    suspend fun getSearchRecipes(
        @Query("query") query: String,
        @Query("offset") page: Int = 0,
        @Query("equipment") equipment: String? = null,
        @Query("maxReadyTime") cookingTime: Int? = null,
        @Query("minCalories") minCalories: Int? = null,
        @Query("maxCalories") maxCalories: Int? = null
    ): SearchResponse

    @GET
    suspend fun getInfoAboutRecipe(@Path("id") id: Int)

    @GET("recipes/random")
    suspend fun getRandomRecipes(): RandomResponse
}
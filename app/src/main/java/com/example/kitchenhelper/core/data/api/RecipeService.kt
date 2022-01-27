package com.example.kitchenhelper.core.data.api

import com.example.kitchenhelper.core.data.entities.*
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

    @GET("recipes/{id}/information")
    suspend fun getInfoAboutRecipe(@Path("id") id: Int): RecipeInfoDto

    @GET("recipes/random")
    suspend fun getRandomRecipes(): RandomResponse

    @GET("food/videos/search")
    suspend fun searchVideos(
        @Query("query") query: String,
        @Query("offset") page: Int = 0,
    ): VideoResponse
}
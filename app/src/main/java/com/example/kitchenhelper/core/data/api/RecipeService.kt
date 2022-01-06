package com.example.kitchenhelper.core.data.api

import com.example.kitchenhelper.core.data.entities.SearchResponse
import retrofit2.http.GET
import retrofit2.http.Path
import retrofit2.http.Query

interface RecipeService {

    @GET
    fun getSearchRecipes(@Query("query") query: String): SearchResponse

    @GET
    fun getInfoAboutRecipe(@Path("id") id: Int)
}
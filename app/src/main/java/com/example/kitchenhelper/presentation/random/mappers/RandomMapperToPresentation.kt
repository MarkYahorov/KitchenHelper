package com.example.kitchenhelper.presentation.random.mappers

import com.example.kitchenhelper.core.data.entities.IngredientsDto
import com.example.kitchenhelper.core.data.entities.RandomRecipeDto
import com.example.kitchenhelper.presentation.random.model.Ingredients
import com.example.kitchenhelper.presentation.random.model.RandomRecipe

fun IngredientsDto.toPresentation() = Ingredients(id, image,originalString)
fun RandomRecipeDto.toPresentation() = RandomRecipe(
    id,
    title,
    readyTime,
    image,
    servings,
    instructions,
    ingredients?.map { it.toPresentation() }
)
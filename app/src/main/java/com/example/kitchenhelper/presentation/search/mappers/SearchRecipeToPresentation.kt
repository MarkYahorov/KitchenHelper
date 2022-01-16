package com.example.kitchenhelper.presentation.search.mappers

import com.example.kitchenhelper.core.data.entities.RecipeDto
import com.example.kitchenhelper.presentation.search.model.Recipe

fun RecipeDto.toPresentation() = Recipe(id, title, image)
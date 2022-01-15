package com.example.kitchenhelper.presentation.random.mappers

import com.example.kitchenhelper.core.data.entities.IngredientsDto
import com.example.kitchenhelper.core.data.entities.RandomRecipeDto
import com.example.kitchenhelper.presentation.random.model.Ingredient
import com.example.kitchenhelper.presentation.random.model.RandomRecipe
import java.util.regex.Pattern

fun IngredientsDto.toPresentation() = Ingredient(id, image, originalString)
fun RandomRecipeDto.toPresentation(pattern: Pattern, buffer: StringBuffer): RandomRecipe {

    if (buffer.isNotEmpty()) {
        buffer.delete(0, buffer.length)
    }

    val matcher = pattern.matcher(instructions)
    while (matcher.find()) {
        matcher.appendReplacement(buffer, "")
    }

    return RandomRecipe(
        id,
        title,
        readyTime,
        image,
        servings,
        matcher.appendTail(buffer).toString(),
        ingredients.map { it.toPresentation() }
    )
}
package com.example.kitchenhelper.presentation.recipeInfo.mappers

import com.example.kitchenhelper.core.data.entities.RecipeInfoDto
import com.example.kitchenhelper.presentation.random.mappers.toPresentation
import com.example.kitchenhelper.presentation.recipeInfo.model.RecipeInfo
import java.util.regex.Pattern

fun RecipeInfoDto.toPresentation(buffer: StringBuffer, pattern: Pattern): RecipeInfo {
    if (buffer.isNotEmpty()) {
        buffer.delete(0, buffer.length)
    }

    val matcher = pattern.matcher(instructions)
    while (matcher.find()) {
        matcher.appendReplacement(buffer, "")
    }

    return RecipeInfo(
        id,
        title,
        readyTime,
        image,
        servings,
        matcher.appendTail(buffer).toString(),
        ingredients.map { it.toPresentation() }
    )
}
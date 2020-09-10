package com.aldidwikip.thecocktail.util

import com.aldidwikip.thecocktail.data.model.CocktailDetail

fun mapIngredientsToList(data: CocktailDetail): List<String> {
    val mIngredientsList: MutableList<String?> = mutableListOf()

    mIngredientsList.add(data.strIngredient1)
    mIngredientsList.add(data.strIngredient2)
    mIngredientsList.add(data.strIngredient3)
    mIngredientsList.add(data.strIngredient4)
    mIngredientsList.add(data.strIngredient5)
    mIngredientsList.add(data.strIngredient6)
    mIngredientsList.add(data.strIngredient7)
    mIngredientsList.add(data.strIngredient8)
    mIngredientsList.add(data.strIngredient9)
    mIngredientsList.add(data.strIngredient10)
    mIngredientsList.add(data.strIngredient11)
    mIngredientsList.add(data.strIngredient12)
    mIngredientsList.add(data.strIngredient13)
    mIngredientsList.add(data.strIngredient14)
    mIngredientsList.add(data.strIngredient15)

    return mIngredientsList.filter { it != "" }.filterNotNull()
}

fun mapMeasuresToList(data: CocktailDetail): List<String?> {
    val mMeasuresList: MutableList<String?> = mutableListOf()

    mMeasuresList.add(data.strMeasure1)
    mMeasuresList.add(data.strMeasure2)
    mMeasuresList.add(data.strMeasure3)
    mMeasuresList.add(data.strMeasure4)
    mMeasuresList.add(data.strMeasure5)
    mMeasuresList.add(data.strMeasure6)
    mMeasuresList.add(data.strMeasure7)
    mMeasuresList.add(data.strMeasure8)
    mMeasuresList.add(data.strMeasure9)
    mMeasuresList.add(data.strMeasure10)
    mMeasuresList.add(data.strMeasure11)
    mMeasuresList.add(data.strMeasure12)
    mMeasuresList.add(data.strMeasure13)
    mMeasuresList.add(data.strMeasure14)
    mMeasuresList.add(data.strMeasure15)

    return mMeasuresList
}
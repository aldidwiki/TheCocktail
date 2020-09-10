package com.aldidwikip.thecocktail.data.model

import androidx.room.Entity
import androidx.room.PrimaryKey
import com.google.gson.annotations.SerializedName

data class CocktailResponse(
        val drinks: List<Cocktail>
)

data class CocktailDetailResponse(
        @SerializedName("drinks")
        val detailDrinks: List<CocktailDetail>
)

@Entity(tableName = "cocktail_table")
data class Cocktail(

        @SerializedName("idDrink")
        @PrimaryKey
        val cocktailId: String,

        @SerializedName("strDrink")
        val cocktailName: String,

        @SerializedName("strDrinkThumb")
        val cocktailImg: String
)

@Entity(tableName = "cocktail_detail_table")
data class CocktailDetail(
        @SerializedName("idDrink")
        @PrimaryKey
        val cocktailId: String,

        @SerializedName("strDrink")
        val cocktailName: String,

        @SerializedName("strGlass")
        val cocktailGlass: String,

        @SerializedName("strInstructions")
        val cocktailInstructions: String,

        @SerializedName("strDrinkThumb")
        val cocktailImg: String,

        val strIngredient1: String?,
        val strIngredient2: String?,
        val strIngredient3: String?,
        val strIngredient4: String?,
        val strIngredient5: String?,
        val strIngredient6: String?,
        val strIngredient7: String?,
        val strIngredient8: String?,
        val strIngredient9: String?,
        val strIngredient10: String?,
        val strIngredient11: String?,
        val strIngredient12: String?,
        val strIngredient13: String?,
        val strIngredient14: String?,
        val strIngredient15: String?,
        val strMeasure1: String?,
        val strMeasure2: String?,
        val strMeasure3: String?,
        val strMeasure4: String?,
        val strMeasure5: String?,
        val strMeasure6: String?,
        val strMeasure7: String?,
        val strMeasure8: String?,
        val strMeasure9: String?,
        val strMeasure10: String?,
        val strMeasure11: String?,
        val strMeasure12: String?,
        val strMeasure13: String?,
        val strMeasure14: String?,
        val strMeasure15: String?,
        val strMeasure16: String?
)
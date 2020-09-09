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
        val cocktailImg: String
)